package ai.sahaj.arran.stobbs.repository;

import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class InMemoryTicketRepositoryTest {

    private final TicketRepository repository = new InMemoryTicketRepository();

    @Test
    public void testSaveAndFind() throws ParkingLotError {
        Ticket ticket = new Ticket(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                -1,
                List.of(),
                LocalDateTime.now());
        repository.save(ticket);

        Ticket found = repository.find(ticket.ticketNumber());
        Assertions.assertEquals(ticket, found);
    }

    @Test
    public void testSave_nullTicket() {
        var error = Assertions.assertThrows(NullPointerException.class, () -> repository.save(null));
        Assertions.assertEquals("Null Ticket", error.getMessage());
    }

    @Test
    public void testSave_missingTicketNumber() {
        Ticket ticket = new Ticket(
                null,
                UUID.randomUUID().toString(),
                -1,
                List.of(),
                LocalDateTime.now());
        var error = Assertions.assertThrows(NullPointerException.class, () -> repository.save(ticket));
        Assertions.assertEquals("Missing Ticket Number", error.getMessage());
    }

    @Test
    public void testTicketNotFound() {
        var error = Assertions.assertThrows(ParkingLotError.class, () -> repository.find(null));
        Assertions.assertEquals("Ticket null Not Found", error.getMessage());

        var id = UUID.randomUUID().toString();
        error = Assertions.assertThrows(ParkingLotError.class, () -> repository.find(id));
        Assertions.assertEquals("Ticket " + id + " Not Found", error.getMessage());
    }
}
