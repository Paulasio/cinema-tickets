package uk.gov.dwp.uc.pairtest.service;

import uk.gov.dwp.uc.pairtest.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.PurchaseSummary;
import uk.gov.dwp.uc.pairtest.model.TicketType;


import java.util.Arrays;


/*
 * Pure calculator: aggregates requests and derives price and seat reservations.
 */
public final class PurchaseCalculator {



     //summarises the purchase.
    // Note: infants are free and do not occupy seats.

    public PurchaseSummary summarize(TicketTypeRequest[] requests) {
        int adults = qty(requests, TicketType.ADULT);
        int children = qty(requests, TicketType.CHILD);
        int infants = qty(requests, TicketType.INFANT);
        int total = adults + children + infants;


// Only adults and children contribute to payment.
        int amount = (adults * Pricing.ADULT_PRICE) + (children * Pricing.CHILD_PRICE);
// Seats reserved for adults and children only; infants sit on laps.
        int seats = adults + children;


        return new PurchaseSummary(adults, children, infants, total, amount, seats);
    }


    private static int qty(TicketTypeRequest[] requests, TicketType type) {
        return Arrays.stream(requests)
                .filter(r -> r.ticketType() == type)
                .mapToInt(TicketTypeRequest::noOfTickets)
                .sum();
    }
}
