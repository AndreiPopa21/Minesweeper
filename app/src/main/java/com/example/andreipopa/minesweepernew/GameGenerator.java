package com.example.andreipopa.minesweepernew;

import android.util.Log;
import android.util.TypedValue;

import java.util.Random;
import java.util.regex.Pattern;

public class GameGenerator {

    private GameProperties gameProperties;
    private GameManager gameManager;
    private int[][] newGamePattern;

    public GameGenerator(GameProperties gp, GameManager gm){

        if(gp==null){ throw new RuntimeException("The GameProperties object passed to the GameGenerator constructor is empty"); }
        if(gm==null){ throw new RuntimeException("The GameManager object passed to the GameGenerator constructor is empty"); }

        this.gameProperties=gp;
        this.gameManager=gm;

        this.generateNewGame();
    }

    private void generateNewGame(){

        newGamePattern=new int[gameProperties.getNewGameHeight()][gameProperties.getNewGameWidth()];
        resetNewConfiguration();

        gameProperties.decideTheBombsCount();
        setTheBombs(gameProperties.getBombsCount());

        for(int i=0;i<gameProperties.getNewGameHeight();i++){
            for(int j=0;j<gameProperties.getNewGameWidth();j++){

                if(newGamePattern[i][j]==0){ //if it is a free tile
                    int neighboursCount=countEachTileNeighbours(i,j);
                    newGamePattern[i][j]=neighboursCount;
                }
            }
        }
        
        outputTablePattern();
    }



    private void resetNewConfiguration(){

        for(int i=0;i<gameProperties.getNewGameHeight();i++){
            for(int j=0;j<gameProperties.getNewGameWidth();j++){
                newGamePattern[i][j]=0;
            }
        }
    }
    //position each bomb in a selected position
    private void setTheBombs(int bombsCount){

        Random rand=new Random();
        while(bombsCount>0){
            int randX=rand.nextInt(gameProperties.getNewGameHeight()-1);
            int randY=rand.nextInt(gameProperties.getNewGameWidth()-1);

            if(randX<0 && randX>=gameProperties.getNewGameHeight() && randY<0 && randY>=gameProperties.getNewGameWidth()){
                continue;
            } else{
                if(newGamePattern[randX][randY]!=0){
                    continue;
                }else{
                    if(newGamePattern[randX][randY]==0){
                        newGamePattern[randX][randY]= -1;
                        bombsCount-=1;
                    }
                }
            }
        }
    }
    //count each tile neighbours according to the current game mode
    // e.g. CLASSICAL counts only the eight-adjacent neighbours
    // e.g. KNIGHTPATHS counts the eight neighbours  at a chess-knight-movement position
    // begin count the neighbour directions clockwise starting from 12 o'clock
    private int countEachTileNeighbours(int tileX,int tileY){
        int[] xDir= new int[]{0,0,0,0,0,0,0,0};
        int[] yDir= new int[]{0,0,0,0,0,0,0,0};

        switch (gameProperties.getNewGameMode()){
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

            if(newX>=0 && newX<gameProperties.getNewGameHeight() && newY>=0 && newY<gameProperties.getNewGameWidth()){
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
    private void outputTablePattern(){

        Log.d("Output pattern",
                String.valueOf(gameProperties.getNewGameHeight())+
                        "//"+String.valueOf(gameProperties.getNewGameWidth()));

        String s="\n";
        for(int i=0;i<gameProperties.getNewGameHeight();i++){

            for(int j=0;j<gameProperties.getNewGameWidth();j++){
                s=s+ Integer.toString(newGamePattern[i][j])+'\t';
            }
            s=s+'\n';

        }
        Log.d("Output pattern",s);
    }
}
