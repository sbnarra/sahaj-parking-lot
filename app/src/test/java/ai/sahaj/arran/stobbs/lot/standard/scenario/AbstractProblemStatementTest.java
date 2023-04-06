package ai.sahaj.arran.stobbs.lot.standard.scenario;

import ai.sahaj.arran.stobbs.lot.ParkingLot;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.lot.ParkingLotFactory;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractProblemStatementTest {
    protected final static ParkingLotFactory factory = new ParkingLotFactory();
    private int parkingCounter = 0;

    protected void testParking(ParkingLot lot, String vehicleType, Duration length, double expectedFees) throws ParkingLotError {
        parkingCounter++;

        LocalDateTime entry = LocalDateTime.now();
        LocalDateTime exit = entry.plus(length);

        var ticket = lot.park(vehicleType, entry);
        assertEquals("00" + parkingCounter, ticket.ticketNumber());
        assertEquals(1, ticket.spotNumber());
        assertEquals(entry, ticket.entry());

        var receipt = lot.unpark(ticket.ticketNumber(), exit);
        assertEquals("R-00" + parkingCounter, receipt.receiptNumber());
        assertEquals(entry, receipt.ticket().entry());
        assertEquals(expectedFees, receipt.fees());
        assertEquals(exit, receipt.exit());
    }

    protected Duration duration(int days, int hours, int minutes) {
        return duration((days * 24) + hours, minutes);
    }

    protected Duration duration(int hours, int minutes) {
        return Duration.ofMinutes(hours * 60L + minutes);
    }
}
