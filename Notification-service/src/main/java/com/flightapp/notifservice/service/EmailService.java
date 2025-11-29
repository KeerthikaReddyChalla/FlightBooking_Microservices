package com.flightapp.notifservice.service;

import com.flightapp.notifservice.dto.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendBookingEmail(BookingEvent event) {
        log.info("📧 Sending email to {} for booking PNR {}", 
                 event.getEmail(), event.getPnr());

        // MOCK EMAIL (just logging)
        log.info("Email Sent Successfully: \nUser: {} \nSeats: {} \nAmount: {}",
                event.getUserName(), event.getNumberOfSeats(), event.getTotalPrice());
    }
}
