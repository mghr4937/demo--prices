package com.mghr4937.demo.web.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * A DTO for the {@link com.mghr4937.demo.model.Price} entity
 */
@Data
public class PriceDto implements Serializable {
    private final Long id;
    private final BrandDto brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int priceList;
    private final long productId;
    private final int priority;
    private final double price;
    @Length(max = 3)
    private final Currency currency;
}