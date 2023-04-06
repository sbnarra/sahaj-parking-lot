package ai.sahaj.arran.stobbs.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(fluent = true) // same accessors as record type
public class Ticket {
    private final String ticketNumber;
    private final String vehicleType;
    private final int spotNumber;
    private final List<IntervalCost> costs;
    private final LocalDateTime entry;
    private Status status = Status.PARKED;
    public enum Status {
        PARKED,
        UNPAID,
        PAID
    }
}
