package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.web.dto.PriceDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/price")
public interface PriceRestController {

    @GetMapping("")
    List<PriceDto> getAll();

    @PostMapping("")
    public PriceDto save(@RequestBody PriceDto newPrice);

    @GetMapping("/{id}")
    public PriceDto getPrice(@PathVariable Long id);

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id);

}
