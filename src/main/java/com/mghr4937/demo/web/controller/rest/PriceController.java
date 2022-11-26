package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.controller.PriceOperations;
import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.expression.ParseException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class PriceController implements PriceOperations {

    private final PriceRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PriceController(PriceRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PriceDto> getAll() {
        var prices = repository.findAll();
        return prices.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PriceDto save(@RequestBody PriceDto newPrice) {
        var price = repository.save(convertToEntity(newPrice));
        return convertToDto(price);
    }

    @Override
    public PriceDto getPrice(@PathVariable Long id) {
        var price = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return convertToDto(price);
    }

    @Override
    public QueryPriceResponseDto getQueryPrice(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime date,
                                               @RequestParam("productId") Long productId,
                                               @RequestParam("brandId") Long brandId) {
        var price = repository.queryPrice(date, productId, brandId)
                .orElseThrow(ResourceNotFoundException::new);
        return convertToQueryPriceResponseDto(price);

    }

    @Override
    public void deleteBrand(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private PriceDto convertToDto(Price price) {
        return modelMapper.map(price, PriceDto.class);
    }

    private Price convertToEntity(PriceDto priceDto) throws ParseException {
        return modelMapper.map(priceDto, Price.class);
    }

    private QueryPriceResponseDto convertToQueryPriceResponseDto(Price price) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(price, QueryPriceResponseDto.class);
    }

}
