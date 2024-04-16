package uz.handihub.productms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.handihub.productms.domain.Content;
import uz.handihub.productms.exceptions.InternalServerErrorException;
import uz.handihub.productms.exceptions.InvalidArgumentException;
import uz.handihub.productms.exceptions.NotFoundException;
import uz.handihub.productms.repository.ContentRepository;
import uz.handihub.productms.service.ContentService;
import uz.handihub.productms.service.ProductService;
import uz.handihub.productms.service.StorageService;
import uz.handihub.productms.service.dto.ContentDTO;
import uz.handihub.productms.service.dto.ContentWithResourceDTO;
import uz.handihub.productms.service.dto.ProductDTO;
import uz.handihub.productms.service.mapper.ContentMapper;
import uz.handihub.productms.service.mapper.ProductMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final StorageService storageService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    @SneakyThrows
    @Override
    public Optional<ContentDTO> save(MultipartFile file, UUID productId, Boolean isMain) {
        log.debug("Start upload content for product. ProductID: {}", productId);

        if (Objects.isNull(productId)) {
            log.warn("Invalid argument is passed! ProductID must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! ProductID must not be null!");
        }

        Optional<ProductDTO> productOptional = productService.getById(productId);
        if (productOptional.isEmpty()) {
            log.warn("Product is not found with given ID! ProductID: {}", productId);
            throw new NotFoundException("Product is not found with given ID!");
        }

        Optional<Resource> storedFileOptional = storageService.store(file);
        if (storedFileOptional.isEmpty()) {
            log.warn("File is not stored!");
            throw new InternalServerErrorException("File is not stored!");
        }

        Content entity = new Content();
        entity.setType(findContentType(file.getContentType()));
        entity.setPhotoUrl(String.valueOf(storedFileOptional.get().getURI()));
        entity.setIsMain(isMain);
        entity.setProduct(productMapper.toEntity(productOptional.get()));

        contentRepository.save(entity);

        log.debug("Content is saved successfully. ContentID: {}", entity.getId());
        return Optional.of(contentMapper.toDto(entity));
    }

    @Override
    public Optional<ContentWithResourceDTO> getResourceByContentId(UUID id) {
        log.debug("Start fetch file resource by content ID. ContentID: {}", id);

        if (Objects.isNull(id)) {
            log.warn("Invalid argument is passed! ContentID must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! ContentID must not be null!");
        }

        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isEmpty()) {
            log.warn("Content is not found by given ID! ContentID: {}", id);
            throw new NotFoundException("Content is not found by given ID!");
        }

        Optional<Resource> resourceOptional = storageService.fetch(contentOptional.get().getPhotoUrl());
        if (resourceOptional.isEmpty()) {
            log.warn("Resource of content is not found!");
            throw new NotFoundException("Resource of content is not found!");
        }

        ContentWithResourceDTO result = new ContentWithResourceDTO()
                .setContent(contentMapper.toDto(contentOptional.get()))
                .setResource(resourceOptional.get());

        return Optional.of(result);
    }

    private MediaType findContentType(String contentTypeAsString) {
        return MediaType.valueOf(contentTypeAsString);
    }
}
