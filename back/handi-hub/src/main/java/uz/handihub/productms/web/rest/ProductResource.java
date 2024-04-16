package uz.handihub.productms.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.handihub.productms.service.ProductService;
import uz.handihub.productms.service.dto.ProductDTO;
import uz.handihub.productms.web.rest.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO dto) {
        log.debug("REST request to save new product");

        Optional<ProductDTO> productOptional = productService.save(dto);

        ResponseEntity<ProductDTO> response = ResponseUtils.wrap(productOptional);

        log.debug("Saving new product result. Response: {}", response);
        return response;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable UUID id) {
        log.debug("REST request to fetch product by ID");

        Optional<ProductDTO> productOptional = productService.getById(id);

        ResponseEntity<ProductDTO> response = ResponseUtils.wrap(productOptional);

        log.debug("Find product by ID result. Response: {}", response);
        return response;
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> findAll(@ParameterObject Pageable pageable) {
        log.debug("REST request to fetch product by ID");

        Page<ProductDTO> productsPage = productService.findAll(pageable);

        ResponseEntity<List<ProductDTO>> response = ResponseUtils.wrap(productsPage);

        log.debug("Find product by ID result. Response: {}", response);
        return response;
    }
}
