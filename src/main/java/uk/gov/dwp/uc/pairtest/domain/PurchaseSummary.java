package uk.gov.dwp.uc.pairtest.domain;

/**
 * Aggregate of derived values for a purchase: counts, price, and seat reservations.
 */
public record PurchaseSummary(int adults, int children, int infants,
                              int totalTickets, int amountToPay, int seatsToReserve) {}