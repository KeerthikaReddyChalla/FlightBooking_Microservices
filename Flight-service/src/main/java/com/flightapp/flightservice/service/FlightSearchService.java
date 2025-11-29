package com.flightapp.flightservice.service;

import com.flightapp.flightservice.dto.FlightSearchRequest;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final InventoryRepository inventoryRepository;

    public Flux<Inventory> searchFlights(FlightSearchRequest req) {
        return inventoryRepository.findByFromPlaceAndToPlaceAndFlightDate(
                req.getFromPlace(),
                req.getToPlace(),
                req.getFlightDate()
        );
    }
}
