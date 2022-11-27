package com.mghr4937.demo.util;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Component
public class EntityTestUtil {

    private static final String CURRENCY = "EUR";

    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;


    @Autowired
    public EntityTestUtil(PriceRepository priceRepository, BrandRepository brandRepository) {
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
    }

    public Price createPrice(int priority, Float priceValue) {
        var brand = brandRepository.getReferenceById(1L);
        var price = Price.builder().brand(brand)
                .startDate(LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, Month.MARCH, 31, 23, 59, 59))
                .priceList(1)
                .productId(35999L)
                .priority(priority)
                .price(priceValue)
                .currency(CURRENCY)
                .build();


        return priceRepository.save(price);
    }

    public Brand createBrand() {
        return brandRepository.save(Brand.builder()
                .name(randomAlphabetic(6))
                .build());
    }

    public Brand createBrand(String name) {
        return brandRepository.save(Brand.builder()
                .name(name)
                .build());
    }
}
