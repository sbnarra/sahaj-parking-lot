package ai.sahaj.arran.stobbs.lot.standard;

import ai.sahaj.arran.stobbs.lot.FeeCalculator;
import ai.sahaj.arran.stobbs.model.IntervalCost;
import ai.sahaj.arran.stobbs.model.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
public class StandardFeeCalculator implements FeeCalculator {
    // calculate for the window in order to accumulate or calculate for entire duration
    private final boolean accumulate;

    @Override
    public double calculate(Ticket ticket, LocalDateTime exit) {
        Duration duration = Duration.between(ticket.entry(), exit);
        long hoursParked = TimeUnit.SECONDS.toHours(duration.getSeconds()) + 1;
        log.info("vehicle has been parked for {} hours", hoursParked);

        AtomicReference<Double> cost = new AtomicReference<>(0.0);

        ticket.costs().forEach(c -> {
            if (hoursParked > c.from()) {
                if (c.from() > hoursParked) {
                    return; // skip, doesn't apply when hours parked is less than the duration this cost kicks in
                }

                double additionalCost = typeBasedCalculation(c, hoursParked);

                if (accumulate) {
                    cost.set(additionalCost + cost.get());
                } else {
                    cost.set(additionalCost);
                }
                log.info("Added cost to fee {}, new total is {}", additionalCost, cost);
            }
        });

        return cost.get();
    }

    private double typeBasedCalculation(IntervalCost c, long hoursParked) {
        return switch (c.type()) {
            case PER_HOUR -> {
                if (accumulate) {
                    long hoursChargedFor = hoursParked - c.from();
                    if (c.till() > 0) hoursChargedFor -= c.till();
                    yield hoursChargedFor * c.price();
                } else {
                    yield hoursParked * c.price();
                }
            }
            case PER_DAY -> {
                if (accumulate) {
                    long daysChargedFor = hoursParked - c.from();
                    if (c.till() > 0) daysChargedFor -= c.till();
                    yield TimeUnit.HOURS.toDays(daysChargedFor) * c.price();
                } else {
                    yield (TimeUnit.HOURS.toDays(hoursParked) + 1/*round up*/) * c.price();
                }
            }
            case FLAT_RATE -> c.price();
        };
    }
}
