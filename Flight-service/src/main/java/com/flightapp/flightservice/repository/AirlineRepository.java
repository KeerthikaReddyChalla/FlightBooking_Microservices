package com.flightapp.flightservice.repository;

import com.flightapp.flightservice.model.Airline;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AirlineRepository extends ReactiveCrudRepository<Airline, Long> {

    Mono<Airline> findByName(String name);
}
