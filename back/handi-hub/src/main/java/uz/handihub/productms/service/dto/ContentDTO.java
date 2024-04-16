package uz.handihub.productms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContentDTO {

    private UUID id;

    private String photoUrl;

    private MediaType type;

    private Boolean isMain;

    private ProductDTO product;
}
