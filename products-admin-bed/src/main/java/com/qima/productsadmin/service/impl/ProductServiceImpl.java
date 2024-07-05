package com.qima.productsadmin.service.impl;

import com.qima.productsadmin.exception.RecordNotFoundException;
import com.qima.productsadmin.model.ProductResponse;
import com.qima.productsadmin.persistence.model.Product;
import com.qima.productsadmin.persistence.repository.ProductRepository;
import com.qima.productsadmin.service.ProductService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getProducts(String name, String sortBy, String sortOrder, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortBy, sortOrder)));

        Product product = new Product();
        product.setName(name);

        final ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(product, matcher);

        return productRepository.findAll(example, pageable);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RecordNotFoundException("Product not found with id: " + id));
    }

    @Override
    public ProductResponse saveProduct(Product product) {
        Product results = productRepository.save(product);
        return mapToResponse(results);
    }

    @Override
    public ProductResponse updateProduct(Product product) {
        Product results = productRepository.save(product);
        return mapToResponse(results);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    private List<Sort.Order> createSortOrder(String sortBy, String sortDirection) {
        Sort.Direction direction = (sortDirection != null) ?
                Sort.Direction.fromString(sortDirection) : Sort.Direction.DESC;
        return List.of(new Sort.Order(direction, sortBy));
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategoryPath(), product.isAvailable());
    }

}
