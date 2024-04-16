package uz.handihub.productms.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.handihub.productms.service.UserService;
import uz.handihub.productms.service.dto.UserDTO;
import uz.handihub.productms.service.dto.UserRequestDTO;
import uz.handihub.productms.web.rest.utils.ResponseUtils;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getUserId(@RequestParam("username") String username) {
        log.debug("REST request to get user by username");
        String userId = userService.getUserId(username);

        log.debug("Successfully fetched userId");
        return ResponseEntity.ok().body(Map.of("id", userId));
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody UserRequestDTO userRequestDto) {
        log.debug("REST request to create new User");
        String userId = userService.create(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("userId", userId));
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam("email") String email) {
        log.debug("REST request to reset password to email");
        userService.sendEmail4forgotPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/verify-email/{id}")
    public ResponseEntity<Void> verifyEmail(@PathVariable String id) {
        log.debug("REST request to verify email");
        userService.verifyEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/user/info/{id}")
    public ResponseEntity<UserDTO> getInfoById(@PathVariable String id) {
        log.debug("REST request to get user info id {}", id);
        Optional<UserDTO> userDTO = userService.getUserInfoById(id);

        ResponseEntity<UserDTO> response = ResponseUtils.wrap(userDTO);
        log.debug("Getting user info. Response: {}", response);
        return response;
    }
}
