package com.mghr4937.demo.web.dto;

import com.mghr4937.demo.model.Brand;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;


/**
 * A DTO for the {@link Brand} entity
 */
@Getter
@Setter
@NoArgsConstructor
public class BrandDto {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "ZARA")
    @Size(min = 3, message = "Name must have more than 3 characters")
    @NotBlank(message = "Name is mandatory")
    @NotNull
    @Valid
    private String name;
}