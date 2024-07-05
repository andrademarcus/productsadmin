package com.qima.productsadmin.service;

import com.qima.productsadmin.model.ProductRequest;
import com.qima.productsadmin.model.ProductResponse;
import com.qima.productsadmin.persistence.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> getProducts(String name, String sortBy, String sortOrder, int page, int size);
    ProductResponse getProductById(Long id);
    ProductResponse saveProduct(Product product);
    ProductResponse updateProduct(Product product);
    void deleteProduct(Long id);

}
