package com.mghr4937.demo.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    public static final String API = " API";
    public static final String API_DESCRIPTION = " API Description";
    public static final String NAME = "Matias Hernandez";
    public static final String URL = "https://github.com/mghr4937";
    public static final String EMAIL = "mghr4937@gmail.com";
    public static final String LICENSE = "Apache 2.0";
    public static final String LICENSE_URL = "http://springdoc.org";
    private final BuildProperties buildProperties;

    @Autowired
    public SwaggerConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                buildProperties.getArtifact() + API,
                buildProperties.getArtifact() + API_DESCRIPTION,
                buildProperties.getVersion(),
                StringUtils.EMPTY,
                new Contact(NAME, URL, EMAIL),
                LICENSE,
                LICENSE_URL,
                Collections.emptyList()
        );
    }

}
