package com.mghr4937.demo.web.controller.util;

import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.web.dto.PriceDto;
import com.mghr4937.demo.web.dto.QueryPriceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PriceConverter {

    private final ModelMapper modelMapper;

    public PriceConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PriceDto toResponse(Price price) throws ParseException {
        return modelMapper.map(price, PriceDto.class);
    }

    public Price convertToEntity(PriceDto PriceDto) throws ParseException {
        return modelMapper.map(PriceDto, Price.class);
    }

    public QueryPriceResponseDto convertToQueryPriceResponse(Price price) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(price, QueryPriceResponseDto.class);
    }
}
