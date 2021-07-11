package dev.nova.nmoyang;

import dev.nova.nmoyang.api.Mojang;
import dev.nova.nmoyang.console.Command;
import dev.nova.nmoyang.console.commands.PlayerCommands;
import dev.nova.nmoyang.console.commands.StatsCommand;
import dev.nova.nmoyang.console.commands.StatusCommand;
import dev.nova.nmoyang.console.commands.UpdateNameCommand;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Mojang API;

    public static void main(String[] args) throws LoginException, IOException {
        API = new Mojang();

        registerCommands(new StatusCommand(),new PlayerCommands(),new StatsCommand());
            if(API.isAuthMode()){
                System.out.println("The mojang api instance is in authentication mode! Enabling auth-required commands.");
                registerCommands(new UpdateNameCommand());
            }
            commandMode("You have entered command mode!");
    }

    private static void commandMode(String s) {

        Scanner scanner = new Scanner(System.in);

        if(s != null) {
            System.out.println(s);
        }
        String command = scanner.nextLine();

        if(command.isEmpty()){
            commandMode("Command cannot be empty! Try again!");
            return;
        }

        String[] argsRaw = command.split(" ");
        String cmd = argsRaw[0];
        String[] args = new String[0];
        if(argsRaw.length != 1) {
            args = new String[argsRaw.length - 1];
            int index = 0;
            for (String c : argsRaw) {
                if (index != 0) {
                    args[index - 1] = c;
                }
                index++;
            }
        }



        Command cmdCls = Command.getCommand(cmd);

        if(cmdCls == null) {
            commandMode("Could not find this command!");
            return;
        }

        cmdCls.execute(args);

        commandMode(null);
    }

    public static Mojang getAPI() {
        return API;
    }

    public static void registerCommands(Command cmd, Command... commands) {
        Command.COMMANDS.add(cmd);

        Command.COMMANDS.addAll(Arrays.asList(commands));
    }
}
