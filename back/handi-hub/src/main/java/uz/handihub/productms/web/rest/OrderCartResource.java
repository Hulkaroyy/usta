package uz.handihub.productms.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.handihub.productms.service.OrderCartService;
import uz.handihub.productms.service.dto.CartDTO;
import uz.handihub.productms.service.dto.CartProductsDTO;
import uz.handihub.productms.service.dto.OrderDTO;
import uz.handihub.productms.web.rest.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderCartResource {

    private final OrderCartService orderCartService;

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody List<CartDTO> carts) {
        log.debug("REST request to ordering products");

        Optional<OrderDTO> orderOptional = orderCartService.order(carts);

        ResponseEntity<OrderDTO> response = ResponseUtils.wrap(orderOptional);

        log.debug("Ordering products result. Response: {}", response);
        return response;
    }
}
