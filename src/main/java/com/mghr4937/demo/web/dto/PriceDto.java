package com.mghr4937.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.mghr4937.demo.model.Price} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Validated
public class PriceDto {
    @ApiModelProperty(example = "1")
    private Long id;

    @NotNull(message = "Invalid Brand")
    @Valid
    private BrandDto brand;

    @ApiModelProperty(required = true, value = "yyyy-MM-dd'T'HH:mm:ss'Z'", example = "2020-01-01T16:01:33Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Start Date can't be null")
    @Valid
    private LocalDateTime startDate;

    @ApiModelProperty(required = true, value = "yyyy-MM-dd'T'HH:mm:ss'Z'", example = "2022-02-25T00:00:00Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "End Date can't be null")
    @Valid
    private LocalDateTime endDate;

    @ApiModelProperty(example = "1")
    @NotNull(message = "Price List can't be null")
    private Integer priceList;

    @ApiModelProperty(example = "35455")
    @NotNull(message = "Product id can't be null")
    private Long productId;

    @ApiModelProperty(example = "1")
    @NotNull(message = "Priority can't be null")
    private Integer priority;

    @ApiModelProperty(example = "99.99")
    @NotNull(message = "Price can't be null")
    @Min(value = 0, message = "Price value must be greater or equal to 0")
    private Float price;

    @ApiModelProperty(example = "EUR")
    @Size(min = 3, max = 3, message = "Currency length 3 characters.")
    @NotBlank(message = "Currency can't be empty")
    @Valid
    private String currency;
}