package ai.sahaj.arran.stobbs.lot;

import ai.sahaj.arran.stobbs.model.Ticket;

import java.time.LocalDateTime;

public interface FeeCalculator {
    double calculate(Ticket ticket, LocalDateTime exit);
}
