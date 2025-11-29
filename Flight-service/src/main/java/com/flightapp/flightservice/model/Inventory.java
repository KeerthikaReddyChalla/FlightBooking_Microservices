package com.flightapp.flightservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("inventory")
public class Inventory {

    @Id
    private Long id;

    private Long airlineId;
    private String fromPlace;
    private String toPlace;

    private LocalDateTime flightDate;
    private String tripType;

    private int totalSeats;
    private int availableSeats;
    private double price;
}
