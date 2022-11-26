package com.mghr4937.demo.repository;

import com.mghr4937.demo.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    public Optional<Brand> findByName(String name);


}
