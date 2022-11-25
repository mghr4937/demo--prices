package com.mghr4937.demo.unit.repository;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.repository.BrandRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository repository;

    @Test
    public void testSave() throws Exception {
        var brand = createBrand();

        var brandsIterable = repository.findAll();
        List<Brand> brandsList = new ArrayList<>(brandsIterable);

        assertTrue(brandsList.contains(brand));
    }

    @Test
    public void testFindById() throws Exception {
        var brand = createBrand();

        Brand result = repository.findById(brand.getId()).orElseThrow();
        assertNotNull(brand);
        assertEquals(brand.getId(), result.getId());
    }

    @Test
    public void testFindByName() throws Exception {
        var brand = createBrand();

        Optional<Brand> result = repository.findByName(brand.getName());
        assertNotNull(brand);
        assertEquals(brand.getName(), result.get().getName());
    }

    @Test
    public void testFindAll() throws Exception {
        var brand = createBrand();

        List<Brand> result = new ArrayList<>();
        result.addAll(repository.findAll());
        assertEquals(4, result.size());
    }

    @Test
    public void testDeleteById() throws Exception {
        var brand = createBrand();

        repository.deleteById(brand.getId());
        List<Brand> result = new ArrayList<>();
        result.addAll(repository.findAll());
        assertEquals(3, result.size());
    }

    private Brand createBrand() {
        return repository.save(Brand.builder()
                .name(randomAlphabetic(6))
                .build());
    }


}
