package uz.handihub.productms.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentDTO {

    private UUID id;

    @NotBlank
    @Size(max = 1024)
    private String message;

    private Integer rating;

    private Instant createdAt;

    private UUID createdBy;

}
