package dev.nova.nmoyang.console.commands;

import dev.nova.nmoyang.Main;
import dev.nova.nmoyang.console.Command;

public class APICommand extends Command {
    public APICommand() {
        super("api","API command");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0){
            System.out.println("Please specify an action!");
            return;
        }

        if(args[0].equalsIgnoreCase("status")){
            if(Main.getAPI().isUp()){
                System.out.println("Mojang API is working!");
            }else{
                System.out.println("The mojang API is down.");
            }
        }
    }
}
