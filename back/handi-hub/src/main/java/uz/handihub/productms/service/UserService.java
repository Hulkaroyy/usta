package uz.handihub.productms.service;

import uz.handihub.productms.service.dto.UserDTO;
import uz.handihub.productms.service.dto.UserRequestDTO;

import java.util.Optional;

public interface UserService {
    String create(UserRequestDTO userRequestDto);

    void sendEmail4forgotPassword(String email);

    Optional<UserDTO> getUserInfo();

    void verifyEmail(String id);

    Optional<UserDTO> getUserInfoById(String id);

    String getUserId(String username);
}
