package com.mghr4937.demo;

import com.mghr4937.demo.models.Brand;
import com.mghr4937.demo.models.Price;
import com.mghr4937.demo.repositories.BrandRestRepository;
import com.mghr4937.demo.repositories.PriceRestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;

@Slf4j
@SpringBootApplication
@EnableWebMvc
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


        log.info("Demo started successfully.");
    }

    @Bean
    public CommandLineRunner createDemoDataIfNeeded(BrandRestRepository brandRestRepository, PriceRestRepository priceRestRepository) {
        return args -> {
            var brand = brandRestRepository.save(Brand.builder().name("ZARA").build());
            brandRestRepository.save(Brand.builder().name("ZARA HOME").build());
            brandRestRepository.save(Brand.builder().name("ZARA KIDS").build());


            var price = Price.builder().brandId(brand)
                    .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(35455)
                    .priority(0)
                    .price(35.50)
                    .currency(Currency.getInstance("EUR"))
                    .build();
            priceRestRepository.save(price);
        };
    }


}
