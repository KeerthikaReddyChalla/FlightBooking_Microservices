package com.flightapp.flightservice.controller;

import com.flightapp.flightservice.dto.InventoryRequest;
import com.flightapp.flightservice.exception.GlobalExceptionHandler;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.service.InventoryService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(InventoryController.class)
@Import(GlobalExceptionHandler.class)
class InventoryControllerTest {

    @MockBean
    private InventoryService service;

    private final WebTestClient client = WebTestClient.bindToController(new InventoryController(service))
            .controllerAdvice(new GlobalExceptionHandler())
            .build();

    @Test
    void testAddInventorySuccess() {

        Inventory inv = Inventory.builder()
                .id(1L)
                .airlineId(10L)
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDate(LocalDateTime.now())
                .build();

        Mockito.when(service.addInventory(any()))
                .thenReturn(Mono.just(inv));

        InventoryRequest req = InventoryRequest.builder()
                .airlineId(10L)
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDate(LocalDateTime.now())
                .build();

        client.post()
                .uri("/api/flight/airline/inventory/add")
                .bodyValue(req)
                .exchange()
                .expectStatus().isCreated();
    }
}
