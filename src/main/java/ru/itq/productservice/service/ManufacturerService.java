package ru.itq.productservice.service;

import ru.itq.productservice.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {
    List<ManufacturerDto> getAllManufacturers();
    ManufacturerDto getManufacturerById(Long id);
    ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto);
    ManufacturerDto updateManufacturer(Long id, ManufacturerDto manufacturerDto);
    void deleteManufacturer(Long id);
    List<ManufacturerDto> searchManufacturersByName(String name);
}
