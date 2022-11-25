package com.mghr4937.demo.web.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * A DTO for the {@link com.mghr4937.demo.model.Price} entity
 */
@Data
public class PriceDto {
    private Long id;
    private BrandDto brand;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priceList;
    private long productId;
    private int priority;
    private double price;
    @Length(max = 3)
    private Currency currency;
}