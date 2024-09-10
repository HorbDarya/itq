package ru.itq.productservice.service;

import ru.itq.productservice.entity.Product;
import ru.itq.productservice.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByStatus(ProductStatus status);

    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product updatedProduct);

    void deleteProduct(Long id);
}
