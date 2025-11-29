package com.flightapp.flightservice.service;

import com.flightapp.flightservice.dto.InventoryRequest;
import com.flightapp.flightservice.exception.ResourceNotFoundException;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.model.TripType;
import com.flightapp.flightservice.repository.InventoryRepository;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import reactor.core.publisher.*;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

class InventoryServiceTest {

    private InventoryRepository repo;
    private InventoryService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(InventoryRepository.class);
        service = new InventoryService(repo);
    }

    @Test
    void testAddInventory() {

        InventoryRequest request = InventoryRequest.builder()
                .airlineId(10L)
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDate(LocalDateTime.now())
                .totalSeats(100)
                .tripType(TripType.ONE_WAY)
                .price(5000.0)
                .build();

        Inventory expected = Inventory.builder()
                .id(1L)
                .airlineId(10L)
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDate(request.getFlightDate())
                .totalSeats(100)
                .availableSeats(100)
                .tripType("ONE_WAY")
                .price(5000.0)
                .build();

        Mockito.when(repo.save(any()))
                .thenReturn(Mono.just(expected));

        StepVerifier.create(service.addInventory(request))
                .expectNext(expected)
                .verifyComplete();
    }

}
