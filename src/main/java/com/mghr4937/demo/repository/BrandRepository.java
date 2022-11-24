package com.mghr4937.demo.repository;

import com.mghr4937.demo.model.Brand;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Api(tags = "Brand API")
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    public Optional<Brand> findByName(String name);


}
