package ru.itq.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itq.productservice.entity.Manufacturer;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findByNameContainingIgnoreCase(String name);
}
