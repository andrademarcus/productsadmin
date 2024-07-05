package com.qima.productsadmin.persistence.repository;

import com.qima.productsadmin.persistence.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("select a from Product a where" +
//            " (a.name) = (concat('%', :name, '%')) and " +
//            " and (a.description) = (concat('%', :description, '%'))" +
//            " and a.price = :price" +
//            " and (a.categoryPath) = (concat('%', :categoryPath, '%'))" +
//            " and a.available = :available")
//Page<Product> findProducts(String name, String description, Double price,
//                           String categoryPath, boolean available, Pageable pageable);
//    @Query("select a from Product a")
//    Page<Product> findAllProducts(Pageable pageable);


    Page<Product> findByNameContaining(String name, Pageable pageable);

}
