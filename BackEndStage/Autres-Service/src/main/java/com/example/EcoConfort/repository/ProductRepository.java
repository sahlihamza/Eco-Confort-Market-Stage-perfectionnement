package com.example.EcoConfort.repository;

import com.example.EcoConfort.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long categorieId);
//    List<Product> findByProductName(String nomProduit);

    List<Product> findByProductNameContaining(String productName);

    List<Product> findByBrandContaining(String brand);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByOrderByPriceDesc();
    List<Product> findByOrderByPriceAsc();

    List<Product> findByInStockTrue();

    List<Product> findByInStockFalse();

    //fonction pour admin

    long count();  // Cette méthode compte tous les produits

    long countByInStockTrue();  // Cette méthode compte tous les produits en stock



}
