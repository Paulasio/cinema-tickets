package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.model.TicketType;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//Verifies integration logic: validation → calculation → provider calls.

class TicketServiceImplTest {
    private TicketPaymentService paymentService;
    private SeatReservationService seatService;
    private TicketService service;


    @BeforeEach
    void setUp() {
        paymentService = mock(TicketPaymentService.class);
        seatService = mock(SeatReservationService.class);
        service = new TicketServiceImpl(paymentService, seatService);
    }


    @Test
    void happyPath_mixedTickets_callsPaymentAndSeatReservation() {
        long accountId = 42L;
        var reqs = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketType.ADULT, 2),
                new TicketTypeRequest(TicketType.CHILD, 1),
                new TicketTypeRequest(TicketType.INFANT, 1)
        };


        assertDoesNotThrow(() -> service.purchaseTickets(accountId, reqs));
        verify(paymentService).makePayment(accountId, 2*25 + 1*15);
        verify(seatService).reserveSeat(accountId, 3);
    }


    @Test
    void rejects_over25_totalTickets() {
        long accountId = 1L;
        var reqs = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketType.ADULT, 20),
                new TicketTypeRequest(TicketType.CHILD, 6)
        };
        assertThrows(InvalidPurchaseException.class, () -> service.purchaseTickets(accountId, reqs));
    }


    @Test
    void rejects_childrenWithoutAdult() {
        long accountId = 2L;
        var reqs = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketType.CHILD, 1)
        };
        assertThrows(InvalidPurchaseException.class, () -> service.purchaseTickets(accountId, reqs));
    }


    @Test
    void rejects_infantsWithoutAdult() {
        long accountId = 3L;
        var reqs = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketType.INFANT, 2)
        };
        assertThrows(InvalidPurchaseException.class, () -> service.purchaseTickets(accountId, reqs));
    }
}
