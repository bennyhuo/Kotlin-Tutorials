package net.println.kt15;

import java.util.ArrayList;

/**
 * Created by benny on 12/17/16.
 */
public class SAMInJava {

    private ArrayList<Runnable> runnables = new ArrayList<Runnable>();

    public void addTask(Runnable runnable){
        runnables.add(runnable);
        System.out.println("After add: " + runnable + ", we have " + runnables.size() + " in all.");
    }

    public void removeTask(Runnable runnable){
        runnables.remove(runnable);
        System.out.println("After remove: " + runnable + ", only " + runnables.size() + " left.");
    }

}
