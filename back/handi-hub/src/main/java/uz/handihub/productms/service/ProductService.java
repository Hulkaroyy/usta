package uz.handihub.productms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.handihub.productms.service.dto.ProductDTO;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    Optional<ProductDTO> save(ProductDTO dto);

    Optional<ProductDTO> update(ProductDTO dto);

    Optional<ProductDTO> getById(UUID id);

    Page<ProductDTO> findAll(Pageable pageable);

}
