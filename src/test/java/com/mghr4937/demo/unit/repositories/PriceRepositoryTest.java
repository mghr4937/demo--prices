package com.mghr4937.demo.unit.repositories;

import com.mghr4937.demo.models.Price;
import com.mghr4937.demo.repositories.BrandRepository;
import com.mghr4937.demo.repositories.PriceRepository;
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

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PriceRepositoryTest {

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    private BrandRepository branRestRepository;

    @Test
    public void testSave() throws Exception {
        var brand = createPrice();

        var pricesIterable = priceRepository.findAll();
        List<Price> brandsList = new ArrayList<>(pricesIterable);

        assertTrue(brandsList.contains(brand));
    }

    @Test
    public void testFindById() throws Exception {
        var price = createPrice();

        Price result = priceRepository.findById(price.getId()).orElseThrow();
        assertNotNull(price);
        assertEquals(price.getId(), result.getId());
    }

    @Test
    public void testFindAll() throws Exception {
        var price = createPrice();

        List<Price> result = new ArrayList<>();
        result.addAll(priceRepository.findAll());
        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteById() throws Exception {
        var price = createPrice();

        priceRepository.deleteById(price.getId());
        List<Price> result = new ArrayList<>();
        result.addAll(priceRepository.findAll());
        assertEquals(1, result.size());
    }

    private Price createPrice() {
        var brand = branRestRepository.getReferenceById(1L);
        var price = Price.builder().brandId(brand)
                .startDate(LocalDateTime.of(2022, Month.MARCH, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2022, Month.MARCH, 31, 23, 59, 59))
                .priceList(1)
                .productId(35455)
                .priority(0)
                .price(85.50)
                .currency(Currency.getInstance("EUR"))
                .build();
        return priceRepository.save(price);
    }
}
