package com.mghr4937.demo.repository;

import com.mghr4937.demo.model.Price;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = "Price API")
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM Price p WHERE " +
            "(p.start_date <= :date and p.end_date >= :date) and " +
            "p.product_id = :productId and p.brand_id = :brandId " +
            "ORDER BY p.priority DESC")
    Optional<Price> queryPrice(@Param("date") LocalDateTime date, @Param("productId") Long productId, @Param("brandId") Long brandId);
}
