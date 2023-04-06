package ai.sahaj.arran.stobbs.repository;

import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.model.Ticket;

public interface TicketRepository {
    Ticket find(String ticketNumber) throws ParkingLotError;

    void save(Ticket ticket);
}
