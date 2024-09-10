package ru.itq.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itq.productservice.entity.Product;
import ru.itq.productservice.entity.enums.ProductStatus;
import ru.itq.productservice.exception.NotFoundException;
import ru.itq.productservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new NotFoundException("Product with id " + id + " not found");
                });
    }

    public List<Product> getProductsByName(String name) {
        log.info("Searching products by name: {}", name);
        return productRepository.findByNameContaining(name);
    }

    public List<Product> getProductsByStatus(ProductStatus status) {
        log.info("Fetching products by status: {}", status);
        return productRepository.findByStatus(status);
    }

    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Fetching products with price range from {} to {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);

    }

    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating new product: {}", product.getName());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        log.info("Updating product with id: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setManufacturer(updatedProduct.getManufacturer());
                    product.setStatus(updatedProduct.getStatus());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> {
                    log.error("Product with id {} not found for update", id);
                    return new NotFoundException("Product with id " + id + " not found");
                });
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product with id {} not found for deletion", id);
                    return new NotFoundException("Product with id " + id + " not found");
                });
        productRepository.deleteById(id);
    }
}
