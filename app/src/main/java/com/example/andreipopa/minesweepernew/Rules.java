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

        return number;
    }
    public static int iconAccordingToNumber(int number){
        int icon=-1212121;
        switch(number){
            case 1:
                icon=IconAnnotations.ONE;
                break;
            case 2:
                icon=IconAnnotations.TWO;
                break;
            case 3:
                icon=IconAnnotations.THREE;
                break;
            case 4:
                icon=IconAnnotations.FOUR;
                break;
            case 5:
                icon=IconAnnotations.FIVE;
                break;
            case 6:
                icon=IconAnnotations.SIX;
                break;
            case 7:
                icon=IconAnnotations.SEVEN;
                break;
            case 8:
                icon= IconAnnotations.EIGHT;
                break;
            case 0:
                icon=IconAnnotations.EMPTY;
                break;
            default:
                throw new RuntimeException("WHAT Number did you give me? ");
        }

        return icon;

    }

/*
    private static Vector<MiniTileInfo> uncover_empty_tile(){
        Vector<MiniTileInfo> vect= new Vector<MiniTileInfo>();
        Queue<MiniTileInfo>


        return vect;
    }*/
/*
    private static Vector<MiniTileInfo> uncover_revealed_tile(){
        Vector<MiniTileInfo> vect= new Vector<MiniTileInfo>();


        return vect;
    }

    private void lee_algorithm(int startX,
                                      int startY,
                                      int gameMode,
                                      int uncoverType,
                                      int startIcon){

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




    }*/

}
