package uz.handihub.productms.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String error;
    private int status;
    private Long timestamp;
}
