package com.mghr4937.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.mghr4937.demo.model.Price} entity
 */
@Getter
@Setter
@NoArgsConstructor
public class PriceDto implements Serializable {
    @ApiModelProperty(example = "1")
    private Long id;

    @NotNull
    @Valid
    private BrandDto brand;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Valid
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Valid
    private LocalDateTime endDate;

    @ApiModelProperty(example = "1")
    private int priceList;

    @ApiModelProperty(example = "ZARA")
    private long productId;

    @ApiModelProperty(example = "1")
    private int priority;

    @ApiModelProperty(example = "99.99")
    private Float price;

    @ApiModelProperty(example = "EUR")
    @Size(max = 3, message = "Currency length 3 characters.")
    @NotNull
    @Valid
    private String currency;
}