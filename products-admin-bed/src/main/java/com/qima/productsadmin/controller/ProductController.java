package com.qima.productsadmin.controller;

import com.qima.productsadmin.model.ProductResponse;
import com.qima.productsadmin.model.GenericResponse;
import com.qima.productsadmin.persistence.model.Product;
import com.qima.productsadmin.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Page<Product> getProducts(@RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
                                     @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
                                     @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(name = "size", required = false, defaultValue = "20") int size) {


        return productService.getProducts(name, sortBy, sortOrder, page, size);
    }

    @GetMapping(value = "/product/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ProductResponse getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/product/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse> deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        GenericResponse response = new GenericResponse("Successfully deleted product with id: " + id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/product/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductResponse updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PostMapping(value = "/product/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductResponse saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

}
