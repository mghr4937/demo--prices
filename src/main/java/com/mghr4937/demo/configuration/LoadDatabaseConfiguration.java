package com.mghr4937.demo.configuration;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;

@Configuration
@Slf4j
public class LoadDatabaseConfiguration {
    @Bean
    public CommandLineRunner initDatabase(BrandRepository brandRepository, PriceRepository priceRepository) {
        return args -> {
            log.info("Starting DB Preload");
            log.info("Preloading Brands");
            var brands = new ArrayList<Brand>();
            brands.add(Brand.builder().name("ZARA").build());
            brands.add(Brand.builder().name("ZARA HOME").build());
            brands.add(Brand.builder().name("ZARA KIDS").build());
            brandRepository.saveAll(brands);
            log.info("Brands Loaded: {}", brands);

            log.info("Preloading Prices");
            var prices = new ArrayList<Price>();

            prices.add(Price.builder().brand(brands.get(0))
                    .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(35455)
                    .priority(0)
                    .price(35.50)
                    .currency(Currency.getInstance("EUR"))
                    .build());

            prices.add(Price.builder().brand(brands.get(0))
                    .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 15, 0, 0))
                    .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 18, 30, 0))
                    .priceList(2)
                    .productId(35455)
                    .priority(1)
                    .price(25.45)
                    .currency(Currency.getInstance("EUR"))
                    .build());

            prices.add(Price.builder().brand(brands.get(0))
                    .startDate(LocalDateTime.of(2020, Month.JUNE, 15, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, Month.JUNE, 15, 11, 0, 0))
                    .priceList(3)
                    .productId(35455)
                    .priority(1)
                    .price(30.50)
                    .currency(Currency.getInstance("EUR"))
                    .build());

            prices.add(Price.builder().brand(brands.get(0))
                    .startDate(LocalDateTime.of(2020, Month.JUNE, 15, 16, 0, 0))
                    .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                    .priceList(4)
                    .productId(35455)
                    .priority(1)
                    .price(38.95)
                    .currency(Currency.getInstance("EUR"))
                    .build());

            priceRepository.saveAll(prices);
            log.info("Prices Loaded: {}", prices);
            log.info("Database Loaded");
        };
    }
}
