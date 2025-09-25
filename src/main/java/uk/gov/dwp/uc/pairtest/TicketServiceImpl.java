package uk.gov.dwp.uc.pairtest;


import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.PurchaseSummary;
import uk.gov.dwp.uc.pairtest.service.PurchaseCalculator;
import uk.gov.dwp.uc.pairtest.service.PurchaseValidator;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


//Application service that enforces business rules and coordinates external calls.

public class TicketServiceImpl implements TicketService {


    private final TicketPaymentService paymentService;
    private final SeatReservationService seatReservationService;
    private final PurchaseValidator validator;
    private final PurchaseCalculator calculator;


    public TicketServiceImpl(TicketPaymentService paymentService,
                             SeatReservationService seatReservationService) {
        this(paymentService, seatReservationService, new PurchaseValidator(), new PurchaseCalculator());
    }


    // Allows injecting custom collaborators (useful for tests/DI).
    public TicketServiceImpl(TicketPaymentService paymentService,
                             SeatReservationService seatReservationService,
                             PurchaseValidator validator,
                             PurchaseCalculator calculator) {
        this.paymentService = paymentService;
        this.seatReservationService = seatReservationService;
        this.validator = validator;
        this.calculator = calculator;
    }


    // Validates request, computes totals, then delegates to payment and seat providers.
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests)
            throws InvalidPurchaseException {
        validator.validate(accountId, ticketTypeRequests);
        PurchaseSummary summary = calculator.summarize(ticketTypeRequests);
        paymentService.makePayment(accountId, summary.amountToPay());
        seatReservationService.reserveSeat(accountId, summary.seatsToReserve());
    }
}