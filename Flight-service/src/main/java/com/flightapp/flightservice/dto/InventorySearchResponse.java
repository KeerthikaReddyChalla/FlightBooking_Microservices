package com.flightapp.flightservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.flightapp.flightservice.model.TripType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventorySearchResponse {

    private Long id;
    private Long airlineId;
    private String fromPlace;
    private String toPlace;
    private LocalDateTime flightDate;
    private TripType tripType;
    private double price;
}
