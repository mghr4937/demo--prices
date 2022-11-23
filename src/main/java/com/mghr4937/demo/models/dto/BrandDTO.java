package com.mghr4937.demo.models.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mghr4937.demo.models.Brand} entity
 */
@Data
public class BrandDTO implements Serializable {
    private final Long id;
    private final String name;


}