package uz.handihub.productms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.handihub.productms.domain.Comment;
import uz.handihub.productms.service.dto.CommentDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper extends AbsMapper<CommentDTO, Comment> {
}
