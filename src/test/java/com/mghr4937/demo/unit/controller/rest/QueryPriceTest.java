package com.mghr4937.demo.unit.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class QueryPriceTest {

    private static final String PATH = "/price/query";
    private static final Long PRODUCT_ID = 35455L;

    private final MockMvc mvc;

    @Autowired
    public QueryPriceTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    //Test 1: petición a las 10:00 del día 14 del producto 35455  para la brand 1 (ZARA)
    @Test
    public void test_1_whenGetDay14at10Hrs() throws Exception {
        var query = UriComponentsBuilder.fromUriString(PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-14T10:00:00")
                .queryParam("productId", PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.priority").value(0))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50F));

    }

    //Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
    @Test
    public void test_2_whenGetDay14at16Hrs() throws Exception {
        // Test 1: petición a las 10:00 del día 14 del producto 35455  para la brand 1 (ZARA)
        var query = UriComponentsBuilder.fromUriString(PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-14T16:00:00")
                .queryParam("productId", PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45F));
    }

    //Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
    @Test
    public void test_3_whenGetDay14at21Hrs() throws Exception {
        // Test 1: petición a las 10:00 del día 14 del producto 35455  para la brand 1 (ZARA)
        var query = UriComponentsBuilder.fromUriString(PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-14T21:00:00")
                .queryParam("productId", PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.priority").value(0))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50F));
    }

    //Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
    @Test
    public void test_4_whenGetDay15at10Hrs() throws Exception {

        var query = UriComponentsBuilder.fromUriString(PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-15T10:00:00")
                .queryParam("productId", PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50F));
    }

    //Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
    @Test
    public void test_5_whenGetDay16at21Hrs() throws Exception {

        var query = UriComponentsBuilder.fromUriString(PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-16T21:00:00")
                .queryParam("productId", PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95F));
    }

}
