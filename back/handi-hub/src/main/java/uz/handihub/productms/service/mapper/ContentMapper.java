package uz.handihub.productms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.handihub.productms.domain.Comment;
import uz.handihub.productms.domain.Content;
import uz.handihub.productms.service.dto.CommentDTO;
import uz.handihub.productms.service.dto.ContentDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContentMapper extends AbsMapper<CommentDTO, Comment> {

    @Mapping(target = "product.contents", ignore = true)
    ContentDTO toDto(Content entity);

}
