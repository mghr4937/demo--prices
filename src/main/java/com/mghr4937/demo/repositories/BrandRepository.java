package com.mghr4937.demo.repositories;

import com.mghr4937.demo.models.Brand;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Api(tags = "Brand API")
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    public Brand findByName(String name);


}
