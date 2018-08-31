package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class UncoverSituation {

    public static final int ON_REVEALED_TILE=1616;
    public static final int ON_EMPTY_TILE=1717;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ON_REVEALED_TILE,ON_EMPTY_TILE})
    @interface UncoverInterf{}

    private int mode;
    public void setMode(@UncoverInterf int mode){
        this.mode= mode;
    }

    @UncoverInterf
    public int getMode(){
        return mode;
    }

}
