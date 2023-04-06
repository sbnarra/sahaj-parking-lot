package ai.sahaj.arran.stobbs.model;

import java.time.LocalDateTime;

public record Receipt(
        String receiptNumber,
        Ticket ticket,
        LocalDateTime exit,
        double fees
) {
}
