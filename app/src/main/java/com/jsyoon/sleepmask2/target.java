package com.jsyoon.sleepmask2;


public class target {
    // custom shared key
    public static final boolean enableCustonkey = false;

    private target(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}
