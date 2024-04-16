package uz.handihub.productms.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import uz.handihub.productms.domain.enumeration.Category;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductDTO {

    private UUID id;

    @NotBlank
    @Size(max = 64)
    private String name;

    @NotNull
    @Positive
    private Float price;

    @NotNull
    private Category category;

    private Instant createdAt;

    private UUID createdBy;

    @Size(max = 256)
    private String description;

    private List<CommentDTO> comments;

    private List<ContentDTO> contents;
}
