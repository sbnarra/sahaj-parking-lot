package ai.sahaj.arran.stobbs.lot.standard.scenario;

import ai.sahaj.arran.stobbs.lot.ParkingLot;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.lot.ParkingLotFactory;
import ai.sahaj.arran.stobbs.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MallProblemStatement1Test {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
    private final static ParkingLot lot = new ParkingLotFactory().forMall(2, 0, 0);

    private final static LocalDateTime entryTime1 = LocalDateTime.parse("29-May-2022 14:04:07", formatter);
    private static Ticket parked1;

    private final static LocalDateTime entryTime2 = LocalDateTime.parse("29-May-2022 14:44:07", formatter);
    private static Ticket parked2;

    @Test
    @Order(1)
    public void testSuccessfullyParkMotorCycle() throws ParkingLotError {
        parked1 = lot.park("motorcycle", entryTime1);
        assertEquals("001", parked1.ticketNumber());
        assertEquals(1, parked1.spotNumber());
        assertEquals(entryTime1, parked1.entry());
    }

    @Test
    @Order(2)
    public void testSuccessfullyParkScooter() throws ParkingLotError {
        parked2 = lot.park("scooter", entryTime2);
        assertEquals("002", parked2.ticketNumber());
        assertEquals(2, parked2.spotNumber());
        assertEquals(entryTime2, parked2.entry());
    }

    @Test
    @Order(3)
    public void testFailureParkFirstScooter_thenNoSpaceAvailable() {
        var error = Assertions.assertThrowsExactly(ParkingLotError.class, () ->
                lot.park("scooter", LocalDateTime.now()));
        assertEquals("No space available", error.getMessage());
    }

    @Test
    @Order(4)
    public void testSuccessfullyUnparkScooter() throws ParkingLotError {
        var exitTime2 = LocalDateTime.parse("29-May-2022 15:40:07", formatter);
        var ticket2 = lot.unpark(parked2.ticketNumber(), exitTime2);
        assertEquals("R-001", ticket2.receiptNumber());
        assertEquals(entryTime2, ticket2.ticket().entry());
        assertEquals(10, ticket2.fees());
        assertEquals(exitTime2, ticket2.exit());
    }

    @Test
    @Order(5)
    public void testSuccessfullyParkAnotherMotorcycle() throws ParkingLotError {
        var entryTime3 = LocalDateTime.parse("29-May-2022 15:59:07", formatter);
        var parked3 = lot.park("motorcycle", entryTime3);
        assertEquals("003", parked3.ticketNumber());
        assertEquals(2, parked3.spotNumber());
        assertEquals(entryTime3, parked3.entry());
    }

    @Test
    @Order(6)
    public void testSuccessfullyUnparkFirstMotorcycle() throws ParkingLotError {
        var exitTime1 = LocalDateTime.parse("29-May-2022 17:44:07", formatter);
        var ticket1 = lot.unpark(parked1.ticketNumber(), exitTime1);
        assertEquals("R-002", ticket1.receiptNumber());
        assertEquals(entryTime1, ticket1.ticket().entry());
        assertEquals(40, ticket1.fees());
        assertEquals(exitTime1, ticket1.exit());

    }
}
