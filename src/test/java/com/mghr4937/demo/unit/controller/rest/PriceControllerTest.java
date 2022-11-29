package com.mghr4937.demo.unit.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mghr4937.demo.util.EntityTestUtil;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PriceControllerTest {
    private static final String URL = "/price";
    private static final String QUERY_PRICE_PATH = URL.concat("/query");
    private static final Long BAD_PRODUCT_ID = 999L;
    private static final Long PRODUCT_ID = 35455L;
    private static final String BAD_ID = "/99";

    private final EntityTestUtil entityTestUtil;
    private final MockMvc mvc;

    @Autowired
    public PriceControllerTest(EntityTestUtil entityTestUtil, ObjectMapper objectMapper, MockMvc mvc) {

        this.entityTestUtil = entityTestUtil;
        this.mvc = mvc;
    }

    @Test
    public void whenPostPrice_thenReturn200() throws Exception {
        var rawJson = entityTestUtil.retrieveFileContent("/price.json");
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostPriceWithEmptyCurrency_thenReturn400() throws Exception {
        var rawJson = entityTestUtil.retrieveFileContent("/price_empty_currency.json");
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostPriceWithBadDate_thenReturn400() throws Exception {
        var rawJson = entityTestUtil.retrieveFileContent("/price_bad_date.json");
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostPriceWithStarDateNotBeforeEndDate_thenReturn400() throws Exception {
        var rawJson = entityTestUtil.retrieveFileContent("/price_not_before_start_date.json");
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostPriceWithShortCurrency_thenReturn400() throws Exception {
        var rawJson = entityTestUtil.retrieveFileContent("/price_short_currency.json");
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostPriceWithLongCurrency_thenReturn400() throws Exception {
        var rawJson = entityTestUtil.retrieveFileContent("/price_long_currency.json");
        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetAllPrices_thenReturn200() throws Exception {
        mvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void whenGetWithId_thenReturn200() throws Exception {
        var price = entityTestUtil.createPrice(0, 55.90F);
        mvc.perform(get(URL.concat("/" + price.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(price.getId()))
                .andExpect(jsonPath("$.price").value(55.90F));
    }

    @Test
    public void whenGetWithNoFoundId_thenReturn404() throws Exception {
        mvc.perform(get(URL.concat(BAD_ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetQueryPrice_thenReturn200() throws Exception {
        var query = UriComponentsBuilder.fromUriString(QUERY_PRICE_PATH)
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

    @Test
    public void whenGetQueryPriceWithPriority_thenReturn200() throws Exception {
        var query = UriComponentsBuilder.fromUriString(QUERY_PRICE_PATH)
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

    @Test
    public void whenGetQueryPriceBadDate_thenReturn400() throws Exception {
        var query = UriComponentsBuilder.fromUriString(QUERY_PRICE_PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-14T120:00:00Z")
                .queryParam("productId", PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void whenGetQueryPriceNotFound_thenReturn404() throws Exception {

        var query = UriComponentsBuilder.fromUriString(QUERY_PRICE_PATH)
                // Add query parameter
                .queryParam("brandId", 1L)
                .queryParam("date", "2020-06-16T21:00:00")
                .queryParam("productId", BAD_PRODUCT_ID)
                .toUriString();
        mvc.perform((get(query))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
