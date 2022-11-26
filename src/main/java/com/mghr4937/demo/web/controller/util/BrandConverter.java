package com.mghr4937.demo.web.controller.util;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.web.dto.BrandDto;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter {

    private final ModelMapper modelMapper;

    public BrandConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BrandDto toResponse(Brand brand) {
        return modelMapper.map(brand, BrandDto.class);
    }


    public Brand convertToEntity(BrandDto brandDto) throws ParseException {
        return modelMapper.map(brandDto, Brand.class);
    }
}
