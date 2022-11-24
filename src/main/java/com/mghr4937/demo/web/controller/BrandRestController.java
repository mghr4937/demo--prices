package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.model.Brand;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/brand")
public interface BrandRestController {
    @GetMapping("")
    List<Brand> getAll();

    @PostMapping("")
    public Brand save(@RequestBody Brand newBrand);

    @GetMapping("/{id}")
    public Brand getBrand(@PathVariable Long id);

    @GetMapping("/search/findByName")
    public Brand getBrandByName(@Param("name") String name);

    @PutMapping("/{id}")
    public Brand replaceBrand(@RequestBody Brand newBrand, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id);


}
