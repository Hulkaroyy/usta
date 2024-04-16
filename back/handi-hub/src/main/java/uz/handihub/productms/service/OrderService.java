package uz.handihub.productms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.handihub.productms.security.SecurityUtils;
import uz.handihub.productms.service.dto.OrderDTO;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    Optional<OrderDTO> save(OrderDTO dto);

    Optional<OrderDTO> getById(UUID id);

    Page<OrderDTO> getUserOrders(UUID userId, Pageable pageable);

    default Page<OrderDTO> getCurrentUserOrders(Pageable pageable) {

        UUID userId = SecurityUtils.getUserId();

        return getUserOrders(userId, pageable);
    }
}
