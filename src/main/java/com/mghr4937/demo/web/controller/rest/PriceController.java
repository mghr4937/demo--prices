package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.controller.PriceOperations;
import com.mghr4937.demo.web.controller.util.PriceConverter;
import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class PriceController implements PriceOperations {

    private final PriceRepository repository;
    private final PriceConverter priceConverter;

    @Autowired
    public PriceController(PriceRepository repository, PriceConverter priceConverter) {
        this.repository = repository;
        this.priceConverter = priceConverter;
    }

    @Override
    public List<PriceDto> getAll() {
        var prices = repository.findAll();
        return prices.stream().map(priceConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PriceDto save(@RequestBody PriceDto newPrice) {
        var price = repository.save(priceConverter.convertToEntity(newPrice));
        return priceConverter.toResponse(price);
    }

    @Override
    public PriceDto getPrice(@PathVariable Long id) {
        var price = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return priceConverter.toResponse(price);
    }

    @Override
    public void deleteBrand(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @Override
    public QueryPriceResponseDto getQueryPrice(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime date,
                                               @RequestParam("productId") Long productId,
                                               @RequestParam("brandId") Long brandId) {
        var price = repository.queryPrice(date, productId, brandId)
                .orElseThrow(ResourceNotFoundException::new);
        return priceConverter.convertToQueryPriceResponse(price);

    }



}
