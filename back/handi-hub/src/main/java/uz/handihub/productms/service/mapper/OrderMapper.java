package uz.handihub.productms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.handihub.productms.domain.Order;
import uz.handihub.productms.service.dto.OrderDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper extends AbsMapper<OrderDTO, Order> {
}
