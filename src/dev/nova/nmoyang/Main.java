package dev.nova.nmoyang;

import dev.nova.nmoyang.api.API;
import dev.nova.nmoyang.gui.GUI;

public class Main {

    private static API API;

    public static void main(String[] args) {
        API = new API();
        if(args.length == 0){
            guiMode();
        }else if (args.length == 1 && args[0].equalsIgnoreCase("consoleMode")){
            consoleMode();
        }else{
            consoleMode();
        }
    }

    private static void consoleMode() {

    }

    private static void guiMode() {
        GUI gui = new GUI();
    }

    public static API getAPI() {
        return API;
    }
}
