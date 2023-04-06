package ai.sahaj.arran.stobbs.lot.standard.scenario;

import ai.sahaj.arran.stobbs.lot.ParkingLot;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import org.junit.jupiter.api.Test;

public class AirportProblemStatementTest extends AbstractProblemStatementTest {

    @Test
    public void testAirportParkingLot() throws ParkingLotError {
        ParkingLot lot = factory.forAirport(1, 1);

        testParking(lot, "motorcycle", duration(0, 55), 0);
        testParking(lot, "motorcycle", duration(14, 59), 60);
        testParking(lot, "motorcycle", duration(1, 12, 0), 160);
        testParking(lot, "car", duration(0, 50), 60);
        testParking(lot, "suv", duration(23, 59), 80);
        testParking(lot, "car", duration(3, 1, 0), 400);
    }
}
