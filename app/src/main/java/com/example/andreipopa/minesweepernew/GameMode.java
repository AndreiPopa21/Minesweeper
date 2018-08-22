package com.example.andreipopa.minesweepernew;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class GameMode {

    public static final int CLASSICAL=111;
    public static final int KNIGHTPATHS=222;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CLASSICAL,KNIGHTPATHS})
    @interface GameModeInterf{}

    private int mode;
    public void setMode(@GameModeInterf int mode){
        this.mode= mode;
    }

    @GameModeInterf
    public int getMode(){
        return mode;
    }


}
