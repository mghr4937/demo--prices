package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.web.controller.BrandOperations;
import com.mghr4937.demo.web.controller.util.BrandConverter;
import com.mghr4937.demo.web.dto.BrandDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
public class BrandController implements BrandOperations {

    private final BrandRepository repository;
    private final BrandConverter brandConverter;

    BrandController(BrandRepository repository, BrandConverter brandConverter) {
        this.repository = repository;
        this.brandConverter = brandConverter;
    }

    @Override
    public List<BrandDto> getAll() {
        log.info("Getting all Brands");
        var brands = repository.findAll();
        return brands.stream().map(brandConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto save(@RequestBody BrandDto newBrand) {
        log.info("Saving Brand: {}", newBrand);
        var brand = repository.save(brandConverter.convertToEntity(newBrand));
        return brandConverter.toResponse(brand);
    }

    @Override
    public BrandDto getBrand(@PathVariable Long id) {
        log.info("Getting Brand by Id: {}", id);
        var brand = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return brandConverter.toResponse(brand);
    }

    @Override
    public BrandDto getBrandByName(String name) {
        log.info("Getting Brand by Name: {}", name);
        var brand = repository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
        return brandConverter.toResponse(brand);
    }

    @Override
    public void deleteBrand(@PathVariable Long id) {
        log.info("Deleting Brand by Id: {}", id);
        repository.deleteById(id);
    }


}

