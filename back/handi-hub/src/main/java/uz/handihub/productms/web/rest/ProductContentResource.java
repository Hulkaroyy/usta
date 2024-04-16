package uz.handihub.productms.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.handihub.productms.service.ContentService;
import uz.handihub.productms.service.dto.ContentDTO;
import uz.handihub.productms.service.dto.ContentWithResourceDTO;
import uz.handihub.productms.web.rest.utils.ResponseUtils;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductContentResource {

    private final ContentService contentService;

    @PostMapping("/product/{id}/content")
    public ResponseEntity<ContentDTO> save(@PathVariable UUID id,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "is_main", defaultValue = "false") boolean isMain) {
        log.debug("REST request to save content for product");

        Optional<ContentDTO> contentOptional = contentService.save(file, id, isMain);

        ResponseEntity<ContentDTO> response = ResponseUtils.wrap(contentOptional);

        log.debug("Saved content for product result. Response: {}", response);
        return response;
    }

    @GetMapping("/product/content/{id}")
    public ResponseEntity<Resource> getResourceByContentId(@PathVariable UUID id) {
        log.debug("REST request to get resource of content by ID");

        Optional<ContentWithResourceDTO> resourceOptional = contentService.getResourceByContentId(id);

        ResponseEntity<Resource> response = ResponseEntity.notFound().build();
        if (resourceOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(resourceOptional.get().getContent().getType());

            response = ResponseEntity.ok()
                    .headers(headers)
                    .body(resourceOptional.get().getResource());
        }

        log.debug("Resource of content result. Response: {}", response);
        return response;
    }
}
