package com.mghr4937.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.repository.PriceRepository;
import com.mghr4937.demo.web.dto.BrandDto;
import com.mghr4937.demo.web.dto.PriceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Month;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Slf4j
@Component
public class EntityTestUtil {

    private static final String CURRENCY = "EUR";
    private static final String TEST_DATA_PATH = "src/test/resources/data";

    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;
    private final ObjectMapper objectMapper;


    @Autowired
    public EntityTestUtil(PriceRepository priceRepository, BrandRepository brandRepository, ObjectMapper objectMapper) {
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
        this.objectMapper = objectMapper;
    }

    public BrandDto getBrandDtoFromFile(String path) throws IOException {
        String content = Files.readString(Path.of(TEST_DATA_PATH.concat(path)), StandardCharsets.US_ASCII);
        return objectMapper.readValue(content, BrandDto.class);
    }

    public String retrieveFileContent(String path) throws IOException {
        return Files.readString(Path.of(TEST_DATA_PATH.concat(path)), StandardCharsets.US_ASCII);
    }

    public Price createPrice(int priority, Float priceValue) {
        log.info("Creating Price priority: {}, price: {}", priority, priceValue);
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
        log.info("Creating Brand");
        return brandRepository.save(Brand.builder()
                .name(randomAlphabetic(6))
                .build());
    }

    public Brand createBrand(String name) {
        log.info("Creating Brand name: {}", name);
        return brandRepository.save(Brand.builder()
                .name(name)
                .build());
    }


}
