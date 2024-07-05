package com.qima.productsadmin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryPath;
    private boolean available;
}
