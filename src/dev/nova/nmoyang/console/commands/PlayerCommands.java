package dev.nova.nmoyang.console.commands;

import dev.nova.nmoyang.Main;
import dev.nova.nmoyang.console.Command;

import java.util.UUID;

public class PlayerCommands extends Command {
    public PlayerCommands() {
        super("player", "Commands to handle player stuff");
    }

    @Override
    public void execute(String[] args) {

        if(args.length == 0) {
            System.out.println("Please specifty an action.");
        }

        /*
        Sub commands that require only one argument (usually only the player name / uuid)
         */
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("get-uuid")){
                UUID uuid = Main.getAPI().getUserUUID(args[1]);

                if(uuid != null){
                    System.out.println("The UUID of: "+args[1]+" is: "+uuid);
                }else{
                    System.out.println("Couldn't get a UUID corresponding to this username! (P.R: Request Timeout, Unexisting username)");
                }
            }
        }

    }
}
