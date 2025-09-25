package uk.gov.dwp.uc.pairtest.service;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.PurchaseSummary;
import uk.gov.dwp.uc.pairtest.model.TicketType;


import static org.junit.jupiter.api.Assertions.*;


/** Focused unit test for pure calculation logic. */
class PurchaseCalculatorTest {
    @Test
    void computesAmountAndSeats() {
        var calc = new PurchaseCalculator();
        var reqs = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketType.ADULT, 2),
                new TicketTypeRequest(TicketType.CHILD, 3),
                new TicketTypeRequest(TicketType.INFANT, 1)
        };
        PurchaseSummary s = calc.summarize(reqs);
        assertEquals(2, s.adults());
        assertEquals(3, s.children());
        assertEquals(1, s.infants());
        assertEquals(6, s.totalTickets());
        assertEquals(2*25 + 3*15, s.amountToPay());
        assertEquals(5, s.seatsToReserve());
    }
}
