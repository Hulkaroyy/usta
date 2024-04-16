package uz.handihub.productms.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import uz.handihub.productms.domain.enumeration.Role;
import uz.handihub.productms.exceptions.*;
import uz.handihub.productms.properties.KeycloakProperties;
import uz.handihub.productms.service.UserService;
import uz.handihub.productms.service.dto.UserDTO;
import uz.handihub.productms.service.dto.UserRequestDTO;
import uz.handihub.productms.service.mapper.UserMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String UPDATE_PASSWORD ="UPDATE_PASSWORD";
    public static final int ONE = 1;

    private final KeycloakProperties keyCloakProperties;
    private final Keycloak keycloak;
    private final UserMapper userMapper;
    private UsersResource usersResource;

    public UserServiceImpl(KeycloakProperties keyCloakProperties, Keycloak keycloak, UserMapper userMapper) {
        this.keyCloakProperties = keyCloakProperties;
        this.keycloak = keycloak;
        this.userMapper = userMapper;
    }

    @PostConstruct
    void init() {
        usersResource = keycloak.realm(keyCloakProperties.getRealm()).users();
    }

    @Override
    public String create(UserRequestDTO userRequestDto) {
        log.info("Requested to create new user");

        if (Objects.isNull(userRequestDto)) {
            log.warn("requestDTO cannot be null");
            throw new InvalidArgumentException("requestDto cannot be null");
        }
        String userId;

        try {
            checkUsers(usersResource, userRequestDto);

            Response response = usersResource.create(userMapper.toUserRepresentation(userRequestDto));

            if(Objects.isNull(response.getLocation())) {
                log.warn("Network url is null");
                throw new InternalServerErrorException("Network url is null");
            }

            log.info("Network location: {}", response.getLocation());

             userId = extractUserId(response);

            usersResource.get(userId).sendVerifyEmail();
            log.info("Successfully sent email");
        } catch (ExternalServerErrorException ex) {
            log.warn(ex.getMessage());
            throw new ExternalServerErrorException(ex.getMessage());
        }

        log.info("Successfully created new User");
        return userId;
    }

    @Override
    public void sendEmail4forgotPassword(String email) {
        log.info("Requested to reset password, email: {}", email);
        List<UserRepresentation> users = usersResource.searchByEmail(email, true);
        Optional<UserRepresentation> user = users.stream().filter(u->Objects.equals(u.getEmail(), email)).findFirst();

        if(user.isEmpty()) {
            log.warn("User is not found {}", email);
            throw new NotFoundException("User is not found");
        }

        UserRepresentation theUser = user.get();

        usersResource.get(theUser.getId()).executeActionsEmail(List.of(UPDATE_PASSWORD));
        log.info("Successfully reset password");
    }

    @Override
    public Optional<UserDTO> getUserInfo() {
        log.info("Requested to get user info");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)) {
            log.warn("User should be signed in");
            throw new InternalServerErrorException("You should sign in");
        }

        String userId = ((Jwt) authentication.getPrincipal()).getClaimAsString("sub");

        return getUserInfoById(userId);
    }

    @Override
    public void verifyEmail(String id) {
        log.info("Requested to send email for verification");
        UserResource userResource = usersResource.get(id);

        if(Objects.isNull(userResource)) {
            log.warn("userResource is null");
            throw new InternalServerErrorException("User resource is null");
        }

        userResource.sendVerifyEmail();
        log.info("Successfully send email");
    }

    @Override
    public Optional<UserDTO> getUserInfoById(String id) {
        UserResource userResource = usersResource.get(id);

        if(Objects.isNull(userResource)) {
            log.warn("User is not found {}", id);
            throw new NotFoundException("User is not found");
        }

        UserDTO userDTO = userMapper.toUserDTO(userResource.toRepresentation());
        setRole4User(userDTO, userResource);

        log.info("Successfully fetched user info");
        return Optional.of(userDTO);
    }

    @Override
    public String getUserId(String username) {
        UserRepresentation userRepresentation = usersResource.searchByUsername(username, true).stream().findFirst().orElseThrow(()-> new NotFoundException("User is not found"));
        return userRepresentation.getId();
    }

    private void setRole4User(UserDTO userDTO, UserResource userResource) {
        log.info("Started to set role to the user {}", userDTO.getUsername());

        GroupRepresentation group = userResource
                .groups()
                .stream()
                .findFirst()
                .orElseThrow(()->new CustomException("User is not assigned to group"));

        Role role = UserMapper.GROUP_BY_ROLES
                .keySet()
                .stream()
                .filter(key-> Objects.equals(UserMapper.GROUP_BY_ROLES.get(key),  group.getId()))
                .findFirst()
                .orElseThrow(()->new NotFoundException("User is not assigned to role"));

        userDTO.setRole(role);
        log.info("Successfully set role for user");
    }

    private void checkUsers(UsersResource usersResource, UserRequestDTO userRequestDTO) {
        List<UserRepresentation> users = usersResource.searchByUsername(userRequestDTO.getUsername(), true);
        if (!users.isEmpty()) {
            log.warn("User exists with the username {}", userRequestDTO.getUsername());
            throw new ExistsException("Username exists " + userRequestDTO.getUsername());
        }

        users = usersResource.searchByEmail(userRequestDTO.getEmail(), true);
        if (!users.isEmpty()) {
            log.warn("User exists with the email {}", userRequestDTO.getEmail());
            throw new ExistsException("Email already exists");
        }
    }

    private String extractUserId (Response response) {
        log.info("Requested to extract userId from network location");
        Path path = Paths.get(response.getLocation().getPath());
        String userId = path.getName(path.getNameCount() - ONE).toString();

        log.info("successfully extracted {}", userId);
        return userId;
    }
}
