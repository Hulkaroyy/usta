package uz.handihub.productms.service.mapper;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import uz.handihub.productms.domain.enumeration.Role;
import uz.handihub.productms.service.dto.UserDTO;
import uz.handihub.productms.service.dto.UserRequestDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    Map<Role, String> GROUP_BY_ROLES = Map.of(Role.ROLE_CUSTOMER, "Customer", Role.ROLE_SELLER, "Seller");
    String KEY_PHONE_NUMBER = "phoneNumber";
    String KEY_SELLER_TYPE = "sellerType";
    String KEY_LICENSE_URL = "licenseUrl";
    String KEY_IMAGE_URL = "imageUrl";

    @Mapping(target = "groups", source = "role", qualifiedByName = "setRoles")
    @Mapping(target = "firstName", source = "fullName")
    @Mapping(target = "enabled", constant = "true", resultType = Boolean.class)
    @Mapping(target = "credentials", source = "password", qualifiedByName = "setCredentials")
    UserRepresentation toUserRepresentation(UserRequestDTO requestDto);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "sellerType", expression = "java(getDataFromAttributes(userRepresentation.getAttributes(),KEY_SELLER_TYPE ))")
    @Mapping(target = "phoneNumber", expression = "java(getDataFromAttributes(userRepresentation.getAttributes(),KEY_PHONE_NUMBER ))")
    @Mapping(target = "licenseUrl", expression = "java(getDataFromAttributes(userRepresentation.getAttributes(),KEY_LICENSE_URL ))")
    @Mapping(target = "imageUrl",expression = "java(getDataFromAttributes(userRepresentation.getAttributes(),KEY_IMAGE_URL ))")
    @Mapping(target = "fullName", source = "firstName")
    UserDTO toUserDTO(UserRepresentation userRepresentation);

    @Named("setCredentials")
    default List<CredentialRepresentation> setCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setType("password");
        return List.of(credentialRepresentation);
    }

    @Named("setRoles")
    default List<String> setRoles(Role userRole) {
        return List.of(GROUP_BY_ROLES.get(userRole));
    }

    default String getDataFromAttributes(Map<String, List<String>> attributes, String key ) {
        if(Objects.isNull(attributes)) {
            return null;
        }

        List<String> values =  attributes.get(key);

        if(Objects.isNull(values)) {
            return null;
        }

        Optional<String> attribute = values.stream().findFirst();

        if(attribute.isEmpty()) {
            return null;
        }

        return attribute.get();
    }
}
