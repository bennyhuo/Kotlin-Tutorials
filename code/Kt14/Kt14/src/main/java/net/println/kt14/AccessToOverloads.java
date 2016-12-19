package net.println.kt14;

/**
 * Created by benny on 12/18/16.
 */
public class AccessToOverloads {
    public static void main(String... args) {
        Overloads overloads = new Overloads();
        overloads.overloaded(1, 2, 3);
        overloads.overloaded(1);
        overloads.overloaded(1,3);
    }
}
