package ai.sahaj.arran.stobbs.model;

public record IntervalCost(double price, int from, int till, Type type) {
    public enum Type {
        PER_HOUR,
        PER_DAY,
        FLAT_RATE
    }
}
