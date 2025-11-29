package com.flightapp.flightservice.repository;

import com.flightapp.flightservice.model.Inventory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface InventoryRepository extends ReactiveCrudRepository<Inventory, Long> {

    Flux<Inventory> findByFromPlaceAndToPlaceAndFlightDate(
            String from, String to, LocalDateTime flightDate);
}
