package com.flightapp.flightservice.controller;

import com.flightapp.flightservice.dto.InventoryRequest;
import com.flightapp.flightservice.model.Inventory;
import com.flightapp.flightservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Inventory> addInventory(@RequestBody InventoryRequest req) {
        return inventoryService.addInventory(req);
    }
}
