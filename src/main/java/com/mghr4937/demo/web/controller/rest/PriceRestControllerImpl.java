package com.mghr4937.demo.web.controller.rest;

import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.controller.PriceRestController;
import com.mghr4937.demo.web.dto.PriceDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PriceRestControllerImpl implements PriceRestController {

    private final PriceRepository repository;
    private final ModelMapper modelMapper;

    public PriceRestControllerImpl(PriceRepository repository, ModelMapper modelMapper) {
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
    public PriceDto save(PriceDto newPrice) {
        var price = repository.save(convertToEntity(newPrice));
        return convertToDto(price);
    }

    @Override
    public PriceDto getPrice(Long id) {
        var price = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return convertToDto(price);
    }

    @Override
    public void deleteBrand(Long id) {
        repository.deleteById(id);
    }

    private PriceDto convertToDto(Price price) {
        return modelMapper.map(price, PriceDto.class);
    }

    private Price convertToEntity(PriceDto priceDto) throws ParseException {
        return modelMapper.map(priceDto, Price.class);
    }
}
