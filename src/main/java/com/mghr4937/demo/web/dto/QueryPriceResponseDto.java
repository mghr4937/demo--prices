package com.mghr4937.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class QueryPriceResponseDto {

    @ApiModelProperty(example = "35455")
    private Long productId;

    @ApiModelProperty(example = "1")
    private Long brandId;

    @ApiModelProperty(example = "1")
    private int priceList;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Valid
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Valid
    private LocalDateTime endDate;

    @ApiModelProperty(example = "99.99")
    @NotNull
    private Float price;
}
