package net.println.kt10.java;

/**
 * Created by benny on 11/13/16.
 */
public class LazyThreadSafeStaticInnerClass {

    private static class Holder{
        private static LazyThreadSafeStaticInnerClass INSTANCE = new LazyThreadSafeStaticInnerClass();
    }

    private LazyThreadSafeStaticInnerClass(){}

    public static LazyThreadSafeStaticInnerClass getInstance(){
        return Holder.INSTANCE;
    }
}
