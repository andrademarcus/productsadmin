package com.qima.productsadmin.model;

public record ProductResponse(Long id, String name, String description, Double price, String categoryPath, boolean available) {

}
