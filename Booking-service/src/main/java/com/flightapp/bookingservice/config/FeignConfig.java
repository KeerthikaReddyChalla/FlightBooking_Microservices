package com.flightapp.bookingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
public class FeignConfig {

    @Bean
    public WebReactiveFeign.Builder feignBuilder() {
        return WebReactiveFeign.builder();
    }
}
