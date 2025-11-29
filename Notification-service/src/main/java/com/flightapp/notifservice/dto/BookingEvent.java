package com.flightapp.notifservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEvent {
    private String pnr;
    private String email;
    private String userName;
    private int numberOfSeats;
    private double totalPrice;
}
