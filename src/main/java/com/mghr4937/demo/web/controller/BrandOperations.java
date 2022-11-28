package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.web.dto.BrandDto;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "Brand API")
@RequestMapping("/api/brand")
@Validated
public interface BrandOperations {
    @GetMapping
    List<BrandDto> getAll();

    @PostMapping
    public BrandDto save(@RequestBody BrandDto newBrand);

    @GetMapping("/{id}")
    public BrandDto getBrand(@Valid @PathVariable Long id);

    @GetMapping("/search/findByName")
    public BrandDto getBrandByName(@NotBlank @Valid @RequestParam String name);

    @DeleteMapping("/{id}")
    public void deleteBrand(@NotBlank @Valid @PathVariable Long id);


}
