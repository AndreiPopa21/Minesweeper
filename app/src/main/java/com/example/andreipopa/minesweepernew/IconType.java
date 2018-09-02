package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IconType {

    public static final int UNDEFINED_ICON= -111111;
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
    public static final int FLAG=50;
    public static final int WRONG_FLAG=60;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNDEFINED_ICON,
              EMPTY,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,HIDDEN,BOMB,RED_BOMB,FLAG,WRONG_FLAG})
    @interface IconInterf{}

    private int mode;
    public void setMode(@IconInterf int mode){
        this.mode= mode;
    }

    @IconInterf
    public int getMode(){
        return mode; }

}
