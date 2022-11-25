package com.mghr4937.demo.web.controller;

import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
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
    public QueryPriceResponseDto getQueryPrice(@ApiParam(value = "yyyy-MM-dd HH:mm:ss", example = "2022-06-01 00:00:00")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @RequestParam("date") LocalDateTime date,
                                               @RequestParam("productId") Long productId,
                                               @RequestParam("brandId") Long brandId);

}
