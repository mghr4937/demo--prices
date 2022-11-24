package com.mghr4937.demo.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;

/**
 * A DTO for the {@link Brand} entity
 */
@Data
public class BrandDto implements Serializable {
    private final Long id;
    @Length(min = 3, max = 255)
    private final String name;
}