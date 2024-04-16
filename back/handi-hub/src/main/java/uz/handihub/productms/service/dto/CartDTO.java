package uz.handihub.productms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CartDTO {

    private UUID id;

    private UUID userId;

    private Integer count;

    private ProductDTO product;

}
