package net.println.kt15;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny on 12/17/16.
 */
public class Generics {

    public abstract class View<P extends Presenter>{
        P presenter;
    }

    public abstract class Presenter<V extends View>{
        V view;
    }

    public static void main(String... args) {
        List list = new ArrayList();
        list.add("Hello");
        list.add(0);
        for (Object o : list) {
            System.out.println(o);
        }
    }

}
