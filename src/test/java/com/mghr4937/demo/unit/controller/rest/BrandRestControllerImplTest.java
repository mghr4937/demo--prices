package com.mghr4937.demo.unit.controller.rest;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BrandRestControllerImplTest {
    private static final String BRAND = "TESTBRAND";
    private static final String URL = "/api/brand";
    private static final String BRAND_JSON = "{\"name\":\"BrandName\"}";
    private static final String BRAND_EMPTY_NAME_JSON = "{\"name\":\"\"}";
    private static final String BRAND_JSON_WITH_ID = "{\"id\":1,\"name\":\"" + BRAND + "\"}";
    private final BrandRepository repository;
    private final ResourceLoader resourceLoader;
    private final MockMvc mvc;


    @Autowired
    public BrandRestControllerImplTest(BrandRepository repository, ResourceLoader resourceLoader, MockMvc mvc) {
        this.repository = repository;
        this.resourceLoader = resourceLoader;
        this.mvc = mvc;
    }

    @Test
    public void whenPostBrand_thenReturn200() throws Exception {
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BRAND_JSON))
                .andExpect(status().isOk());

        mvc.perform(get(URL.concat("/search/findByName?name=BrandName"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("BrandName"));
    }

    @Test
    public void whenPostExistingBrand_thenReturn200() throws Exception {
        mvc.perform(get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BRAND_JSON_WITH_ID))
                .andExpect(status().isOk());

        mvc.perform(get(URL.concat("/search/findByName?name=" + BRAND))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(BRAND));

        mvc.perform(get(URL.concat("/search/findByName?name=ZARA"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPostBrandWithEmptyName_thenReturn422() throws Exception {
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BRAND_EMPTY_NAME_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void whenGetAllBrands_thenReturn200() throws Exception {
        mvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void whenGetWithId_thenReturnBrand() throws Exception {
        var brand = createBrand(BRAND);

        mvc.perform(get(URL.concat("/" + brand.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(brand.getId()))
                .andExpect(jsonPath("$.name").value(brand.getName()));
    }

    @Test
    public void whenGetWithId_thenReturn404() throws Exception {
        mvc.perform(get(URL.concat("/99"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindByName_thenReturnBrand() throws Exception {
        var brand = createBrand(BRAND);

        mvc.perform(get(URL.concat("/search/findByName?name=" + brand.getName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(brand.getId()))
                .andExpect(jsonPath("$.name").value(brand.getName()));
    }

    @Test
    public void whenFindByName_thenReturn404() throws Exception {
        mvc.perform(get(URL.concat("/search/findByName?name=NONE"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private Brand createBrand(String name) {
        return repository.save(Brand.builder()
                .name(BRAND)
                .build());
    }
}
