package me.cdnmflip.menumaker.utilities;

public enum Do {

    /**
     * A utility for running code a certain amount of times
     */

    ;

    public static void many(int times, Runnable action) {
        for (int i = 0; i < times; i++) action.run();
    }

}
