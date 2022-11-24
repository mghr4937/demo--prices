package com.mghr4937.demo.unit.repositories;

import com.mghr4937.demo.models.Brand;
import com.mghr4937.demo.repositories.BrandRestRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class BrandRestRepositoryTest {

    private static final String BRAND = "TEST BRAND";

    @Autowired
    private BrandRestRepository repository;

    @Test
    public void testSave() throws Exception {
        var brand = getBrand(BRAND);

        var brandsIterable = repository.findAll();
        List<Brand> brandsList = StreamSupport
                .stream(brandsIterable.spliterator(), false)
                .collect(Collectors.toList());

        assertTrue(brandsList.contains(brand));
    }

    @Test
    public void testFindById() throws Exception {
        var brand = getBrand(BRAND);

        Brand result = repository.findById(brand.getId()).orElseThrow();
        assertNotNull(brand);
        assertEquals(brand.getId(), result.getId());
    }

    @Test
    public void testFindByName() throws Exception {
        var brand = getBrand(BRAND);

        Brand result = repository.findByName(brand.getName());
        assertNotNull(brand);
        assertEquals(brand.getName(), result.getName());
    }

    @Test
    public void testFindAll() throws Exception {
        var brand = getBrand(BRAND);

        List<Brand> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        assertEquals(4, result.size());
    }

    @Test
    public void testDeleteById() throws Exception {
        var brand = getBrand(BRAND);

        repository.deleteById(brand.getId());
        List<Brand> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        assertEquals(3, result.size());
    }

    private Brand getBrand(String name) {
        return repository.save(Brand.builder()
                .name(BRAND)
                .build());
    }


}
