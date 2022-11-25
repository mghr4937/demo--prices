package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.web.controller.IBrandRestController;
import com.mghr4937.demo.web.dto.BrandDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BrandRestControllerImpl implements IBrandRestController {

    private final BrandRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    BrandRestControllerImpl(BrandRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BrandDto> getAll() {
        var brands = repository.findAll();
        return brands.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto save(@RequestBody BrandDto newBrand) {
        var brand = repository.save(convertToEntity(newBrand));
        return convertToDto(brand);
    }

    @Override
    public BrandDto getBrand(@PathVariable Long id) {
        var brand = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return convertToDto(brand);
    }

    @Override
    public BrandDto getBrandByName(@Param("name") String name) {
        var brand = repository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
        return convertToDto(brand);
    }

    @Override
    public void deleteBrand(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private BrandDto convertToDto(Brand brand) {
        return modelMapper.map(brand, BrandDto.class);
    }

    private Brand convertToEntity(BrandDto brandDto) throws ParseException {
        return modelMapper.map(brandDto, Brand.class);
    }

}
