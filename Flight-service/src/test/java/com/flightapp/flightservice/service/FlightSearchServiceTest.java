package com.flightapp.flightservice.service;

import com.flightapp.flightservice.dto.FlightSearchRequest;
import com.flightapp.flightservice.model.*;
import com.flightapp.flightservice.repository.InventoryRepository;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import reactor.core.publisher.*;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

class FlightSearchServiceTest {

    private InventoryRepository repo;
    private FlightSearchService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(InventoryRepository.class);
        service = new FlightSearchService(repo);
    }
    
    @Test
    void testSearchFlights() {

        Inventory inv = Inventory.builder()
                .airlineId(1L)
                .fromPlace("Hyd")
                .toPlace("Mumbai")
                .flightDate(LocalDateTime.now())
                .tripType(TripType.ONE_WAY.name())
                .price(3500)
                .build();

        Mockito.when(repo.findByFromPlaceAndToPlaceAndFlightDate(any(), any(), any()))
        .thenReturn(Flux.just(inv));

        FlightSearchRequest req = new FlightSearchRequest(
                "Hyd", "Mumbai", LocalDateTime.now(), TripType.ONE_WAY
        );

        StepVerifier.create(service.searchFlights(req))
                .expectNextCount(1)
                .verifyComplete();
    }
}
