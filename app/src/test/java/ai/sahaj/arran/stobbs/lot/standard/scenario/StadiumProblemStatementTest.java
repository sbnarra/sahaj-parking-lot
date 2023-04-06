package ai.sahaj.arran.stobbs.lot.standard.scenario;

import ai.sahaj.arran.stobbs.lot.ParkingLot;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import org.junit.jupiter.api.Test;

public class StadiumProblemStatementTest extends AbstractProblemStatementTest {

    @Test
    public void testStadiumParkingLot() throws ParkingLotError {
        ParkingLot lot = factory.forStadium(1, 1);

        testParking(lot, "motorcycle", duration(3, 40), 30);
        testParking(lot, "motorcycle", duration(14, 59), 390);
        testParking(lot, "suv", duration(11, 30), 180);
        testParking(lot, "suv", duration(13, 5), 580);
    }
}
