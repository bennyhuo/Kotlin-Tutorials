package net.println.kt05;

/**
 * Created by benny on 10/14/16.
 */
public class ConsoleParamInJava {
    public static void main(String... args) {
        for (String arg: args){
            String[] splits = arg.split("_");
            for (String split : splits) {
                System.out.print(split);
                System.out.print(" ");
            }
        }
    }
}
