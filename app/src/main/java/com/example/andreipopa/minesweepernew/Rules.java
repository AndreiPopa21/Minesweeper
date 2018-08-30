package com.example.andreipopa.minesweepernew;

import java.util.Vector;

//this class analysis the input of player and returns
//a specific output to GameManager according to
//the player's actions
public class Rules {

    //will be using this class in order to return informations to
    //GameManager in a specific format for interpretation
    public class MiniTileInfo{

        public MiniTileInfo(int x,int y,int tileIcon){
            this.xCoord=x;
            this.yCoord=y;
            this.tileIcon=tileIcon;
        }

        public int xCoord;
        public int yCoord;
        public int tileIcon;
    }


    public static boolean isItABomb(GameManager gm,int xCoord, int yCoord){

        int tileIcon= gm.getTheTable().getTileAtPosition(xCoord,yCoord).getTileIcon();
        if(tileIcon==IconAnnotations.BOMB){
            return true;
        }else{
            return false;
        }
    }

    public static boolean didILoseGame(GameManager gm,
                                       int inputType,
                                       int xCoord,
                                       int yCoord){

        boolean isBomb= Rules.isItABomb(gm,xCoord,yCoord);

        if(inputType==InputType.DETONATE && isBomb){
            return true;
        }

        return false;
    }

    public static Vector<MiniTileInfo> uncoverTile(GameManager gm,
                                     int inputType,
                                     int xCoord,
                                     int yCoord){

        Vector<MiniTileInfo> tileInfoVector= new Vector<MiniTileInfo>();

        return tileInfoVector;

    }

}
