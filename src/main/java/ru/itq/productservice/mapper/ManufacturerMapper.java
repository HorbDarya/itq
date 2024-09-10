package ru.itq.productservice.mapper;

import org.mapstruct.Mapper;
import ru.itq.productservice.dto.ManufacturerDto;
import ru.itq.productservice.entity.Manufacturer;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper {
    ManufacturerDto toManufacturerDto(Manufacturer manufacturer);
    Manufacturer toManufacturerEntity(ManufacturerDto manufacturerDto);
}
