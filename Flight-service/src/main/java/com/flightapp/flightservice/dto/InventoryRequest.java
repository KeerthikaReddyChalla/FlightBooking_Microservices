package com.flightapp.flightservice.dto;

import com.flightapp.flightservice.model.TripType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {

    private Long airlineId;
    private String fromPlace;
    private String toPlace;

    private LocalDateTime flightDate;

    private TripType tripType;

    private int totalSeats;
    private double price;
}
