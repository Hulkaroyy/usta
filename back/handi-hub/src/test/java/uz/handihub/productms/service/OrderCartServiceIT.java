package uz.handihub.productms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uz.handihub.productms.domain.Product;
import uz.handihub.productms.domain.enumeration.Category;
import uz.handihub.productms.domain.enumeration.OrderStatus;
import uz.handihub.productms.repository.CartRepository;
import uz.handihub.productms.repository.OrderRepository;
import uz.handihub.productms.repository.ProductRepository;
import uz.handihub.productms.security.SecurityUtils;
import uz.handihub.productms.service.dto.CartDTO;
import uz.handihub.productms.service.mapper.ProductMapper;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class OrderCartServiceIT {
    private static final UUID USER_ID = UUID.fromString("28d69baa-c666-42c9-8202-cca96c5fc018");
    private static final String DEFAULT_PRODUCT_NAME = "Sculpture";
    private static final Float DEFAULT_PRODUCT_PRICE = 15000f;
    private static final Category DEFAULT_PRODUCT_CATEGORY = Category.SCULPTURE;

    @Autowired
    private OrderCartService service;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Test
    @Transactional
    public void testSaveOrderWithCarts() {

        MockedStatic<SecurityUtils> mockedSecurityUtils = Mockito.mockStatic(SecurityUtils.class);
        mockedSecurityUtils.when(SecurityUtils::getUserId).thenReturn(USER_ID);

        var product = productMapper.toDto(generateSavedDummyProduct());
        var productCount = 5;

        var cart = new CartDTO();
        cart.setCount(productCount);
        cart.setProduct(product);

        var carts = List.of(cart);

        var orderOptional = service.order(carts);
        Assertions.assertNotNull(orderOptional);
        Assertions.assertTrue(orderOptional.isPresent());
        Assertions.assertNotNull(orderOptional.get().getCreatedAt());
        Assertions.assertEquals(OrderStatus.NOT_PAID, orderOptional.get().getStatus());
        Assertions.assertEquals(USER_ID, orderOptional.get().getCreatedBy());
        Assertions.assertEquals(productCount * product.getPrice(), orderOptional.get().getTotalAmount());
        Assertions.assertEquals(carts.size(), orderOptional.get().getCarts().size());

        var orderCart = orderOptional.get().getCarts().get(0);
        var cartOptional = cartRepository.findById(orderCart.getId());
        Assertions.assertNotNull(cartOptional);
        Assertions.assertTrue(cartOptional.isPresent());
        Assertions.assertEquals(USER_ID, cartOptional.get().getUserId());
        Assertions.assertEquals(productCount, cartOptional.get().getCount());

        System.out.println(orderOptional.get());
        System.out.println(cartOptional);

        mockedSecurityUtils.close();
    }


    private Product generateDummyProduct() {
        var product = new Product();

        product.setName(DEFAULT_PRODUCT_NAME);
        product.setPrice(DEFAULT_PRODUCT_PRICE);
        product.setCategory(DEFAULT_PRODUCT_CATEGORY);
        product.setCreatedBy(USER_ID);

        return product;
    }

    private Product generateSavedDummyProduct() {
        var product = generateDummyProduct();

        return productRepository.save(product);
    }

}
