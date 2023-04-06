package ai.sahaj.arran.stobbs.lot;

import ai.sahaj.arran.stobbs.model.IntervalCost;

import java.util.List;

public interface SpotManager {
    record Obtained(int spotNumber, List<IntervalCost> costs) {}

    Obtained obtain(String vehicleType) throws ParkingLotError;
    void release(String vehicleType, int spotNumber) throws ParkingLotError;
}
