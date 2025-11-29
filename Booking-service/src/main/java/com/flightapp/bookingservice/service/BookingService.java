package com.flightapp.bookingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightapp.bookingservice.dto.*;
import com.flightapp.bookingservice.exception.ResourceNotFoundException;
import com.flightapp.bookingservice.model.Booking;
import com.flightapp.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /*
       Generate PNR
     */
    private String generatePNR() {
        return "PNR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /*
       Book Ticket
     */
    public Mono<BookingResponse> book(String flightId, BookingRequest req) {

        Booking booking = Booking.builder()
                .pnr(generatePNR())
                .flightId(flightId)
                .userName(req.getUserName())
                .email(req.getEmail())
                .numberOfSeats(req.getNumberOfSeats())
                .passengers(req.getPassengers())
                .bookingDate(LocalDateTime.now())
                .journeyDate(LocalDateTime.now().plusDays(5))
                .totalPrice(req.getNumberOfSeats() * 3000) // dummy
                .cancelled(false)
                .build();

        return repo.save(booking)
                .map(b -> {

                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        kafkaTemplate.send("booking-events", mapper.writeValueAsString(b));
                    } catch (Exception ignored) {}

                    return BookingResponse.builder()
                            .pnr(b.getPnr())
                            .flightId(b.getFlightId())
                            .userName(b.getUserName())
                            .email(b.getEmail())
                            .bookingDate(b.getBookingDate())
                            .journeyDate(b.getJourneyDate())
                            .numberOfSeats(b.getNumberOfSeats())
                            .totalPrice(b.getTotalPrice())
                            .passengers(b.getPassengers())
                            .cancelled(b.getCancelled())
                            .build();
                });
    }

    /*
       Get booking by PNR
     */
    public Mono<BookingResponse> getByPnr(String pnr) {
        return repo.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Invalid PNR")))
                .map(b -> BookingResponse.builder()
                        .pnr(b.getPnr())
                        .flightId(b.getFlightId())
                        .userName(b.getUserName())
                        .email(b.getEmail())
                        .bookingDate(b.getBookingDate())
                        .journeyDate(b.getJourneyDate())
                        .numberOfSeats(b.getNumberOfSeats())
                        .totalPrice(b.getTotalPrice())
                        .passengers(b.getPassengers())
                        .cancelled(b.getCancelled())
                        .build());
    }

    /*
        Cancel Booking
     */
    public Mono<CancelResponse> cancel(String pnr) {
        return repo.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Invalid PNR")))
                .flatMap(b -> {

                    if (b.getJourneyDate().minusHours(24).isBefore(LocalDateTime.now())) {
                        return Mono.error(new ResourceNotFoundException("Cannot cancel within 24 hours"));
                    }

                    b.setCancelled(true);

                    return repo.save(b)
                            .thenReturn(
                                    CancelResponse.builder()
                                            .pnr(pnr)
                                            .message("Cancelled successfully")
                                            .build()
                            );
                });
    }
}
