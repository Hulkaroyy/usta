package uz.handihub.productms.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.handihub.productms.service.OrderService;
import uz.handihub.productms.service.dto.OrderDTO;
import uz.handihub.productms.web.rest.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderResource {

    private OrderService service;

    @GetMapping("/order")
    public ResponseEntity<List<OrderDTO>> findOrdersOfUser(@RequestParam("userId") UUID userId,
                                                           @ParameterObject Pageable pageable) {
        log.debug("REST request to find orders of users by UserID");

        Page<OrderDTO> ordersPage = service.getUserOrders(userId, pageable);

        ResponseEntity<List<OrderDTO>> response = ResponseUtils.wrap(ordersPage);

        log.debug("Find orders of users by UserID result. Response: {}", response);
        return response;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable UUID id) {
        log.debug("REST request to find order by ID");

        Optional<OrderDTO> orderOptional = service.getById(id);

        ResponseEntity<OrderDTO> response = ResponseUtils.wrap(orderOptional);

        log.debug("Find order by ID result. Response: {}", response);
        return response;
    }
}
