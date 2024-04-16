package uz.handihub.productms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import uz.handihub.productms.domain.enumeration.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDTO {

    private UUID id;

    private Float totalAmount;

    private OrderStatus status;

    private Instant createdAt;

    private UUID createdBy;

    private List<CartDTO> carts;

}
