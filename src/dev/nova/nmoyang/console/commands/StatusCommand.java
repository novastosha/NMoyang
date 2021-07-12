package dev.nova.nmoyang.console.commands;

import dev.nova.nmoyang.Main;
import dev.nova.nmoyang.api.MojangServiceType;
import dev.nova.nmoyang.console.Command;

public class StatusCommand extends Command {
    public StatusCommand() {
        super("status","Checks status of all or one of mojang servers");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            System.out.println("Please specify a type");
            for(MojangServiceType serverType : MojangServiceType.values()){
                System.out.println("  - "+serverType.name());
            }
            System.out.println("  - ALL");
            return;
        }

            if(!args[0].equalsIgnoreCase("all")){
                try{
                    MojangServiceType serverType = MojangServiceType.valueOf(args[0].toUpperCase());
                    System.out.println(serverType.name()+" is: "+Main.getAPI().getStatus(serverType));
                }catch (IllegalArgumentException e){
                    System.out.println("Unknown server type!");
                }
            }else{
                for(MojangServiceType serverType : MojangServiceType.values()){
                    System.out.println("- "+serverType.name()+" is: "+Main.getAPI().getStatus(serverType));
                }
            }
    }
}
