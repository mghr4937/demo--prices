package com.mghr4937.demo.unit.controllers.rest;

import com.mghr4937.demo.models.Brand;
import com.mghr4937.demo.repositories.BrandRestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BrandRestRepositoryIntegrationTest {
    private static final String BRAND = "TEST BRAND";
    private static final String URL = "/brands";

    @Autowired
    BrandRestRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenPostBrand_thenReturn200() throws Exception {
        var brand = getBrand(BRAND);

        mvc.perform(post(URL.concat("/99"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(get(URL.concat("/" + brand.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(brand.getId()))
                .andExpect(jsonPath("$.name").value(brand.getName()));
    }

    @Test
    public void whenFindById_thenReturnBrand() throws Exception {
        var brand = getBrand(BRAND);

        mvc.perform(get(URL.concat("/" + brand.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(brand.getId()))
                .andExpect(jsonPath("$.name").value(brand.getName()));
    }

    @Test
    public void whenFindById_thenReturn404() throws Exception {

        mvc.perform(get(URL.concat("/99"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenFindByName_thenReturnBrand() throws Exception {
        var brand = getBrand(BRAND);

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

    private Brand getBrand(String name) {
        return repository.save(Brand.builder()
                .name(BRAND)
                .build());
    }
}
