package com.example.andreipopa.minesweepernew;

import java.util.Random;

public class GameManager {

    private int newGameMode; //the current game mode of this session
    private int newGameWidth; //corresponding to the y-coord
    private int newGameLength; //corresponding to the x-coord
    private int newGameDifficulty; //the current game difficulty, decisive factor
                                   //when generating the newGame recipe
                                   //could be used for the count of the bombs as well as for
                                   //their positioning
    private int newBombsCount;

    private int[][] newGamePattern;

    //the GameManager class is initialized only once during the entire app lifecycle
    public GameManager(){

    }

    public void setGameMode(int newGameMode){
        this.newGameMode=newGameMode;
    }
    public int getGameMode(){
        return this.newGameMode;
    }
    public void setNewGameWidth(int newGameWidth){
        this.newGameWidth=newGameWidth;
    }
    public int getNewGameWidth(){
        return this.newGameWidth;
    }
    public void setNewGameLength(int newGameLength){
        this.newGameLength=newGameLength;
    }
    public int getNewGameLength(){
        return this.newGameLength;
    }
    public void setNewGameDifficulty(int newGameDifficulty){
        this.newGameDifficulty=newGameDifficulty;
    }
    public int getNewGameDifficulty(){
        return this.newGameDifficulty;
    }
    public int getNewBombsCount(){
        return this.newBombsCount;
    }
    public void setNewBombsCount(int newBombsCount){
        this.newBombsCount=newBombsCount;
    }


    //this is called every time we require a nee game session
    public int[][] generateNewConfiguration(int newGameLength,
                                            int newGameWidth,
                                            int newGameMode,
                                            int newGameDifficulty){

        newGamePattern= new int[newGameLength][newGameWidth];
        setNewGameLength(newGameLength);
        setNewGameWidth(newGameWidth);
        setGameMode(newGameMode);
        setNewGameDifficulty(newGameDifficulty);

        resetNewConfiguration();
        decideTheBombsCount();
        setTheBombs();

        for(int i=0;i<newGameLength;i++){
            for(int j=0;j<newGameWidth;j++){

                if(newGamePattern[i][j]==0){ //if it is a free tile
                    int neighboursCount=countEachTileNeighbours(i,j);
                    newGamePattern[i][j]=neighboursCount;
                }
            }
        }

        return newGamePattern;
    }

    private void resetNewConfiguration(){

       setNewBombsCount(0);
       for(int i=0;i<newGameLength;i++){
           for(int j=0;j<newGameWidth;j++){
               newGamePattern[i][j]=0;
           }
       }
    }

    //function the according to the game difficulty and the table sizes, outputs
    //a specific number of bombs, later these parameters could process some bomb cluster features
    private int decideTheBombsCount(){
        //the algorithm for generating the bombs count actually computes a minCount and a maxCount value
        //the specific number of bombs is a random-chosen number in this interval
        int minCount=0;
        int maxCount=0;

        //{...
        // ...
        // ..}  the algorithm for deciding the two boundaries according to the 3 parameters:
        // newGameDifficulty, newGameWidth, newGameLength

        Random rand= new Random(this.newGameDifficulty-
                                  this.newGameMode*this.newGameWidth
                                  +this.newGameLength);

        int random_integer=rand.nextInt(maxCount-minCount)+minCount;

        return random_integer;
    }

    //position each bomb in a selected position
    private void setTheBombs(){

    }

    //count each tile neighbours according to the current game mode
    // e.g. CLASSICAL counts only the eight-adjacent neighbours
    // e.g. KNIGHTPATHS counts the eight neighbours  at a chess-knight-movement position
    // begin count the neighbour directions clockwise starting from 12 o'clock
    private int countEachTileNeighbours(int tileX,int tileY){
        int[] xDir= new int[]{0,0,0,0,0,0,0,0};
        int[] yDir= new int[]{0,0,0,0,0,0,0,0};

        switch (this.newGameMode){
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

        int neighboursCount=0;
        for(int i=0;i<8;i++){
            int newX= tileX+xDir[i];
            int newY= tileY+yDir[i];

            if(newX>=0 && newX<newGameLength && newY>=0 && newY<newGameWidth){
                if(newGamePattern[newX][newY]== -1){
                    neighboursCount+=1;
                }else{
                    neighboursCount+=0;
                }
            }else{
                neighboursCount+=0;
            }
        }

        return neighboursCount;
    }

}
