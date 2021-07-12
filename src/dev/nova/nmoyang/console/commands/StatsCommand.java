package dev.nova.nmoyang.console.commands;

import dev.nova.nmoyang.MainApplication;
import dev.nova.nmoyang.api.stats.SaleStatistics;
import dev.nova.nmoyang.api.stats.SaleStatisticsType;
import dev.nova.nmoyang.console.Command;

public class StatsCommand extends Command {

    public StatsCommand() {
        super("stats","Get stats about sales.");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0){
            System.out.println("Please specify a type!");
            return;
        }

        try{

            SaleStatisticsType type = SaleStatisticsType.valueOf(args[0].toUpperCase());

            SaleStatistics saleStatistics = MainApplication.getAPI().getSaleStatistics(type);

            if(saleStatistics != null) {

                System.out.println("Statistics for: " + type.name());
                System.out.println();
                System.out.println("Total: " +saleStatistics.getTotal());
                System.out.println("24 hours: " +saleStatistics.getLastDay());
                System.out.println("Avg. per second: " +saleStatistics.getAveragePerSecond());
            }else{
                System.out.println("Couldn't get stats!");
            }

        }catch (IllegalArgumentException e) {
            System.out.println("Unknown type!");
        }
    }
}
