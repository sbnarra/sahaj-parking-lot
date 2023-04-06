package ai.sahaj.arran.stobbs.lot.standard;

import ai.sahaj.arran.stobbs.lot.FeeCalculator;
import ai.sahaj.arran.stobbs.lot.ParkingLot;
import ai.sahaj.arran.stobbs.lot.SpotManager;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.repository.TicketRepository;
import ai.sahaj.arran.stobbs.model.Receipt;
import ai.sahaj.arran.stobbs.model.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
public class StandardParkingLot implements ParkingLot {
    private final AtomicLong receiptCounter = new AtomicLong(Long.parseLong(System.getProperty("start-receipt-counter", "1")));
    private final AtomicLong ticketCounter = new AtomicLong(Long.parseLong(System.getProperty("start-ticket-counter", "1")));

    private final TicketRepository ticketRepository;
    private final FeeCalculator feeCalculator;
    private final SpotManager spotManager;

    @Override
    public Ticket park(String vehicleType, LocalDateTime entry) throws ParkingLotError {
        var obtained = spotManager.obtain(vehicleType);
        String ticketNumber = String.format("%03d", ticketCounter.getAndIncrement());
        Ticket ticket = new Ticket(ticketNumber, vehicleType, obtained.spotNumber(), obtained.costs(), entry);
        ticketRepository.save(ticket);
        log.info("produced ticket: {}", ticket);
        return ticket;
    }

    @Override
    public Receipt unpark(String ticketNumber, LocalDateTime exit) throws ParkingLotError {
        Ticket ticket = ticketRepository.find(ticketNumber);

        spotManager.release(ticket.vehicleType(), ticket.spotNumber());

        double fees = feeCalculator.calculate(ticket, exit);

        String receiptNumber = "R-" + String.format("%03d", receiptCounter.getAndIncrement());
        Receipt receipt = new Receipt(receiptNumber, ticket, exit, fees);
        log.info("produced receipt: {}", receipt);

        ticket.status(Ticket.Status.PAID);
        // in future, can issue unpaid receipt to allow separate payment
        // in real life, should save this update or just delete we don't care about keeping a record
        // (or add auditing via any mechanism, db table or event bus)

        return receipt;
    }
}
