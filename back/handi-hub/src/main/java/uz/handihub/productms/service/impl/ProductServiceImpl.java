package uz.handihub.productms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.handihub.productms.domain.Product;
import uz.handihub.productms.exceptions.InvalidArgumentException;
import uz.handihub.productms.exceptions.NotFoundException;
import uz.handihub.productms.exceptions.NotImplementedException;
import uz.handihub.productms.repository.ProductRepository;
import uz.handihub.productms.security.SecurityUtils;
import uz.handihub.productms.service.ProductService;
import uz.handihub.productms.service.dto.ProductDTO;
import uz.handihub.productms.service.mapper.ProductMapper;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Optional<ProductDTO> save(ProductDTO dto) {
        log.debug("Start save new product. ProductDTO: {}", dto);

        if (Objects.isNull(dto)) {
            log.warn("Invalid argument is passed! ProductDTO must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! ProductDTO must not be null!");
        }

        UUID userId = SecurityUtils.getUserId();

        Product product = mapper.toEntity(dto);
        product.setId(null);
        // comments and contents should attach via another API
        product.setContents(null);
        product.setComments(null);
        product.setCreatedBy(userId);
        product.setCreatedAt(Instant.now());

        repository.save(product);

        log.debug("Product is saved successfully. Product: {}", product);
        return Optional.of(mapper.toDto(product));
    }

    @Override
    public Optional<ProductDTO> update(ProductDTO dto) {
        throw new NotImplementedException("Update method of ProductService is not implemented yet!");
    }

    @Override
    public Optional<ProductDTO> getById(UUID id) {
        log.debug("Start find product by ID. ID: {}", id);

        if (Objects.isNull(id)) {
            log.warn("Invalid argument is passed! ID must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! ID must not be null!");
        }

        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product is not found with given ID!"));

        log.debug("Product is fetched successfully. Product: {}", product);
        return Optional.of(mapper.toDto(product));
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Start fetch products like batch. Pageable: {}", pageable);

        if (Objects.isNull(pageable)) {
            log.warn("Invalid argument is passed! Pageable must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! Pageable must not be null!");
        }

        Page<ProductDTO> productsPage = repository.findAll(pageable)
                .map(mapper::toDto);

        log.debug("Products are fetched successfully like batch");
        return productsPage;
    }
}
