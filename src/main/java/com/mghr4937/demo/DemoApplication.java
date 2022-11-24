package com.mghr4937.demo;

import com.mghr4937.demo.model.Brand;
import com.mghr4937.demo.model.Price;
import com.mghr4937.demo.repository.BrandRepository;
import com.mghr4937.demo.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;

@Slf4j
@SpringBootApplication
@EnableWebMvc
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


        log.info("Demo started successfully.");
    }




}
