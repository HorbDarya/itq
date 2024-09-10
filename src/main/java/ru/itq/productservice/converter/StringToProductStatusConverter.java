package ru.itq.productservice.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.itq.productservice.entity.enums.ProductStatus;

@Component
@Slf4j
public class StringToProductStatusConverter implements Converter<String, ProductStatus> {
    @Override
    public ProductStatus convert(String source) {
        try {
            return ProductStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Cannot convert '{}' to ProductStatus. Error: {}", source, e.getMessage());
            return null;
        }
    }
}
