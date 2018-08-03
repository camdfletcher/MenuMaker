package me.cdnmflip.menumaker.utilities;

public enum Repeatedly {

    /*
     * A utility for running code a certain amount of times
     */;

    public static void execute(int times, Runnable action) {
        for (int i = 0; i < times; i++) action.run();
    }

}
