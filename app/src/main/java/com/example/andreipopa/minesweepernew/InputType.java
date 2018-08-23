package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class InputType {

    public static final int UNDEFINED_INPUT= -121211;
    public static final int FLAG=1111;
    public static final int DETONATE=2222;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNDEFINED_INPUT, FLAG,DETONATE})
    @interface InputTypeInterf{}

    private int mode;
    public void setMode(@InputTypeInterf int mode){
        this.mode= mode;
    }

    @InputTypeInterf
    public int getMode(){
        return mode;
    }
}
