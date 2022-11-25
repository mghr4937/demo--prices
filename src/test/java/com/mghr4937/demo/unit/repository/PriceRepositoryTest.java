package com.mghr4937.demo.unit.repository;

import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PriceRepositoryTest {
    private static final String CURRENCY = "EUR";

    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public PriceRepositoryTest(PriceRepository priceRepository, BrandRepository brandRepository) {
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
    }

    @Test
    public void testSave() throws Exception {
        var price = createPrice(0, 45.50F);

        var pricesIterable = priceRepository.findAll();
        List<Price> pricesList = new ArrayList<>(pricesIterable);

        assertTrue(pricesList.contains(price));
    }

    @Test
    public void testFindById() throws Exception {
        var price = createPrice(0, 45.50F);

        Price result = priceRepository.findById(price.getId()).orElseThrow();
        assertNotNull(price);
        assertEquals(price.getId(), result.getId());
    }

    @Test
    public void testFindAll() throws Exception {
        var price = createPrice(0, 45.50F);

        List<Price> result = new ArrayList<>();
        result.addAll(priceRepository.findAll());
        assertEquals(5, result.size());
    }

    @Test
    public void testDeleteById() throws Exception {
        var price = createPrice(0, 45.50F);

        priceRepository.deleteById(price.getId());
        List<Price> result = new ArrayList<>();
        result.addAll(priceRepository.findAll());
        assertEquals(4, result.size());
    }

    @Test
    public void testQueryPrice() throws Exception {
        var date = LocalDateTime.of(2022, Month.MARCH, 15, 0, 0, 0);
        var price = createPrice(0, 45.50F);

        var result = priceRepository.queryPrice(date, 35999L, 1L);
        assertTrue(result.isPresent());
        assertEquals(price, result.get());
    }

    @Test
    public void testQueryPriceWithSameDateAndHighPriority() throws Exception {
        var date = LocalDateTime.of(2022, Month.MARCH, 15, 0, 0, 0);
        createPrice(0, 45.50F);
        var priceHighPriority = createPrice(1, 99.99F);

        var result = priceRepository.queryPrice(date, 35999L, 1L);
        assertTrue(result.isPresent());
        assertEquals(priceHighPriority, result.get());
    }

    @Test
    public void testQueryPriceNotFound() throws Exception {
        var date = LocalDateTime.of(2022, Month.MARCH, 15, 0, 0, 0);
        var price = createPrice(0, 45.50F);

        var result = priceRepository.queryPrice(date, 35999L, 2L);
        assertFalse(result.isPresent());
    }

    private Price createPrice(int priority, Float priceValue) {
        var brand = brandRepository.getReferenceById(1L);
        var price = Price.builder().brand(brand)
                .startDate(LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, Month.MARCH, 31, 23, 59, 59))
                .priceList(1)
                .productId(35999L)
                .priority(priority)
                .price(priceValue)
                .currency(Currency.getInstance(CURRENCY))
                .build();


        return priceRepository.save(price);
    }
}
