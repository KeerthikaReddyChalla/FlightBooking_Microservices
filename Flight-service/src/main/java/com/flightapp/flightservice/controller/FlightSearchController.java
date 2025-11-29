package com.flightapp.flightservice.controller;

import com.flightapp.flightservice.dto.FlightSearchRequest;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight/search")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @PostMapping
    public Flux<Inventory> searchFlights(@RequestBody FlightSearchRequest req) {
        return flightSearchService.searchFlights(req);
    }
}
