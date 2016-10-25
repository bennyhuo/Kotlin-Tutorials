package net.println.kt07;

import java.util.ArrayList;

/**
 * Created by benny on 10/21/16.
 */
public class ACertainJavaClass {

    private ArrayList<Runnable> runnables = new ArrayList<>();

    public void addRunnable(Runnable runnable){
        runnables.add(runnable);
        System.out.println("Add a runnable!!" + runnable);
    }

    public void removeRunnable(Runnable runnable){
        if(runnables.remove(runnable)){
            System.out.println("Remove successfully!!");
        } else {
            System.out.println("Where is my Runnable!!! " + runnable);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener){

    }

    interface OnClickListener{
        void onClick(String string, String anotherString);
    }
}
