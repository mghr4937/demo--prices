package com.mghr4937.demo.repositories;

import com.mghr4937.demo.models.Price;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Api(tags = "Price Entity")
@Repository
public interface PriceRestRepository extends JpaRepository<Price, Long> {
}
