package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.web.dto.BrandDto;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/brand")
public interface IBrandRestController {
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
