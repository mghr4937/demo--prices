package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/price")
public interface IPriceRestController {

    @GetMapping("")
    List<PriceDto> getAll();

    @PostMapping("")
    public PriceDto save(@RequestBody PriceDto newPrice);

    @GetMapping("/{id}")
    public PriceDto getPrice(@PathVariable Long id);

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id);

    @GetMapping("/queryPrice")
    public QueryPriceResponseDto getQueryPrice(@Param("date") LocalDateTime date, @Param("productId") Long productId, @Param("brandId") Long brandId);

}
