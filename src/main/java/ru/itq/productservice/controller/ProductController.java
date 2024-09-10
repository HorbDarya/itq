package ru.itq.productservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itq.productservice.dto.ProductDto;
import ru.itq.productservice.entity.Product;
import ru.itq.productservice.entity.enums.ProductStatus;
import ru.itq.productservice.mapper.ProductMapper;
import ru.itq.productservice.service.ProductHistoryService;
import ru.itq.productservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductHistoryService productHistoryService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.getProductsByName(name);
        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ProductDto>> getProductsByStatus(@RequestParam ProductStatus status) {
        List<Product> products = productService.getProductsByStatus(status);
        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.toProductEntity(productDto);
        Product createdProduct = productService.createProduct(product);
        ProductDto createdProductDto = productMapper.toProductDto(createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            Product product = productMapper.toProductEntity(productDto);
            Product updatedProduct = productService.updateProduct(id, product);
            ProductDto updatedProductDto = productMapper.toProductDto(updatedProduct);
            return ResponseEntity.ok(updatedProductDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<Object[]> getProductHistory(@PathVariable Long id) {
        return productHistoryService.getProductRevisions(id);
    }
}

