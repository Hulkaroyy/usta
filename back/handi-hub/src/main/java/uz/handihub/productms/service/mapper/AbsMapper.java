package uz.handihub.productms.service.mapper;

import java.util.List;

public interface AbsMapper<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

    List<DTO> toDto(List<ENTITY> entity);

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(List<DTO> dto);
}
