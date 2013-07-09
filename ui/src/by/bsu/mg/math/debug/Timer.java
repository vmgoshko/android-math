package by.bsu.mg.math.debug;

import android.util.Log;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Timer {
    private long begin;

    public void start() {
        begin = System.currentTimeMillis();
    }

    public void stop() {
        double time = (System.currentTimeMillis() - begin) / 1000.0;
        Log.d("____TASK TIMER____", "Task finished in " + time + " secs");
    }
}
