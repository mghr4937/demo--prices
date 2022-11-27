package com.mghr4937.demo.unit.repository;

import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.util.EntityTestUtil;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BrandRepositoryTest {

    private final EntityTestUtil entityTestUtil;
    private final BrandRepository repository;

    @Autowired
    public BrandRepositoryTest(EntityTestUtil entityTestUtil, BrandRepository repository) {
        this.entityTestUtil = entityTestUtil;
        this.repository = repository;
    }

    @Test
    public void testSave() throws Exception {
        var brand = entityTestUtil.createBrand();

        var brandsIterable = repository.findAll();
        var brandsList = new ArrayList<>(brandsIterable);

        assertTrue(brandsList.contains(brand));
    }

    @Test
    public void testFindById() throws Exception {
        var brand = entityTestUtil.createBrand();

        var result = repository.findById(brand.getId()).orElseThrow();
        assertNotNull(brand);
        assertEquals(brand.getId(), result.getId());
    }

    @Test
    public void testFindByName() throws Exception {
        var brand = entityTestUtil.createBrand();

        var result = repository.findByName(brand.getName());
        assertNotNull(brand);
        assertEquals(brand.getName(), result.get().getName());
    }

    @Test
    public void testFindAll() throws Exception {
        var brand = entityTestUtil.createBrand();

        var result = new ArrayList<>(repository.findAll());
        assertEquals(4, result.size());
    }

    @Test
    public void testDeleteById() throws Exception {
        var brand = entityTestUtil.createBrand();

        repository.deleteById(brand.getId());
        var result = new ArrayList<>(repository.findAll());
        assertEquals(3, result.size());
    }


}
