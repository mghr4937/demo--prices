package com.mghr4937.demo.web.dto;

import com.mghr4937.demo.model.Brand;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;

/**
 * A DTO for the {@link Brand} entity
 */
@Data
public class BrandDto {
    private Long id;
    @Length(min = 3, max = 255)
    private String name;
}