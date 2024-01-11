package com.atguigu.utils;

import java.util.Timer;
import java.util.TimerTask;

public class UtilStorage {
    public static String confirm;
    public static void resetConfirm()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                confirm=null;
            }
        }, 6000*5);
    }

}
