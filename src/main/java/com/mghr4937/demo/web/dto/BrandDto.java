package com.mghr4937.demo.web.dto;

import com.mghr4937.demo.model.Brand;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * A DTO for the {@link Brand} entity
 */
@Data
public class BrandDto {
    @ApiModelProperty(example = "1")
    private Long id;

    @Length(min = 3, max = 255)
    @ApiModelProperty(example = "ZARA")
    private String name;
}