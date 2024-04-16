package uz.handihub.productms.service;

import uz.handihub.productms.service.dto.CartDTO;

import java.util.List;
import java.util.Optional;

public interface CartService {

    Optional<CartDTO> save(CartDTO dto);

    List<CartDTO> save(List<CartDTO> dtos);

}
