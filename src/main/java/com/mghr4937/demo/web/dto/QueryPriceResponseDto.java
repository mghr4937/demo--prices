package com.mghr4937.demo.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryPriceResponseDto {

    private Long productId;
    private Long brandId;
    private int priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;
}
