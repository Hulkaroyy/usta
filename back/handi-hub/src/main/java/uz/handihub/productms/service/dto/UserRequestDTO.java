package uz.handihub.productms.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import uz.handihub.productms.domain.enumeration.Role;

@Data
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @NotBlank(message = "email cannot be null")
    private String email;

    @NotBlank(message = "fullName cannot be null")
    private String fullName;

    @NotNull(message = "userType cannot be null")
    private Role role;
}
