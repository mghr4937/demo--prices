package com.mghr4937.demo.models.dto;

import com.mghr4937.demo.models.Brand;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Brand} entity
 */
@Data
public class BrandDto implements Serializable {
    private final Long id;
    @Length(min = 3, max = 255)
    @NotNull
    private final String name;
}