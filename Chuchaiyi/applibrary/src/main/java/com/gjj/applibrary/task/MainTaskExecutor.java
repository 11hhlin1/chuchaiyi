package com.gjj.applibrary.task;

import android.os.Handler;
import android.os.Looper;

public class MainTaskExecutor {
    private static Handler sMainHandler = null;

    public static void scheduleTaskOnUiThread(long delay, Runnable task) {
        ensureMainHandler();
        sMainHandler.postDelayed(task, delay);
    }
    
    public static void cancelTaskOnUiThread(Runnable task) {
        ensureMainHandler();
        sMainHandler.removeCallbacks(task);
    }

    public static void runTaskOnUiThread(Runnable task) {
        ensureMainHandler();
        sMainHandler.post(task);
    }
    
    public static void runTaskOnUiThreadImmediately(Runnable task) {
        ensureMainHandler();
        sMainHandler.postAtFrontOfQueue(task);
    }

    public static void removeRunnable(Runnable task) {
        if (sMainHandler != null) {
            sMainHandler.removeCallbacks(task);
        }
    }

    private synchronized static void ensureMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
    }
}
