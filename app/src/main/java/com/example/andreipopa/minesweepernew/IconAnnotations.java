package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IconAnnotations {

    public static final int EMPTY=0;
    public static final int ONE=1;
    public static final int TWO=2;
    public static final int THREE=3;
    public static final int FOUR=4;
    public static final int FIVE=5;
    public static final int SIX=6;
    public static final int SEVEN=7;
    public static final int EIGHT=8;
    public static final int HIDDEN=20;
    public static final int BOMB=30;
    public static final int RED_BOMB=40;
    public static final int FLAG=100;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EMPTY,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,HIDDEN,BOMB,RED_BOMB,FLAG})
    @interface TileIcon{}

    private int mode;
    public void setMode(@TileIcon int mode){
        this.mode= mode;
    }

    @TileIcon
    public int getMode(){
        return mode;
    }

}
