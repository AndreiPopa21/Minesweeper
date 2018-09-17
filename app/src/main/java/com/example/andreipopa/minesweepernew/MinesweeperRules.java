package com.example.andreipopa.minesweepernew;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class MinesweeperRules {

    private Queue<Rules.MiniTileInfo> queue_for_lee;

    //will be using this class in order to return informations to
    //GameManager in a specific format for interpretation
    public class MiniTileInfo{

        public MiniTileInfo(int x,int y){
            this.xCoord=x;
            this.yCoord=y;
        }

        public int xCoord;
        public int yCoord;
    }


    public static boolean isItABomb(MinesweeperGameManager gm,int xCoord, int yCoord){

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
   /* public static Vector<MinesweeperAdapter.MinesweeperViewHolder> uncoverSpace(GameManager gm,
                                                                                int inputType,
                                                                                int gameMode,
                                                                                int uncoverSituation,
                                                                                int xCoord,
                                                                                int yCoord, int startIcon){

        Vector<MinesweeperAdapter.MinesweeperViewHolder> tileInfoVector= new Vector<MinesweeperAdapter.MinesweeperViewHolder>();

        switch(uncoverSituation){
            case UncoverSituation.ON_EMPTY_TILE:
               tileInfoVector= uncover_empty_tile(xCoord,yCoord,gameMode,gm);
                break;
            case UncoverSituation.ON_REVEALED_TILE:
             //   tileInfoVector= uncover_revealed_tile();
                break;
            default:
                throw new RuntimeException("The uncover situation is not defined or obsolete");
        }
        return tileInfoVector;
    }*/

    public static Vector<MinesweeperAdapter.MinesweeperViewHolder> uncover_empty_tile(int startX,
                                                                                      int startY,
                                                                                      int gameMode,
                                                                                      int uncovered_situation,
                                                                                      MinesweeperGameManager gm){

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

        Vector<MinesweeperAdapter.MinesweeperViewHolder> targets=new Vector<MinesweeperAdapter.MinesweeperViewHolder>();
        Queue<MinesweeperAdapter.MinesweeperViewHolder> lee_queue= new LinkedList<MinesweeperAdapter.MinesweeperViewHolder>();


        MinesweeperAdapter.MinesweeperViewHolder firstTarget=
                gm.getTheTable().getTileAtPosition(startX,startY).getHolderOfThisClass();
        firstTarget.thisTileClass.setWhetherIsRevealed(true);
        targets.add(firstTarget);
        lee_queue.add(firstTarget);

        if(uncovered_situation==UncoverSituation.ON_REVEALED_TILE){

            lee_queue.poll();
            for(int i=0;i<8;i++){
                int nextX= startX+xDir[i];
                int nextY= startY+yDir[i];
                if(!(nextX<0 || nextX>=gm.getNewGameHeight()
                        || nextY<0 || nextY>=gm.getNewGameWidth())){
                    Tile targetTile= gm.getTheTable().getTileAtPosition(nextX,nextY);
                    if(targetTile.getWhetherIsRevelead()==false){
                        if(targetTile.getTileValue()!=ValueType.BOMB){
                            if(!(targetTile.getTileIcon()==IconType.FLAG ||
                                    targetTile.getTileIcon()==IconType.RED_BOMB||
                                    targetTile.getTileIcon()==IconType.WRONG_FLAG)){

                                targetTile.setWhetherIsRevealed(true);
                                MinesweeperAdapter.MinesweeperViewHolder holder
                                        = targetTile.getHolderOfThisClass();
                                lee_queue.add(holder);
                                targets.add(holder);
                            }
                        }
                    }
                }
            }
        }

        while(lee_queue.peek()!=null){

            MinesweeperAdapter.MinesweeperViewHolder target= lee_queue.poll();
            if(target!=null){

                if(target.thisTileClass.getTileValue()==ValueType.EMPTY){
                    int beginX= target.thisTileClass.getxCoord();
                    int beginY= target.thisTileClass.getyCoord();

                    for(int i=0;i<8;i++){
                        int nextX= beginX+xDir[i];
                        int nextY= beginY+yDir[i];
                        if(!(nextX<0 || nextX>=gm.getNewGameHeight()
                                || nextY<0 || nextY>=gm.getNewGameWidth())){
                            Tile targetTile= gm.getTheTable().getTileAtPosition(nextX,nextY);
                            if(targetTile.getWhetherIsRevelead()==false){
                                if(targetTile.getTileValue()!=ValueType.BOMB){
                                    if(!(targetTile.getTileIcon()==IconType.FLAG ||
                                            targetTile.getTileIcon()==IconType.RED_BOMB||
                                            targetTile.getTileIcon()==IconType.WRONG_FLAG)){

                                        targetTile.setWhetherIsRevealed(true);
                                        MinesweeperAdapter.MinesweeperViewHolder holder
                                                = targetTile.getHolderOfThisClass();
                                        lee_queue.add(holder);
                                        targets.add(holder);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return targets;
    }

    public static boolean doesItHaveEnoughFlagsSet(MinesweeperGameManager gm,
                                                   int startX,
                                                   int startY,
                                                   int startValue,
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

        int necessaryFlags= startValue;

        int foundFlags=0;

        for(int i=0;i<8;i++){
            int nextX= startX+xDir[i];
            int nextY= startY+yDir[i];

            if(!(nextX<0 || nextX>=gm.getNewGameHeight()
                    || nextY<0 || nextY>=gm.getNewGameWidth())){
                if(gm.getTheTable().getTileAtPosition(nextX,nextY).getTileIcon()
                        ==IconType.FLAG){
                    foundFlags+=1;
                }
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
                throw new RuntimeException("Why did you not send a number tile: "+String.valueOf(icon));
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

    public static boolean checkWhetherAllBombsFlagged(int startX,
                                                      int startY,
                                                      MinesweeperGameManager gm,
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

        boolean foundUnflaggedBomb=false;
        for(int i=0;i<8;i++){
            int nextX=startX+xDir[i];
            int nextY=startY+yDir[i];
            if(!(nextX<0 || nextX>=gm.getNewGameHeight()
                    || nextY<0 || nextY>=gm.getNewGameWidth())) {
                Tile targetTile = gm.getTheTable().
                        getTileAtPosition(nextX, nextY);

                if (!targetTile.getWhetherIsRevelead()) {
                    if (targetTile.getTileIcon() == IconType.HIDDEN) {
                        if (targetTile.getIsFlagged() == false &&
                                targetTile.getTileValue() == ValueType.BOMB) {
                            foundUnflaggedBomb=true;
                        }
                    }
                }
            }
        }

        return foundUnflaggedBomb;
    }


}
