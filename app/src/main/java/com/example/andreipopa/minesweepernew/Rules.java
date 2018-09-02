package com.example.andreipopa.minesweepernew;

import android.graphics.drawable.Icon;
import android.util.Log;

import java.util.LinkedList;
import java.util.Vector;
import java.util.Queue;

//this class analysis the input of player and returns
//a specific output to GameManager according to
//the player's actions
public class Rules {

    private Queue<MiniTileInfo> queue_for_lee;

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

        int tileValue= gm.getTheTable().getTileAtPosition(xCoord,yCoord).getTileValue();
        if(tileValue==ValueType.BOMB){
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
                                     int uncoverSituation,
                                     int xCoord,
                                     int yCoord, int startIcon){

        Vector<MiniTileInfo> tileInfoVector= new Vector<MiniTileInfo>();

        switch(uncoverSituation){
            case UncoverSituation.ON_EMPTY_TILE:
               // tileInfoVector= uncover_empty_tile();
                break;
            case UncoverSituation.ON_REVEALED_TILE:
             //   tileInfoVector= uncover_revealed_tile();
                break;
            default:
                throw new RuntimeException("The uncover situation is not defined or obsolete");
        }


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

        int necessaryFlags= valueAccordingToIcon(startIcon);

        int foundFlags=0;

        for(int i=0;i<8;i++){
            int nextX= startX+xDir[i];
            int nextY= startY+yDir[i];

            if(gm.getTheTable().getTileAtPosition(nextX,nextY).getTileIcon()
                    ==IconType.FLAG){
                foundFlags+=1;
            }
        }
        /*Log.d("Debug flag",
                "Icon is: "+String.valueOf(startIcon)+"//"+
                        "necessary flags are: "+String.valueOf(necessaryFlags)+
        "Count of found flags is: "+String.valueOf(foundFlags));
*/
        if(necessaryFlags<=foundFlags){
            return true;
        }

        return false;
    }

    public static int valueAccordingToIcon(int icon){

        int value= -1;

        switch (icon){
            case IconType.ONE:
                value=ValueType.ONE;
                break;
            case IconType.TWO:
                value=ValueType.TWO;
                break;
            case IconType.THREE:
                value=ValueType.THREE;
                break;
            case IconType.FOUR:
                value=ValueType.FOUR;
                break;
            case IconType.FIVE:
                value=ValueType.FIVE;
                break;
            case IconType.SIX:
                value=ValueType.SIX;
                break;
            case IconType.SEVEN:
                value=ValueType.SEVEN;
                break;
            case IconType.EIGHT:
                value=ValueType.EIGHT;
                break;
            default:
                throw new RuntimeException("Why did you not send a number tile");
        }
        return value;
    }
    public static int iconAccordingToValue(int value){
        int icon=-1212121;
        switch(value){
            case ValueType.ONE:
                icon=IconType.ONE;
                break;
            case ValueType.TWO:
                icon=IconType.TWO;
                break;
            case ValueType.THREE:
                icon=IconType.THREE;
                break;
            case ValueType.FOUR:
                icon=IconType.FOUR;
                break;
            case ValueType.FIVE:
                icon=IconType.FIVE;
                break;
            case ValueType.SIX:
                icon=IconType.SIX;
                break;
            case ValueType.SEVEN:
                icon=IconType.SEVEN;
                break;
            case ValueType.EIGHT:
                icon= IconType.EIGHT;
                break;
            case ValueType.EMPTY:
                icon=IconType.EMPTY;
                break;
            default:
                throw new RuntimeException("WHAT Number did you give me? ");
        }

        return icon;

    }



}
