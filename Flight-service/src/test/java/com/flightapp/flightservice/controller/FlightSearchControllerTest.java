package com.flightapp.flightservice.controller;

import com.flightapp.flightservice.dto.FlightSearchRequest;
import com.flightapp.flightservice.exception.GlobalExceptionHandler;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.service.FlightSearchService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(FlightSearchController.class)
@Import(GlobalExceptionHandler.class)
class FlightSearchControllerTest {

    @MockBean
    private FlightSearchService service;

    private final WebTestClient client = WebTestClient.bindToController(
            new FlightSearchController(service)
    )
            .controllerAdvice(new GlobalExceptionHandler())
            .build();

    @Test
    void testSearchFlights() {

        Inventory inv = Inventory.builder()
                .id(1L)
                .airlineId(101L)
                .fromPlace("Hyd")
                .toPlace("Blr")
                .flightDate(LocalDateTime.now())
                .tripType("ONE_WAY")
                .totalSeats(50)
                .availableSeats(40)
                .price(2999)
                .build();

        Mockito.when(service.searchFlights(any()))
                .thenReturn(Flux.just(inv));

        FlightSearchRequest req = FlightSearchRequest.builder()
                .fromPlace("Hyd")
                .toPlace("Blr")
                .flightDate(LocalDateTime.now())
                .tripType(null)
                .build();

        client.post()
                .uri("/api/flight/search")
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].fromPlace").isEqualTo("Hyd")
                .jsonPath("$[0].toPlace").isEqualTo("Blr")
                .jsonPath("$[0].price").isEqualTo(2999.0);
    }
}
