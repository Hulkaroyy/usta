package uz.handihub.productms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.handihub.productms.domain.Order;
import uz.handihub.productms.exceptions.InvalidArgumentException;
import uz.handihub.productms.exceptions.NotFoundException;
import uz.handihub.productms.repository.OrderRepository;
import uz.handihub.productms.service.OrderService;
import uz.handihub.productms.service.dto.OrderDTO;
import uz.handihub.productms.service.mapper.OrderMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public Optional<OrderDTO> save(OrderDTO dto) {
        log.debug("Start save new order");

        if (Objects.isNull(dto)) {
            log.warn("Invalid argument is passed! OrderDTO must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! OrderDTO must not be null!");
        }

        Order entity = mapper.toEntity(dto);
        entity.setId(null);

        repository.save(entity);

        log.debug("Oder is saved successfully. Order: {}", entity);
        return Optional.of(mapper.toDto(entity));
    }

    @Override
    public Optional<OrderDTO> getById(UUID id) {
        log.debug("Start fetch order by ID. OrderID: {}", id);

        if (Objects.isNull(id)) {
            log.warn("Invalid argument is passed! ID must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! ID must not be null!");
        }

        Optional<Order> orderOptional = repository.findById(id);
        if (orderOptional.isEmpty()) {
            log.warn("Order is not found by given ID! ID: {}", id);
            throw new NotFoundException("Order is not found by given ID!");
        }

        log.debug("Order is fetched successfully. Order: {}", orderOptional.get());
        return Optional.of(mapper.toDto(orderOptional.get()));
    }

    @Override
    public Page<OrderDTO> getUserOrders(UUID userId, Pageable pageable) {
        log.debug("Start fetch orders of user. UserID: {}", userId);

        if (Objects.isNull(userId)) {
            log.warn("Invalid argument is passed! UserID must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! UserID must not be null!");
        }

        if (Objects.isNull(pageable)) {
            log.warn("Invalid argument is passed! Pageable must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! Pageable must not be null!");
        }

        Page<OrderDTO> ordersPage = repository.findByCreatedBy(userId, pageable)
                .map(mapper::toDto);

        log.debug("Orders of user are fetched successfully");
        return ordersPage;
    }
}
