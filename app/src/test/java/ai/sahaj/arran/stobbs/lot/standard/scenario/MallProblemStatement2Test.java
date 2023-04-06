package ai.sahaj.arran.stobbs.lot.standard.scenario;

import ai.sahaj.arran.stobbs.lot.ParkingLot;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.lot.ParkingLotFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MallProblemStatement2Test extends AbstractProblemStatementTest {
    private final static ParkingLotFactory factory = new ParkingLotFactory();

    @Test
    public void testMallParkingLot() throws ParkingLotError {
        ParkingLot lot = factory.forMall(1, 1, 1);

        testParking(lot, "motorcycle", duration(3, 30), 40);
        testParking(lot, "car", duration(6, 1), 140);
        testParking(lot, "truck", duration(1, 59), 100);
    }
}
