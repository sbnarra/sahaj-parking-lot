package ai.sahaj.arran.stobbs.model;

import java.util.List;

public record SpotConfiguration(List<String> type, int spots, List<IntervalCost> intervals) {
}
