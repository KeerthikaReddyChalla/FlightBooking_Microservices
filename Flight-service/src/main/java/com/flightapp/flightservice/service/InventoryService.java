package com.flightapp.flightservice.service;

import com.flightapp.flightservice.dto.InventoryRequest;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Mono<Inventory> addInventory(InventoryRequest request) {

        Inventory inv = Inventory.builder()
                .airlineId(request.getAirlineId())
                .fromPlace(request.getFromPlace())
                .toPlace(request.getToPlace())
                .flightDate(request.getFlightDate())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .tripType(request.getTripType().name())
                .price(request.getPrice())
                .build();

        return inventoryRepository.save(inv);
    }
}
