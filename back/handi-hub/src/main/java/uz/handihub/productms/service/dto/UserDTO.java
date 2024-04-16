package uz.handihub.productms.service.dto;

import lombok.Data;
import uz.handihub.productms.domain.enumeration.Role;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String imageUrl;
    private Role role;
    private String phoneNumber;
    private String fullName;
    private String licenseUrl;
    private String sellerType;
    private boolean isEmailVerified;
}
