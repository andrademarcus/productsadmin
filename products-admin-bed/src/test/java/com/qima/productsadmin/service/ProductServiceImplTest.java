package com.qima.productsadmin.service;

import com.qima.productsadmin.exception.RecordNotFoundException;
import com.qima.productsadmin.model.ProductResponse;
import com.qima.productsadmin.persistence.model.Product;
import com.qima.productsadmin.persistence.repository.ProductRepository;
import com.qima.productsadmin.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductsReturnsNonEmptyPageOnValidCriteria() {
        Page<Product> expectedPage = new PageImpl<>(Collections.singletonList(new Product()));
        when(productRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(expectedPage);

        Page<Product> result = productService.getProducts("test", "id", "desc", 0, 20);

        assertFalse(result.isEmpty());
    }

    @Test
    void getProductByIdReturnsProductResponseOnExistingId() {
        Product product = new Product();
        product.setId(1L);
        product.setAvailable(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void getProductByIdThrowsRecordNotFoundExceptionOnNonExistingId() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void saveProductReturnsProductResponseOnValidProduct() {
        Product product = new Product();
        product.setAvailable(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse result = productService.saveProduct(new Product());

        assertNotNull(result);
    }

    @Test
    void updateProductReturnsUpdatedProductResponse() {
        Product product = new Product();
        product.setId(1L);
        product.setAvailable(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse result = productService.updateProduct(product);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void deleteProductThrowsRecordNotFoundExceptionOnNonExistingId() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct(999L));
    }
}
