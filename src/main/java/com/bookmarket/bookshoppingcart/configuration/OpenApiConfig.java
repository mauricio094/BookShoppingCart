package com.bookmarket.bookshoppingcart.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookMarketOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book market Shopping Cart API")
                        .description("REST API for managing a book market shopping cart")
                        .version("1.0.0")
                        .contact(new Contact().name("Book market Team")));
    }
}
