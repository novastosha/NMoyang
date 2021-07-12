package dev.nova.nmoyang.console.commands;

import dev.nova.nmoyang.MainApplication;
import dev.nova.nmoyang.api.player.Name;
import dev.nova.nmoyang.api.player.Profile;
import dev.nova.nmoyang.console.Command;

import java.text.SimpleDateFormat;
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

                UUID uuid = MainApplication.getAPI().getUserUUID(args[1]);

                if(uuid != null){
                    System.out.println("The UUID of: "+args[1]+" is: "+uuid);
                }else{
                    System.out.println("Couldn't get a UUID corresponding to this username! (P.R: Request Timeout, Unexisting username)");
                }
            }
            if(args[0].equalsIgnoreCase("get-name")){
                try{

                    UUID uuid = UUID.fromString(args[1]);

                    System.out.println(MainApplication.getAPI().getName(uuid));

                }catch (Exception e) {
                    System.out.println("An error occurred!");
                }
            }
            if(args[0].equalsIgnoreCase("get-namehistory")){
                try{

                    UUID uuid = UUID.fromString(args[1]);

                    for(Name name : MainApplication.getAPI().getNameHistory(uuid)) {
                        if(name != null) {
                            java.util.Date time = new java.util.Date(name.getChangedAt());
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                            if(name.getChangedAt() != 0) {
                                System.out.println("Name changed to: " + '"' + name.getName() + '"' + " changed at: " + formatter.format(time));
                            }else{
                                System.out.println(name.getName()+" is the initial name of: "+ MainApplication.getAPI().getName(uuid));
                            }
                        }
                    }
                }catch (IllegalArgumentException e) {
                    UUID uuid;
                    try {
                        uuid = MainApplication.getAPI().getUserUUID(args[1]);
                    }catch (Exception ex) {
                        System.out.println("Invalid UUID / Username!");
                        return;
                    }

                    execute(new String[]{args[0],uuid.toString()});
                }
            }

            if(args[0].equalsIgnoreCase("get-profile")){
                try{

                    UUID uuid = UUID.fromString(args[1]);

                    Profile profile = MainApplication.getAPI().getProfile(uuid);



                    if(profile != null){


                        System.out.println("Profile ID: "+profile.getProfileID());
                        System.out.println();
                        System.out.println("Skin URL: "+profile.getSkin().getSkinURL().toString());
                        System.out.println("Is skin slim: "+profile.getSkin().isSlim());
                        System.out.println();
                        if(profile.getCape() != null){
                            System.out.println("Cape URL: "+profile.getCape().toString());
                        }else {
                            System.out.println("This user has no cape!");
                        }
                    }else{
                        System.out.println("Unable to get profile!");
                    }
                }catch (IllegalArgumentException e) {
                    UUID uuid;
                    try {
                        uuid = MainApplication.getAPI().getUserUUID(args[1]);
                    }catch (Exception ex) {
                        System.out.println("Invalid UUID / Username!");
                        return;
                    }

                    execute(new String[]{args[0],uuid.toString()});
                }
            }
        }

    }
}
