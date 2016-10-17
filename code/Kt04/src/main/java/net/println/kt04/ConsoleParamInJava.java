package net.println.kt04;

/**
 * Created by benny on 10/14/16.
 */
public class ConsoleParamInJava {
    public static void main(String... args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
