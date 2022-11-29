package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.controller.PriceOperations;
import com.mghr4937.demo.web.controller.util.PriceConverter;
import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
public class PriceController implements PriceOperations {

    private final PriceRepository repository;
    private final PriceConverter priceConverter;

    public PriceController(PriceRepository repository, PriceConverter priceConverter) {
        this.repository = repository;
        this.priceConverter = priceConverter;
    }

    @Override
    public List<PriceDto> getAll() {
        log.info("Getting all Prices");
        var prices = repository.findAll();
        return prices.stream().map(priceConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PriceDto save(@Valid PriceDto newPrice) {
        log.info("Saving Price: {}", newPrice);
        if (newPrice.getStartDate().isBefore(newPrice.getEndDate())) {
            var price = repository.save(priceConverter.convertToEntity(newPrice));
            log.info("Price stored: {}", newPrice);
            return priceConverter.toResponse(price);
        } else {
            throw new IllegalArgumentException("Start date must be Before End date");
        }
    }

    @Override
    public PriceDto getPrice(Long id) {
        log.info("Saving Price Id: {}", id);
        var price = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return priceConverter.toResponse(price);
    }

    @Override
    public void deletePrice(Long id) {
        log.info("Deleting Price Id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public QueryPriceResponseDto getQueryPrice(String date, Long productId, Long brandId) {
        log.info("Querying Price by brandId: {}, productId: {}, date: {}", brandId, productId, date);
        LocalDateTime datetime;
        try {
            log.info("Parsing date: {}", date);
            datetime = LocalDateTime.parse(date);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(ex);
        }

        log.info("Searching in database");
        var price = repository.queryPrice(datetime, productId, brandId)
                .orElseThrow(ResourceNotFoundException::new);
        log.info("Matched Price: {}", price);
        return priceConverter.convertToQueryPriceResponse(price);

    }


}
