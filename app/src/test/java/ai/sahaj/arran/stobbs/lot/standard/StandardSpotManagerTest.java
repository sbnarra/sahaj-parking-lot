package ai.sahaj.arran.stobbs.lot.standard;

import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.model.IntervalCost;
import ai.sahaj.arran.stobbs.model.SpotConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StandardSpotManagerTest {

    @Test
    public void testNullConfig() {
        var error = Assertions.assertThrows(NullPointerException.class, () -> new StandardSpotManager(null));
        Assertions.assertEquals("Null Spot Configuration", error.getMessage());
    }

    @Test
    public void testObtainNoSpots() {
        StandardSpotManager spotManager = new StandardSpotManager(List.of(
                new SpotConfiguration(List.of("test"), 0, List.of(new IntervalCost(1.0, 0, 1, IntervalCost.Type.PER_DAY)))
        ));

        var error = Assertions.assertThrows(ParkingLotError.class, () -> spotManager.obtain("test"));
        Assertions.assertEquals("No space available", error.getMessage());
    }

    @Test
    public void testObtain() throws ParkingLotError {
        StandardSpotManager spotManager = new StandardSpotManager(List.of(
                new SpotConfiguration(List.of("test"), 1, List.of(new IntervalCost(1.0, 0, 1, IntervalCost.Type.PER_DAY)))
        ));

        var spot = spotManager.obtain("test");
        Assertions.assertEquals(1, spot.spotNumber());
    }

    @Test
    public void testRelease() throws ParkingLotError {

        StandardSpotManager spotManager = new StandardSpotManager(List.of(
                new SpotConfiguration(List.of("test"), 1, List.of(new IntervalCost(1.0, 0, 1, IntervalCost.Type.PER_DAY)))
        ));

        var spot = spotManager.obtain("test");
        spotManager.release("test", spot.spotNumber());
    }

    @Test
    public void testReleaseErrors() {

        StandardSpotManager spotManager = new StandardSpotManager(List.of(
                new SpotConfiguration(List.of("test"), 1, List.of(new IntervalCost(1.0, 0, 1, IntervalCost.Type.PER_DAY)))
        ));

        Throwable error = Assertions.assertThrows(ParkingLotError.class, () -> spotManager.release(null, 0));
        Assertions.assertEquals("Spot Number Less Than 1", error.getMessage());

        error = Assertions.assertThrows(NullPointerException.class, () -> spotManager.release(null, 2));
        Assertions.assertEquals("Null Vehicle Type", error.getMessage());

        error = Assertions.assertThrows(ParkingLotError.class, () -> spotManager.release("other", 2));
        Assertions.assertEquals("Unknown vehicle type: other", error.getMessage());

        error = Assertions.assertThrows(ParkingLotError.class, () -> spotManager.release("test", 2));
        Assertions.assertEquals("Spot Number Greater Than Max Spots", error.getMessage());

        error = Assertions.assertThrows(IllegalStateException.class, () -> spotManager.release("test", 1));
        Assertions.assertEquals("Queue full", error.getMessage());
    }
}
