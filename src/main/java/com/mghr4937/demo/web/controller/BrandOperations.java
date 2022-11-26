package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.web.dto.BrandDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Brand API")
@RequestMapping("/api/brand")
public interface BrandOperations {
    @GetMapping("")
    List<BrandDto> getAll();

    @PostMapping("")
    public BrandDto save(@RequestBody BrandDto newBrand);

    @GetMapping("/{id}")
    public BrandDto getBrand(@PathVariable Long id);

    @GetMapping("/search/findByName")
    public BrandDto getBrandByName(@RequestParam("name") String name);

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id);


}
