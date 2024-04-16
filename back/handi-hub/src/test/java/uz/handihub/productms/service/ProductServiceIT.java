package uz.handihub.productms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import uz.handihub.productms.domain.Comment;
import uz.handihub.productms.domain.Content;
import uz.handihub.productms.domain.Product;
import uz.handihub.productms.domain.enumeration.Category;
import uz.handihub.productms.exceptions.InvalidArgumentException;
import uz.handihub.productms.exceptions.NotFoundException;
import uz.handihub.productms.exceptions.NotImplementedException;
import uz.handihub.productms.repository.ProductRepository;
import uz.handihub.productms.security.SecurityUtils;
import uz.handihub.productms.service.dto.CommentDTO;
import uz.handihub.productms.service.dto.ContentDTO;
import uz.handihub.productms.service.dto.ProductDTO;
import uz.handihub.productms.service.mapper.ProductMapper;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ProductServiceIT {
    private static final UUID USER_ID = UUID.fromString("28d69baa-c666-42c9-8202-cca96c5fc018");
    private static final String DEFAULT_PRODUCT_NAME = "Sculpture";
    private static final Float DEFAULT_PRODUCT_PRICE = 15000f;
    private static final Category DEFAULT_PRODUCT_CATEGORY = Category.SCULPTURE;

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Test
    @Transactional
    public void testSaveProductWithNullDto() {

        Assertions.assertThrows(InvalidArgumentException.class, () -> service.save(null));

    }

    @Test
    @Transactional
    public void testSaveProductWithValidParams() {

        var initialProductsCount = repository.count();

        var comment = new CommentDTO();
        comment.setMessage("Demo comment");
        comment.setCreatedAt(Instant.now());
        comment.setCreatedBy(USER_ID);

        var content = new ContentDTO();
        content.setPhotoUrl("URL of location of photo");
        content.setType(MediaType.IMAGE_PNG);

        var product = generateDummyProductDto();
        product.setComments(List.of(comment));
        product.setContents(List.of(content));

        MockedStatic<SecurityUtils> mockedSecurityUtils = Mockito.mockStatic(SecurityUtils.class);
        mockedSecurityUtils.when(SecurityUtils::getUserId).thenReturn(USER_ID);

        var responseOptional = service.save(product);

        Assertions.assertNotNull(responseOptional);
        Assertions.assertTrue(responseOptional.isPresent());
        Assertions.assertNotNull(responseOptional.get().getId());
        Assertions.assertNotNull(responseOptional.get().getCreatedAt());
        Assertions.assertNotNull(responseOptional.get().getCreatedBy());
        Assertions.assertEquals(USER_ID, responseOptional.get().getCreatedBy());
        Assertions.assertNull(responseOptional.get().getContents());
        Assertions.assertNull(responseOptional.get().getComments());

        var productsCountAfterRequest = repository.count();
        Assertions.assertNotEquals(initialProductsCount, productsCountAfterRequest);

        mockedSecurityUtils.close();

        System.out.println(responseOptional.get());
    }

    @Test
    @Transactional
    public void testUpdateProduct() {

        var product = generateDummyProductDto();

        Assertions.assertThrows(NotImplementedException.class, () -> service.update(product));

    }

    @Test
    @Transactional
    public void testGetByIdWithNullParam() {

        Assertions.assertThrows(InvalidArgumentException.class, () -> service.getById(null));

    }

    @Test
    @Transactional
    public void testGetByIdWithNotExistedProductId() {

        var product = generateSavedDummyProduct();

        var notExistedProductId = UUID.randomUUID();

        Assertions.assertNotEquals(product.getId(), notExistedProductId);
        Assertions.assertThrows(NotFoundException.class, () -> service.getById(notExistedProductId));

    }

    @Test
    @Transactional
    public void testGetByIdWithValidParams() {

        var product = generateSavedDummyProduct();

        var responseOptional = service.getById(product.getId());
        Assertions.assertNotNull(responseOptional);
        Assertions.assertTrue(responseOptional.isPresent());
        Assertions.assertEquals(product.getId(), responseOptional.get().getId());

    }

    @Test
    @Transactional
    public void testGetByIdWithContentAndCommentObjects() {

        var comment = new Comment();
        comment.setMessage("Demo comment");
        comment.setCreatedAt(Instant.now());
        comment.setCreatedBy(USER_ID);

        var content = new Content();
        content.setPhotoUrl("URL of location of photo");
        content.setType(MediaType.IMAGE_PNG);

        var product = generateDummyProduct();
        product.setComments(List.of(comment));
        product.setContents(List.of(content));

        repository.save(product);

        var responseOptional = service.getById(product.getId());
        Assertions.assertNotNull(responseOptional);
        Assertions.assertTrue(responseOptional.isPresent());
        Assertions.assertEquals(product.getId(), responseOptional.get().getId());
        Assertions.assertNotNull(responseOptional.get().getContents());
        Assertions.assertFalse(responseOptional.get().getContents().isEmpty());
        Assertions.assertNotNull(responseOptional.get().getComments());
        Assertions.assertFalse(responseOptional.get().getComments().isEmpty());

        System.out.println(responseOptional.get());

    }

    private Product generateDummyProduct() {
        var product = new Product();

        product.setName(DEFAULT_PRODUCT_NAME);
        product.setPrice(DEFAULT_PRODUCT_PRICE);
        product.setCategory(DEFAULT_PRODUCT_CATEGORY);

        return product;
    }

    private Product generateSavedDummyProduct() {
        var product = generateDummyProduct();

        return repository.save(product);
    }

    private ProductDTO generateDummyProductDto() {
        var product = generateDummyProduct();

        return mapper.toDto(product);
    }
}
