package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DifficultyType {

    public static final int EASY=1111;
    public static final int MEDIUM=2222;
    public static final int HARD=3333;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EASY,MEDIUM,HARD})
    @interface DifficultyInterf{}

    private int mode;
    public void setMode(@DifficultyInterf int mode){
        this.mode= mode;
    }

    @DifficultyInterf
    public int getMode(){
        return mode;
    }
}
