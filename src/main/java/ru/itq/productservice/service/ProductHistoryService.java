package ru.itq.productservice.service;

import java.util.List;

public interface ProductHistoryService {
    List<Object[]> getProductRevisions(Long productId);
}
