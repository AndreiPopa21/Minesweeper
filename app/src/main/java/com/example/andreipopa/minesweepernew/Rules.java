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

    //function for uncovering the tiles
    public static Vector<MiniTileInfo> uncoverSpace(GameManager gm,
                                     int inputType,
                                     int gameMode,
                                     int xCoord,
                                     int yCoord, int startIcon){

        Vector<MiniTileInfo> tileInfoVector= new Vector<MiniTileInfo>();




        return tileInfoVector;

    }

    public static boolean doesItHaveEnoughFlagsSet(GameManager gm,
                                                   int startX,
                                                   int startY,
                                                   int startIcon,
                                                   int gameMode){

        int[] xDir= new int[]{0,0,0,0,0,0,0,0};
        int[] yDir= new int[]{0,0,0,0,0,0,0,0};

        switch (gameMode){
            case GameMode.CLASSICAL:
                xDir= new int[]{-1,-1,0,1,1,1,0,-1};
                yDir= new int[]{0,1,1,1,0,-1,-1,-1};
                break;
            case GameMode.KNIGHTPATHS:
                xDir= new int[]{-2,-1,1,2,2,1,-1,-2};
                yDir= new int[]{1,2,2,1,-1,-2,-2,-1};
                break;
            default:
                throw new RuntimeException("This game mode is either obsolete or does not exist");
        }

        int necessaryFlags= numberAccordingToIcon(startIcon);
        int foundFlags=0;

        for(int i=0;i<8;i++){
            int nextX= startX+xDir[i];
            int nextY= startY+yDir[i];

            if(gm.getTheTable().getTileAtPosition(nextX,nextY).getTileIcon()
                    ==IconAnnotations.FLAG){
                foundFlags+=1;
            }
        }

        if(necessaryFlags==foundFlags){
            return true;
        }

        return false;
    }

    public static int numberAccordingToIcon(int icon){

        int number= -1;

        switch (icon){
            case IconAnnotations.ONE:
                number=1;
                break;
            case IconAnnotations.TWO:
                number=2;
                break;
            case IconAnnotations.THREE:
                number=3;
                break;
            case IconAnnotations.FOUR:
                number=4;
                break;
            case IconAnnotations.FIVE:
                number=5;
                break;
            case IconAnnotations.SIX:
                number=6;
                break;
            case IconAnnotations.SEVEN:
                number=7;
                break;
            case IconAnnotations.EIGHT:
                number=8;
                break;
            default:
                throw new RuntimeException("Why did you not send a number tile");

        }

        return -1;
    }

}
