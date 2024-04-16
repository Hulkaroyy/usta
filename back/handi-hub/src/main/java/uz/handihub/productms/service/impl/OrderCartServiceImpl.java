package uz.handihub.productms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import uz.handihub.productms.domain.enumeration.OrderStatus;
import uz.handihub.productms.exceptions.InternalServerErrorException;
import uz.handihub.productms.exceptions.InvalidArgumentException;
import uz.handihub.productms.security.SecurityUtils;
import uz.handihub.productms.service.OrderCartService;
import uz.handihub.productms.service.OrderService;
import uz.handihub.productms.service.ProductService;
import uz.handihub.productms.service.dto.CartDTO;
import uz.handihub.productms.service.dto.OrderDTO;
import uz.handihub.productms.service.dto.ProductDTO;
import uz.handihub.productms.service.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCartServiceImpl implements OrderCartService {

    private final OrderService orderService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public Optional<OrderDTO> order(List<CartDTO> carts) {
        log.debug("Start ordering products for current user");

        if (CollectionUtils.isEmpty(carts)) {
            log.warn("Invalid argument is passed! Carts collection must not be empty!");
            throw new InvalidArgumentException("Invalid argument is passed! Products collection must not be empty!");
        }

        UUID userId = SecurityUtils.getUserId();
        float totalAmount = calculateTotalAmount(carts, userId);

        OrderDTO order = new OrderDTO();
        order.setStatus(OrderStatus.NOT_PAID);
        order.setCreatedAt(Instant.now());
        order.setCreatedBy(userId);
        order.setTotalAmount(totalAmount);
        order.setCarts(carts);

        Optional<OrderDTO> orderOptional = orderService.save(order);
        if (orderOptional.isEmpty()) {
            log.warn("Order is not saved!");
            throw new InternalServerErrorException("Order is not saved!");
        }

        log.debug("Products ordered successfully. OrderDTO: {}", orderOptional.get());
        return orderOptional;
    }

    private float calculateTotalAmount(List<CartDTO> carts, UUID userId) {

        float totalAmount = 0f;
        for (CartDTO cart : carts) {
            if (!validateCardAndProduct(cart)) continue;

            totalAmount += calculateCartProduct(cart);
            cart.setUserId(userId);
        }

        return totalAmount;
    }

    private boolean validateCardAndProduct(CartDTO cart) {
        return Objects.nonNull(cart) && validateProductOfCard(cart.getProduct());
    }

    private boolean validateProductOfCard(ProductDTO product) {
        return Objects.nonNull(product) && Objects.nonNull(product.getId());
    }

    private float calculateCartProduct(CartDTO cart) {
        BigDecimal sum = BigDecimal.valueOf(cart.getProduct().getPrice()).multiply(new BigDecimal(cart.getCount()));

        return sum.floatValue();
    }

}
