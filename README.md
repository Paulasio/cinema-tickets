This project is an implementation of the Cinema Tickets coding exercise.  
It provides a working solution to the requirements using clean object-oriented design and automated tests.


 Business Rules

- There are three types of tickets:
  - Adult → £25
  - Child → £15
  - Infant → £0
- A maximum of 25 tickets can be purchased in one transaction.
- Infants do not pay and do not occupy a seat (they sit on an adult’s lap).
- Children and infants cannot be purchased without at least one adult.
- All payments go through the provided `TicketPaymentService`.
- All seat reservations go through the provided `SeatReservationServicDesign Choices

-Separation of concerns

model holds core domain entities like TicketType.

domain contains aggregates such as PurchaseSummary.

service contains business logic for validation, pricing, and calculations.

-Validation first
All purchase requests go through PurchaseValidator to enforce the business rules before any calculation or external service call.

-Calculation isolated
PurchaseCalculator is responsible only for computing totals and seats. This makes it easy to test independently.

-Interactions with external services
The TicketServiceImpl is the orchestration layer. It validates, calculates, then delegates to the external TicketPaymentService and SeatReservationService