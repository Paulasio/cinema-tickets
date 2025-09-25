package uk.gov.dwp.uc.pairtest;


import uk.gov.dwp.uc.pairtest.model.TicketType;
import java.util.Objects;


/**
  Immutable value object describing a request for N tickets of a given type.
  Remains in the original package to satisfy the provided interface and tests.
 */
public record TicketTypeRequest(TicketType ticketType, int noOfTickets) {
    public TicketTypeRequest {
        Objects.requireNonNull(ticketType, "ticketType must not be null");
        if (noOfTickets <= 0) {
            throw new IllegalArgumentException("noOfTickets must be > 0");
        }
    }
}
