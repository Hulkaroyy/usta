package uz.handihub.productms.service;

import uz.handihub.productms.service.dto.CartDTO;
import uz.handihub.productms.service.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderCartService {

    Optional<OrderDTO> order(List<CartDTO> carts);

}
