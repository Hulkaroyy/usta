package uz.handihub.productms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.handihub.productms.domain.Cart;
import uz.handihub.productms.service.dto.CartDTO;
import uz.handihub.productms.service.dto.CartProductsDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper extends AbsMapper<CartDTO, Cart> {

    List<CartDTO> cartProductsToDto(List<CartProductsDTO> products);
}
