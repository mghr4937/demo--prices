package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.model.Price;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/price")
public interface PriceRestController {

    @GetMapping("")
    List<Price> getAll();

    @PostMapping("")
    public Price save(@RequestBody Price newPrice);

    @GetMapping("/{id}")
    public Price getPrice(@PathVariable Long id);

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id);

}
