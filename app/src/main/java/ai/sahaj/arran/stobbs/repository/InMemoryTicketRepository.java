package ai.sahaj.arran.stobbs.repository;

import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.model.Ticket;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class InMemoryTicketRepository implements TicketRepository {
    private final Map<String, Ticket> tickets = new HashMap<>();

    @Override
    public Ticket find(String ticketNumber) throws ParkingLotError {
        var ticket = tickets.get(ticketNumber);
        if (ticket == null) {
            throw new ParkingLotError("Ticket " + ticketNumber + " Not Found");
        }
        return ticket;
    }

    @Override
    public void save(Ticket ticket) {
        var ticketNumber = requireNonNull(ticket, "Null Ticket").ticketNumber();
        tickets.put(requireNonNull(ticketNumber, "Missing Ticket Number"), ticket);
    }
}
