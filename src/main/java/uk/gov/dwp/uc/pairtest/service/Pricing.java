package uk.gov.dwp.uc.pairtest.service;


import uk.gov.dwp.uc.pairtest.model.TicketType;


/*
 * Centralised pricing and limits. Swap for a strategy if pricing becomes dynamic.
 */
public final class Pricing {
    public static final int MAX_TICKETS_PER_PURCHASE = 25;
    public static final int ADULT_PRICE = 25;
    public static final int CHILD_PRICE = 15;


    private Pricing() {}


    /* Returns the unit price for the given ticket type. */
    public static int priceOf(TicketType type) {
        return switch (type) {
            case ADULT -> ADULT_PRICE;
            case CHILD -> CHILD_PRICE;
            case INFANT -> 0;
        };
    }
}