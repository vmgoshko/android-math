package by.bsu.mg.math.debug;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: vmgoshko
 * Date: 31.03.13
 * Time: 1:47
 * To change this template use File | Settings | File Templates.
 */
public class Timer {
    private long begin;

    public void start(){
        begin = System.currentTimeMillis();
    }

    public void stop(){
        double time = (System.currentTimeMillis() - begin) / 1000.0;
        Log.d("____TASK TIMER____", "Task finished in " + time + " secs") ;
    }
}
