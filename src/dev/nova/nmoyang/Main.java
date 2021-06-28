package dev.nova.nmoyang;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
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
    }

}
