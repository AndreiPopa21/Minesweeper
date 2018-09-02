package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ValueType {

    public static final int UNDEFINED_VALUETYPE= -66666;
    public static final int ONE= 1;
    public static final int TWO= 2;
    public static final int THREE =3;
    public static final int FOUR= 4;
    public static final int FIVE= 5;
    public static final int SIX=6;
    public static final int SEVEN= 7;
    public static final int EIGHT= 8;
    public static final int EMPTY= 0;
    public static final int BOMB= -1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNDEFINED_VALUETYPE,ONE, TWO, THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,EMPTY,BOMB})
    @interface ValueInterf{}

    private int mode;
    public void setMode(@ValueInterf int mode){
        this.mode= mode;
    }

    @ValueInterf
    public int getMode(){
        return mode;

}}
