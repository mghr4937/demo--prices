package com.mghr4937.demo.repository;

import com.mghr4937.demo.model.Price;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Api(tags = "Price API")
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}
