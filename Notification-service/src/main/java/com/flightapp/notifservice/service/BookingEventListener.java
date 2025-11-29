package com.flightapp.notifservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightapp.notifservice.dto.BookingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingEventListener {

    private final EmailService emailService;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "booking-events", groupId = "notification-consumers")
    public void consumeBookingEvent(String message) {
        try {
            BookingEvent event = mapper.readValue(message, BookingEvent.class);
            log.info("📩 Received Booking Event: {}", event);
            emailService.sendBookingEmail(event);
        } catch (Exception e) {
            log.error("❌ Failed to parse booking event: {}", e.getMessage());
        }
    }
}
