package ru.itq.productservice.mapper;

import org.mapstruct.Mapper;
import ru.itq.productservice.dto.ProductDto;
import ru.itq.productservice.entity.Product;

@Mapper(componentModel = "spring", uses = {ManufacturerMapper.class})
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toProductEntity(ProductDto productDto);
}
