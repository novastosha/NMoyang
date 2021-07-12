package dev.nova.nmoyang.api.stats;

public class SaleStatistics {

    private final SaleStatisticsType statisticsType;
    private final int lastDay;
    private final int total;
    private final int averagePerSecond;

    /**
     *
     * Creates an instance containg all data for a sale type.
     *
     * @param statisticsType Type of the sale.
     * @param lastDay Last day sales.
     * @param total Total sales.
     * @param averagePerSecond AVG. Per Second.
     */
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
