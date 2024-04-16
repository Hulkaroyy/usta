package uz.handihub.productms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.handihub.productms.domain.Product;
import uz.handihub.productms.service.dto.ProductDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper extends AbsMapper<ProductDTO, Product> {
}
