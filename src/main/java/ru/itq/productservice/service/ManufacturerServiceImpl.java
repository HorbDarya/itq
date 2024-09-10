package ru.itq.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itq.productservice.dto.ManufacturerDto;
import ru.itq.productservice.entity.Manufacturer;
import ru.itq.productservice.exception.NotFoundException;
import ru.itq.productservice.mapper.ManufacturerMapper;
import ru.itq.productservice.repository.ManufacturerRepository;
import ru.itq.productservice.service.constant.UtilConstant;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    public List<ManufacturerDto> getAllManufacturers() {
        log.info("Fetching all manufacturers");
        return manufacturerRepository.findAll().stream()
                .map(manufacturerMapper::toManufacturerDto)
                .collect(Collectors.toList());
    }

    public ManufacturerDto getManufacturerById(Long id) {
        log.info("Fetching manufacturer with id: {}", id);
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(UtilConstant.MANUFACTURER_ID_NOT_FOUND, id);
                    return new NotFoundException(UtilConstant.MANUFACTURER_NOT_FOUND);
                });
        return manufacturerMapper.toManufacturerDto(manufacturer);
    }

    @Transactional
    public ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) {
        log.info("Creating new manufacturer: {}", manufacturerDto);
        Manufacturer manufacturer = manufacturerMapper.toManufacturerEntity(manufacturerDto);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        log.info("Manufacturer created with id: {}", savedManufacturer.getId());
        return manufacturerMapper.toManufacturerDto(savedManufacturer);
    }

    @Transactional
    public ManufacturerDto updateManufacturer(Long id, ManufacturerDto manufacturerDto) {
        log.info("Updating manufacturer with id: {}", id);
        Manufacturer existingManufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(UtilConstant.MANUFACTURER_ID_NOT_FOUND, id);
                    return new NotFoundException(UtilConstant.MANUFACTURER_NOT_FOUND);
                });
        existingManufacturer.setName(manufacturerDto.getName());
        existingManufacturer.setCountry(manufacturerDto.getCountry());
        existingManufacturer.setWebsite(manufacturerDto.getWebsite());
        Manufacturer updatedManufacturer = manufacturerRepository.save(existingManufacturer);
        log.info("Manufacturer updated with id: {}", updatedManufacturer.getId());
        return manufacturerMapper.toManufacturerDto(updatedManufacturer);
    }

    @Transactional
    public void deleteManufacturer(Long id) {
        log.info("Deleting manufacturer with id: {}", id);
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(UtilConstant.MANUFACTURER_ID_NOT_FOUND, id);
                    return new NotFoundException(UtilConstant.MANUFACTURER_NOT_FOUND);
                });
        manufacturerRepository.delete(manufacturer);
        log.info("Manufacturer deleted with id: {}", id);
    }

    public List<ManufacturerDto> searchManufacturersByName(String name) {
        log.info("Searching manufacturers by name: {}", name);
        return manufacturerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(manufacturerMapper::toManufacturerDto)
                .collect(Collectors.toList());
    }
}
