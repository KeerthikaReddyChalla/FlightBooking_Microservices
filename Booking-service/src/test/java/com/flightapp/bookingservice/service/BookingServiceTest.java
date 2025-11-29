package com.flightapp.bookingservice.service;

import com.flightapp.bookingservice.dto.*;
import com.flightapp.bookingservice.exception.ResourceNotFoundException;
import com.flightapp.bookingservice.model.*;
import com.flightapp.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import org.springframework.kafka.core.KafkaTemplate;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class BookingServiceTest {

    private BookingRepository repo;
    private KafkaTemplate<String, String> kafka;
    private BookingService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(BookingRepository.class);
        kafka = Mockito.mock(KafkaTemplate.class);
        service = new BookingService(repo, kafka);
    }

    @Test
    void testBookSuccess() {

        Booking reqEntity = Booking.builder()
                .pnr("PNR-123AAA")
                .flightId("F001")
                .userName("K")
                .email("k@a.com")
                .bookingDate(LocalDateTime.now())
                .journeyDate(LocalDateTime.now().plusDays(5))
                .numberOfSeats(1)
                .passengers(List.of())
                .build();

        Mockito.when(repo.save(any()))
                .thenReturn(Mono.just(reqEntity));

        BookingRequest req = BookingRequest.builder()
                .userName("K")
                .email("k@a.com")
                .numberOfSeats(1)
                .passengers(List.of())
                .build();

        StepVerifier.create(service.book("F001", req))
                .expectNextMatches(r -> r.getFlightId().equals("F001"))
                .verifyComplete();
    }

    @Test
    void testGetByPnrNotFound() {

        Mockito.when(repo.findByPnr("PNR000"))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.getByPnr("PNR000"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void testCancelWithin24HoursFails() {

        Booking b = Booking.builder()
                .pnr("P1")
                .journeyDate(LocalDateTime.now().plusHours(10))
                .build();

        Mockito.when(repo.findByPnr("P1"))
                .thenReturn(Mono.just(b));

        StepVerifier.create(service.cancel("P1"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
