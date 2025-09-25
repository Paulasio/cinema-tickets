package uk.gov.dwp.uc.pairtest.service;


import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.model.TicketType;


import java.util.Arrays;


//Validates business rules for a ticket purchase request.

public final class PurchaseValidator {


    //Ensures account and requests are valid and conform to business rules.

    public void validate(Long accountId, TicketTypeRequest[] requests) throws InvalidPurchaseException {
        if (accountId == null || accountId <= 0) {
            throw new InvalidPurchaseException("Account id must be a positive number");
        }
        if (requests == null || requests.length == 0) {
            throw new InvalidPurchaseException("At least one ticket request must be provided");
        }
        if (Arrays.stream(requests).anyMatch(r -> r == null)) {
            throw new InvalidPurchaseException("Ticket request must not be null");
        }


        int total = Arrays.stream(requests).mapToInt(TicketTypeRequest::noOfTickets).sum();
        if (total > Pricing.MAX_TICKETS_PER_PURCHASE) {
            throw new InvalidPurchaseException("Maximum of %d tickets per purchase".formatted(Pricing.MAX_TICKETS_PER_PURCHASE));
        }


        int adults = Arrays.stream(requests)
                .filter(r -> r.ticketType() == TicketType.ADULT)
                .mapToInt(TicketTypeRequest::noOfTickets)
                .sum();


        int childrenOrInfants = Arrays.stream(requests)
                .filter(r -> r.ticketType() == TicketType.CHILD || r.ticketType() == TicketType.INFANT)
                .mapToInt(TicketTypeRequest::noOfTickets)
                .sum();


// Child/Infant tickets require at least one adult in the purchase.
        if (childrenOrInfants > 0 && adults == 0) {
            throw new InvalidPurchaseException("Child and Infant tickets require at least one Adult ticket");
        }


// Optional stricter rule could be enforced: infants <= adults (1:1 lap policy).
    }
}
