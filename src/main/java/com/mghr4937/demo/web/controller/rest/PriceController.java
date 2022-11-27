package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.controller.PriceOperations;
import com.mghr4937.demo.web.controller.util.PriceConverter;
import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class PriceController implements PriceOperations {

    private final PriceRepository repository;
    private final PriceConverter priceConverter;

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
    public PriceDto save(PriceDto newPrice) {
        var price = repository.save(priceConverter.convertToEntity(newPrice));
        return priceConverter.toResponse(price);
    }

    @Override
    public PriceDto getPrice(Long id) {
        var price = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return priceConverter.toResponse(price);
    }

    @Override
    public void deleteBrand(Long id) {
        repository.deleteById(id);
    }

    @Override
    public QueryPriceResponseDto getQueryPrice(String date, Long productId, Long brandId) {
        var price = repository.queryPrice(LocalDateTime.parse(date), productId, brandId)
                .orElseThrow(ResourceNotFoundException::new);

        return priceConverter.convertToQueryPriceResponse(price);

    }



}
