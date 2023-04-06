package ai.sahaj.arran.stobbs.lot.standard;

import ai.sahaj.arran.stobbs.lot.SpotManager;
import ai.sahaj.arran.stobbs.lot.ParkingLotError;
import ai.sahaj.arran.stobbs.model.IntervalCost;
import ai.sahaj.arran.stobbs.model.SpotConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

public class StandardSpotManager implements SpotManager {
    private record SpotQueue(
            Queue<Integer> spots,
            int max,
            List<IntervalCost> costs
    ) {}
    private final Map<String, SpotQueue> inUse;

    public StandardSpotManager(List<SpotConfiguration> spotConfigs) {
        inUse = new HashMap<>();

        requireNonNull(spotConfigs, "Null Spot Configuration").forEach(spotConfig -> {
            // setup spot queue for obtaining unused spots
            Queue<Integer> queue = null;
            if (spotConfig.spots() > 0) {
                queue = new ArrayBlockingQueue<>(spotConfig.spots());
                IntStream.range(1, spotConfig.spots() + 1).forEach(queue::add);
            } else {
                queue = new ArrayBlockingQueue<>(1);
            }

            var spotQueue = new SpotQueue(queue, spotConfig.spots(), spotConfig.intervals());
            // use same spot queue for same types
            spotConfig.type().forEach(type -> inUse.put(type.toLowerCase(), spotQueue));
        });
    }

    @Override
    public Obtained obtain(String vehicleType) throws ParkingLotError {
        var queue = getSpotQueue(vehicleType);
        var spot = queue.spots.poll();
        if (spot == null) throw new ParkingLotError("No space available");
        return new Obtained(spot, queue.costs);
    }
    @Override
    public void release(String vehicleType, int spotNumber) throws ParkingLotError {
        if (spotNumber < 1) throw new ParkingLotError("Spot Number Less Than 1");
        var queue = getSpotQueue(vehicleType);
        if (spotNumber > queue.max) throw new ParkingLotError("Spot Number Greater Than Max Spots");
        queue.spots.add(spotNumber);
    }

    private SpotQueue getSpotQueue(String vehicleType) throws ParkingLotError {
        var spotQueue = inUse.get(requireNonNull(vehicleType, "Null Vehicle Type").toLowerCase());
        if (spotQueue == null) throw new ParkingLotError("Unknown vehicle type: " + vehicleType);
        return spotQueue;
    }
}
