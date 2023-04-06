package ai.sahaj.arran.stobbs.lot;

import ai.sahaj.arran.stobbs.lot.standard.StandardFeeCalculator;
import ai.sahaj.arran.stobbs.lot.standard.StandardParkingLot;
import ai.sahaj.arran.stobbs.lot.standard.StandardSpotManager;
import ai.sahaj.arran.stobbs.model.SpotConfiguration;
import ai.sahaj.arran.stobbs.model.IntervalCost;
import ai.sahaj.arran.stobbs.repository.InMemoryTicketRepository;
import ai.sahaj.arran.stobbs.repository.TicketRepository;

import java.util.List;

public class ParkingLotFactory {

    private ParkingLot createParkingLot(boolean accumulate, List<SpotConfiguration> config) {
        TicketRepository ticketRepository = new InMemoryTicketRepository();
        FeeCalculator feeCalculator = new StandardFeeCalculator(accumulate);
        SpotManager spotManager = new StandardSpotManager(config);
        return new StandardParkingLot(ticketRepository, feeCalculator, spotManager);
    }

    public ParkingLot forMall(int motorcycleSpots, int carSpots, int largeVehicleSpots) {
        return createParkingLot(true, List.of(
                new SpotConfiguration(
                        List.of("Motorcycle", "Scooter"),
                        motorcycleSpots,
                        List.of(
                                new IntervalCost(10, 0, 0, IntervalCost.Type.PER_HOUR)
                        )),

                new SpotConfiguration(
                        List.of("Car", "SUV"),
                        carSpots,
                        List.of(
                                new IntervalCost(20, 0, 0, IntervalCost.Type.PER_HOUR)
                        )),

                new SpotConfiguration(
                        List.of("Bus", "Truck"),
                        largeVehicleSpots,
                        List.of(
                                new IntervalCost(50, 0, 0, IntervalCost.Type.PER_HOUR)
                        ))
        ));
    }

    public ParkingLot forStadium(int motorcycleSpots, int carSpots) {
        return createParkingLot(true, List.of(
                new SpotConfiguration(
                        List.of("Motorcycle", "Scooter"),
                        motorcycleSpots,
                        List.of(
                                new IntervalCost(30, 0, 4, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(60, 4, 12, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(100, 12, -1, IntervalCost.Type.PER_HOUR)
                        )),

                new SpotConfiguration(
                        List.of("Car", "SUV"),
                        carSpots,
                        List.of(
                                new IntervalCost(60, 0, 4, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(120, 4, 12, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(200, 12, -1, IntervalCost.Type.PER_HOUR)
                        ))
        ));
    }

    public ParkingLot forAirport(int motorcycleSpots, int carSpots) {
        return createParkingLot(false, List.of(
                new SpotConfiguration(
                        List.of("Motorcycle", "Scooter"),
                        motorcycleSpots,
                        List.of(
                                new IntervalCost(0, 0, 1, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(40, 1, 8, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(60, 8, 24, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(80, 24, -1, IntervalCost.Type.PER_DAY)
                        )),

                new SpotConfiguration(
                        List.of("Car", "SUV"),
                        carSpots,
                        List.of(
                                new IntervalCost(60, 0, 12, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(80, 12, 24, IntervalCost.Type.FLAT_RATE),
                                new IntervalCost(100, 24, -1, IntervalCost.Type.PER_DAY)
                        ))
        ));
    }
}
