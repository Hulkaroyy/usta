package uz.handihub.productms.service;

import org.springframework.web.multipart.MultipartFile;
import uz.handihub.productms.service.dto.ContentDTO;
import uz.handihub.productms.service.dto.ContentWithResourceDTO;

import java.util.Optional;
import java.util.UUID;

public interface ContentService {

    Optional<ContentDTO> save(MultipartFile file, UUID productId, Boolean isMain);

    Optional<ContentWithResourceDTO> getResourceByContentId(UUID id);

}
