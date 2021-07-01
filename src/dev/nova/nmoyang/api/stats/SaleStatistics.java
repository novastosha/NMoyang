package dev.nova.nmoyang.api.stats;

public class SaleStatistics {

    private final SaleStatisticsType statisticsType;
    private final int lastDay;
    private final int total;
    private final int averagePerSecond;

    public SaleStatistics(SaleStatisticsType statisticsType, int lastDay, int total, int averagePerSecond){
        this.statisticsType = statisticsType;
        this.lastDay = lastDay;
        this.total = total;
        this.averagePerSecond = averagePerSecond;
    }

    public int getAveragePerSecond() {
        return averagePerSecond;
    }

    public int getLastDay() {
        return lastDay;
    }

    public int getTotal() {
        return total;
    }

    public SaleStatisticsType getStatisticsType() {
        return statisticsType;
    }
}
