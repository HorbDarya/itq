package ru.itq.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itq.productservice.entity.Product;
import ru.itq.productservice.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
