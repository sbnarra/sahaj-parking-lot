package ai.sahaj.arran.stobbs.lot;

import ai.sahaj.arran.stobbs.model.Receipt;
import ai.sahaj.arran.stobbs.model.Ticket;

import java.time.LocalDateTime;

public interface ParkingLot {
    Ticket park(String vehicleType, LocalDateTime entry) throws ParkingLotError;
    Receipt unpark(String ticketNumber, LocalDateTime exit) throws ParkingLotError;
}
