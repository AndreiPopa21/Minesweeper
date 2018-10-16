package com.example.andreipopa.minesweepernew;

import android.content.Context;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class MinesweeperRules {

    private Queue<MinesweeperRules.MiniTileInfo> queue_for_lee;

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

        int tileValue= gm.getMinesweeperTable().getTileAtPosition(xCoord,yCoord).getTileValue();
        if(tileValue==ValueType.BOMB){
            return true;
        }else{
            return false;
        }
    }

    /*public static boolean didILoseGame(MinesweeperGameManager gm,
                                       int inputType,
                                       int xCoord,
                                       int yCoord){

        boolean isBomb= MinesweeperRules.isItABomb(gm,xCoord,yCoord);

        if(inputType==InputType.DETONATE && isBomb){
            return true;
        }

        return false;
    }*/

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

        xDir=Utils.xDirAccordingToGameMode(gameMode);
        yDir=Utils.yDirAccordingToGameMode(gameMode);


        Vector<MinesweeperAdapter.MinesweeperViewHolder> targets=new Vector<MinesweeperAdapter.MinesweeperViewHolder>();
        Queue<MinesweeperAdapter.MinesweeperViewHolder> lee_queue= new LinkedList<MinesweeperAdapter.MinesweeperViewHolder>();


        MinesweeperAdapter.MinesweeperViewHolder firstTarget=
                gm.getMinesweeperTable().getTileAtPosition(startX,startY).getHolderOfThisClass();
        firstTarget.thisTileClass.setWhetherIsRevealed(true);
        targets.add(firstTarget);
        lee_queue.add(firstTarget);

        if(uncovered_situation==UncoverSituation.FLAG_ON_REVEALED_TILE){

            lee_queue.poll();
            for(int i=0;i<8;i++){
                int nextX= startX+xDir[i];
                int nextY= startY+yDir[i];
                if(!(nextX<0 || nextX>=gm.getMinesweeperGameProperties().getNewGameHeight()
                        || nextY<0 || nextY>=gm.getMinesweeperGameProperties().getNewGameWidth())){
                    Tile targetTile= gm.getMinesweeperTable().getTileAtPosition(nextX,nextY);
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
                        if(!(nextX<0 || nextX>=gm.getMinesweeperGameProperties().getNewGameHeight()
                                || nextY<0 || nextY>=gm.getMinesweeperGameProperties().getNewGameWidth())){
                            Tile targetTile= gm.getMinesweeperTable().getTileAtPosition(nextX,nextY);
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

    public boolean doesItHaveEnoughFlagsSet(MinesweeperGameManager gm,
                                                   int startX,
                                                   int startY,
                                                   int startValue,
                                                   int gameMode){

        int[] xDir= new int[]{0,0,0,0,0,0,0,0};
        int[] yDir= new int[]{0,0,0,0,0,0,0,0};

        xDir=Utils.xDirAccordingToGameMode(gameMode);
        yDir=Utils.yDirAccordingToGameMode(gameMode);

        int necessaryFlags= startValue;

        int foundFlags=0;

        for(int i=0;i<8;i++){
            int nextX= startX+xDir[i];
            int nextY= startY+yDir[i];

            if(!(nextX<0 || nextX>=gm.getMinesweeperGameProperties().getNewGameHeight()
                    || nextY<0 || nextY>=gm.getMinesweeperGameProperties().getNewGameWidth())){
                if(gm.getMinesweeperTable().getTileAtPosition(nextX,nextY).getTileIcon()
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

    public static boolean checkWhetherAllBombsFlagged(int startX,
                                                      int startY,
                                                      MinesweeperGameManager gm,
                                                      int gameMode){


        int[] xDir= new int[]{0,0,0,0,0,0,0,0};
        int[] yDir= new int[]{0,0,0,0,0,0,0,0};
        xDir=Utils.xDirAccordingToGameMode(gameMode);
        yDir=Utils.yDirAccordingToGameMode(gameMode);

        boolean foundUnflaggedBomb=false;
        for(int i=0;i<8;i++){
            int nextX=startX+xDir[i];
            int nextY=startY+yDir[i];
            if(!(nextX<0 || nextX>=gm.getMinesweeperGameProperties().getNewGameHeight()
                    || nextY<0 || nextY>=gm.getMinesweeperGameProperties().getNewGameWidth())) {
                Tile targetTile = gm.getMinesweeperTable().
                        getTileAtPosition(nextX, nextY);

                if (!targetTile.getWhetherIsRevelead()) {
                    if (targetTile.getTileIcon() == IconType.HIDDEN) {
                        if (targetTile.getWhetherIsFlagged() == false &&
                                targetTile.getTileValue() == ValueType.BOMB) {
                            foundUnflaggedBomb=true;
                        }
                    }
                }
            }
        }

        return foundUnflaggedBomb;
    }





    private MinesweeperGameManager minesweeperGameManager;

    public MinesweeperRules(MinesweeperGameManager minesweeperGameManager){
        if(minesweeperGameManager==null){ throw new RuntimeException("Invalid MinesweeperGameManager passed to function"); }
        this.minesweeperGameManager=minesweeperGameManager;
        this.minesweeperGameManager.attachMinesweeperRulesObject(this);
    }

    //check whether the tile has the values: 1 2 3 4 5 6 7 8
    public boolean checkWhetherTileHasSingleValue(int startX, int startY){

        if(this.minesweeperGameManager.getNewGamePattern()==null){ throw new RuntimeException("Invalid newGamePattern in MinesweeperGameManager"); }
        int boardWidth= this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int boardHeight=this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight();

        if(startX < 0 || startY<0 || startX>=boardHeight || startY>=boardWidth){
            throw new RuntimeException("Invalid coordinates passed to function: x = "
                    +String.valueOf(startX)+"  / y = "+String.valueOf(startY));
        }

        int value_in_pattern=
               this.minesweeperGameManager.getNewGamePattern()[startX][startY];

        int valueType= Utils.patternValueToValue(value_in_pattern);
        if(valueType==ValueType.EMPTY || valueType==ValueType.BOMB || valueType==ValueType.UNDEFINED_VALUETYPE){
            return false;
        }
        return true;
    }

    public boolean checkWhetherTileIsBomb(int startX, int startY){

        if(this.minesweeperGameManager.getNewGamePattern()==null){ throw new RuntimeException("Invalid newGamePattern in MinesweeperGameManager"); }
        int boardWidth= this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int boardHeight=this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight();

        if(startX < 0 || startY<0 || startX>=boardHeight || startY>=boardWidth){
            throw new RuntimeException("Invalid coordinates passed to function: x = "
                    +String.valueOf(startX)+"  / y = "+String.valueOf(startY));
        }

        int value_in_pattern= this.minesweeperGameManager.getNewGamePattern()[startX][startY];
        int valueType= Utils.patternValueToValue(value_in_pattern);
        if(valueType==ValueType.BOMB){
            return true;
        }
        return false;
    }

    public boolean checkWhetherTileIsEmpty(int startX,int startY){

        if(this.minesweeperGameManager.getNewGamePattern()==null){ throw new RuntimeException("Invalid newGamePattern in MinesweeperGameManager"); }
        int boardWidth= this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int boardHeight=this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight();

        if(startX < 0 || startY<0 || startX>=boardHeight || startY>=boardWidth){
            throw new RuntimeException("Invalid coordinates passed to function: x = "
                    +String.valueOf(startX)+"  / y = "+String.valueOf(startY));
        }

        int value_in_pattern= this.minesweeperGameManager.getNewGamePattern()[startX][startY];
        int valueType=Utils.patternValueToValue(value_in_pattern);
        if(valueType==ValueType.EMPTY){
            return true;
        }
        return false;
    }

    public boolean checkWhetherPositionInBounds(int startX, int startY){

        if(this.minesweeperGameManager.getNewGamePattern()==null){ throw new RuntimeException("Invalid newGamePattern in MinesweeperGameManager"); }
        int boardWidth= this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int boardHeight=this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight();

        if(startX<0 || startY<0 || startX>=boardHeight ||startY>=boardWidth){
            return false;
        }
        return true;
    }

    public void detonateOnEmptyTile(int startX, int startY, int gameMode){

        if(this.minesweeperGameManager.getNewGamePattern()==null){ throw new RuntimeException("Invalid newGamePattern in MinesweeperGameManager"); }
        int boardWidth= this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int boardHeight=this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight();

        if(startX < 0 || startY<0 || startX>=boardHeight || startY>=boardWidth){
            throw new RuntimeException("Invalid coordinates passed to function: x = "
                    +String.valueOf(startX)+"  / y = "+String.valueOf(startY));
        }

        int[] xDir=Utils.xDirAccordingToGameMode(gameMode);
        int[] yDir=Utils.yDirAccordingToGameMode(gameMode);

        Vector<MiniTileInfo> tilesToUncover= new Vector<MiniTileInfo>();
        Queue<MiniTileInfo> lee_queue= new LinkedList<MiniTileInfo>();


        tilesToUncover.add(new MiniTileInfo(startX,startY));
        lee_queue.add(new MiniTileInfo(startX,startY));
        minesweeperGameManager.getMinesweeperTable()
                .getTileAtPosition(startX,startY).setWhetherIsRevealed(true);

        while(lee_queue.peek()!=null){

            MiniTileInfo miniTileInfo= lee_queue.poll();
            if(miniTileInfo!=null){
                Tile startTile= minesweeperGameManager.getMinesweeperTable()
                        .getTileAtPosition(miniTileInfo.xCoord,miniTileInfo.yCoord);
                if(startTile.getTileValue()==ValueType.EMPTY){
                    startTile.setWhetherIsRevealed(true);
                    for(int i=0;i<8;i++){
                        int nextX= startTile.getxCoord()+xDir[i];
                        int nextY= startTile.getyCoord()+yDir[i];

                        if(!(nextX<0 || nextX>=boardHeight || nextY < 0 || nextY >=boardWidth)){
                            Tile nextTile= minesweeperGameManager.getMinesweeperTable()
                                    .getTileAtPosition(nextX,nextY);

                            boolean toAddToList= true;

                            if(nextTile.getTileValue()==ValueType.BOMB){
                                toAddToList=false;
                            }

                            if(nextTile.getWhetherIsRevelead()==true){
                                toAddToList=false;
                            }

                            if(nextTile.getWhetherIsFlagged()==true){
                                toAddToList=false;
                            }

                            if(toAddToList){
                                nextTile.setWhetherIsRevealed(true);
                                nextTile.setWhetherIsFlagged(false);
                                lee_queue.add(new MiniTileInfo(nextX,nextY));
                                tilesToUncover.add(new MiniTileInfo(nextX,nextY));
                            }
                        }
                    }
                }
            }
        }

        for(int i=0;i<tilesToUncover.size();i++){
            MinesweeperRules.MiniTileInfo nextTileInfo= tilesToUncover.elementAt(i);
            int nextX= nextTileInfo.xCoord;
            int nextY= nextTileInfo.yCoord;
            Tile nextTile= minesweeperGameManager.getMinesweeperTable().getTileAtPosition(nextX,nextY);
            nextTile.setWhetherIsRevealed(true);
            nextTile.setWhetherIsFlagged(false);
            nextTile.unhideTile();
        }


    }

    public boolean checkWhetherTheMiniTileIsRevealed(MinesweeperGameManager minesweeperGameManager,
                                                            MiniTileInfo miniTileInfo){
        int x= miniTileInfo.xCoord;
        int y= miniTileInfo.yCoord;

        Tile tile= minesweeperGameManager.getMinesweeperTable().getTileAtPosition(x,y);
        if(tile.getWhetherIsRevelead()){
            return true;
        }

        return false;
    }

    public boolean pressOnRevealed(int startX, int startY, int gameMode){

        if(this.minesweeperGameManager.getNewGamePattern()==null){ throw new RuntimeException("Invalid newGamePattern in MinesweeperGameManager"); }
        int boardWidth= this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int boardHeight=this.minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight();

        if(startX < 0 || startY<0 || startX>=boardHeight || startY>=boardWidth){
            throw new RuntimeException("Invalid coordinates passed to function: x = "
                    +String.valueOf(startX)+"  / y = "+String.valueOf(startY));
        }

        int[] xDir=Utils.xDirAccordingToGameMode(gameMode);
        int[] yDir=Utils.yDirAccordingToGameMode(gameMode);





        Vector<MiniTileInfo> tilesToUncover= new Vector<MiniTileInfo>();
        Queue<MiniTileInfo> lee_queue= new LinkedList<MiniTileInfo>();

        tilesToUncover.add(new MiniTileInfo(startX,startY));
        lee_queue.add(new MiniTileInfo(startX,startY));
        minesweeperGameManager.getMinesweeperTable()
                .getTileAtPosition(startX,startY).setWhetherIsRevealed(true);

        for(int i=0;i<8;i++){
            int nextX= startX+xDir[i];
            int nextY=startY+yDir[i];
            if(!(nextX<0 || nextX>=boardHeight || nextY<0 || nextY>=boardWidth)){
                Tile nextTile= minesweeperGameManager.getMinesweeperTable()
                        .getTileAtPosition(nextX,nextY);

                boolean toAdd=true;

                if(nextTile.getWhetherIsFlagged()){
                    toAdd=false;
                }

                

            }
        }




        return true; //the detonateOnRevealed did not lose us the game

    }

    public boolean checkWhetherUnflaggedNeighbourBombs(int startX, int startY,int gameMode){

        int[] xDir= Utils.xDirAccordingToGameMode(gameMode);
        int[] yDir= Utils.yDirAccordingToGameMode(gameMode);

        boolean bombWithoutFlag = false;

        for(int i=0; i<8 ;i++){
            int nextX= startX + xDir[i];
            int nextY= startY + yDir[i];

            if(!(nextX<0 || nextX>=minesweeperGameManager.getMinesweeperGameProperties().getNewGameHeight() ||
                  nextY<0 || nextY>= minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth())){

                int value_in_pattern= minesweeperGameManager.getNewGamePattern()[nextX][nextY];

                if(value_in_pattern == -1){
                    Tile nextTile = minesweeperGameManager.getMinesweeperTable()
                            .getTileAtPosition(nextX,nextY);
                    if(!nextTile.getWhetherIsFlagged()){
                        bombWithoutFlag=true;
                    }
                }

            }
        }

        return bombWithoutFlag;

    }
}
