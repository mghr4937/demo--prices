package com.mghr4937.demo.unit.controller.rest;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.repository.BrandRepository;
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
public class BrandRestControllerImplTest {
    private static final String BRAND = "TEST BRAND";
    private static final String URL = "/api/brand";
    private static final String BRAND_JSON = "{\"name\":\"BrandName\"}";
    private static final String BRAND_EMPTY_NAME_JSON = "{\"name\":\"\"}";
    @Autowired
    BrandRepository repository;
    @Autowired
    private MockMvc mvc;


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
    public void whenPostBrandWithEmptyName_thenReturn422() throws Exception {
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BRAND_EMPTY_NAME_JSON))
                .andExpect(status().isUnprocessableEntity());
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
