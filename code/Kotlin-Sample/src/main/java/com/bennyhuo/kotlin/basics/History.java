package com.bennyhuo.kotlin.basics;

/**
 * Created by benny.
 */
public class History {

    private long when;
    private String eventName;

    public History(long when, String eventName) {
        this.when = when;
        this.eventName = eventName;
    }

    public long when() {
        return when;
    }

    public String eventName() {
        return eventName;
    }
}
