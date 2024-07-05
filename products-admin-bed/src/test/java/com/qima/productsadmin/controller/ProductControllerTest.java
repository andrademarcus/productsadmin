package com.qima.productsadmin.controller;

import com.qima.productsadmin.model.GenericResponse;
import com.qima.productsadmin.model.ProductResponse;
import com.qima.productsadmin.persistence.model.Product;
import com.qima.productsadmin.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getProductsReturnsPageOfProducts() {
        Page<Product> expectedPage = new PageImpl<>(Collections.singletonList(new Product()));
        when(productService.getProducts(null, "id", "desc", 0, 20)).thenReturn(expectedPage);

        Page<Product> result = productController.getProducts(null, "id", "desc", 0, 20);

        assertEquals(expectedPage, result);
    }

    @Test
    void getProductByIdReturnsProductResponse() {
        ProductResponse expectedResponse = new ProductResponse(1L, "name", "description", 10.0, "categoryPath", true);
        when(productService.getProductById(1L)).thenReturn(expectedResponse);

        ProductResponse result = productController.getProductById(1L);

        assertEquals(expectedResponse, result);
    }

    @Test
    void deleteProductByIdReturnsSuccessResponse() {
        GenericResponse expectedResponse = new GenericResponse("Successfully deleted product with id: 1");

        ResponseEntity<GenericResponse> result = productController.deleteProductById(1L);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(expectedResponse.getMessage(), result.getBody().getMessage());
    }

    @Test
    void updateProductReturnsUpdatedProductResponse() {
        Product productToUpdate = new Product();
        ProductResponse expectedResponse = new ProductResponse(1L, "name", "description", 10.0, "categoryPath", true);
        when(productService.updateProduct(productToUpdate)).thenReturn(expectedResponse);

        ProductResponse result = productController.updateProduct(productToUpdate);

        assertEquals(expectedResponse, result);
    }

    @Test
    void saveProductReturnsSavedProductResponse() {
        Product productToSave = new Product();
        ProductResponse expectedResponse = new ProductResponse(1L, "name", "description", 10.0, "categoryPath", true);
        when(productService.saveProduct(productToSave)).thenReturn(expectedResponse);

        ProductResponse result = productController.saveProduct(productToSave);

        assertEquals(expectedResponse, result);
    }
}