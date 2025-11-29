package com.flightapp.notifservice;

import com.flightapp.notifservice.dto.BookingEvent;
import com.flightapp.notifservice.service.BookingEventListener;
import com.flightapp.notifservice.service.EmailService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BookingEventListenerTest {

    private final EmailService emailService = Mockito.mock(EmailService.class);

    private final BookingEventListener listener =
            new BookingEventListener(emailService);

    @Test
    void testConsumeBookingEvent() throws Exception {

        BookingEvent event = BookingEvent.builder()
                .pnr("PNR123")
                .email("test@example.com")
                .userName("Keerthika")
                .numberOfSeats(2)
                .totalPrice(5000)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(event);

        listener.consumeBookingEvent(json);

        Mockito.verify(emailService, Mockito.times(1))
                .sendBookingEmail(Mockito.any());
    }
}
