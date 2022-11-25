package com.mghr4937.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Currency;

/**
 * A DTO for the {@link com.mghr4937.demo.model.Price} entity
 */
@Data
public class PriceDto {
    @ApiModelProperty(example = "1")
    private Long id;

    private BrandDto brand;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    @ApiModelProperty(required = true, value = "yyyy-MM-dd-HH:mm:ss", example = "2022-06-01-00:00:00")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    @ApiModelProperty(required = true, value = "yyyy-MM-dd-HH:mm:ss", example = "2022-06-01-00:00:00")
    private LocalDateTime endDate;

    @ApiModelProperty(example = "1")
    private int priceList;

    @ApiModelProperty(example = "ZARA")
    private long productId;

    @ApiModelProperty(example = "1")
    private int priority;

    @ApiModelProperty(example = "99.99")
    private Float price;

    @Length(max = 3)
    @ApiModelProperty(example = "EUR")
    private Currency currency;
}