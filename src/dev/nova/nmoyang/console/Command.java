package dev.nova.nmoyang.console;

import java.util.ArrayList;

public abstract class Command {

    public static final ArrayList<Command> COMMANDS = new ArrayList<>();

    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name.replaceAll(" ","-");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public static Command getCommand(String name) {
        for(Command command : COMMANDS) {

            if(command.getName().equalsIgnoreCase(name)) return command;

        }
        return null;
    }

    public abstract void execute(String[] args);
}