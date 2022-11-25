package com.mghr4937.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryPriceResponseDto {

    @ApiModelProperty(example = "35455")
    private Long productId;

    @ApiModelProperty(example = "1")
    private Long brandId;

    @ApiModelProperty(example = "1")
    private int priceList;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    @ApiModelProperty(required = true, value = "yyyy-MM-dd-HH:mm:ss", example = "2022-06-01-00:00:00")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    @ApiModelProperty(required = true, value = "yyyy-MM-dd-HH:mm:ss", example = "2022-06-01-00:00:00")
    private LocalDateTime endDate;

    @ApiModelProperty(example = "99.99")
    private Float price;
}
