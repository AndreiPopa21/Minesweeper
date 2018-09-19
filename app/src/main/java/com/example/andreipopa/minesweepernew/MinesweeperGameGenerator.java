package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.util.Log;

import java.util.Random;

public class MinesweeperGameGenerator {

    private MinesweeperGameProperties minesweeperGameProperties;
    private MinesweeperGameManager minesweeperGameManager;
    private Context applicationContext;
    private int[][] newGamePattern;

    public MinesweeperGameGenerator( MinesweeperGameManager gm){

        if(gm==null){ throw new RuntimeException("The GameManager object passed to the GameGenerator constructor is empty"); }
        this.minesweeperGameManager=gm;
        this.applicationContext=gm.getApplicationContext();
        this.minesweeperGameManager.attachMinesweeperGameGeneratorObject(this);
    }

    public void generateNewGame(MinesweeperGameProperties gp){

        if(gp==null){ throw new RuntimeException("The GameProperties object passed to the GameGenerator constructor is empty"); }
        this.minesweeperGameProperties=gp;

        newGamePattern=new int[minesweeperGameProperties.getNewGameHeight()][minesweeperGameProperties.getNewGameWidth()];
        resetNewConfiguration();

        minesweeperGameProperties.decideTheBombsCount();
        setTheBombs(minesweeperGameProperties.getBombsCount());

        for(int i=0;i<minesweeperGameProperties.getNewGameHeight();i++){
            for(int j=0;j<minesweeperGameProperties.getNewGameWidth();j++){

                if(newGamePattern[i][j]==0){ //if it is a free tile
                    int neighboursCount=countEachTileNeighbours(i,j);
                    newGamePattern[i][j]=neighboursCount;
                }
            }
        }

        //outputTablePattern();
        confirmNewGameToGameManager();
    }

    private void resetNewConfiguration(){

        for(int i=0;i<minesweeperGameProperties.getNewGameHeight();i++){
            for(int j=0;j<minesweeperGameProperties.getNewGameWidth();j++){
                newGamePattern[i][j]=0;
            }
        }
    }
    //position each bomb in a selected position
    private void setTheBombs(int bombsCount){

        Random rand=new Random();
        while(bombsCount>0){
            int randX=rand.nextInt(minesweeperGameProperties.getNewGameHeight()-1);
            int randY=rand.nextInt(minesweeperGameProperties.getNewGameWidth()-1);

            if(randX<0 && randX>=minesweeperGameProperties.getNewGameHeight() && randY<0 && randY>=minesweeperGameProperties.getNewGameWidth()){
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

        xDir=Utils.xDirAccordingToGameMode(minesweeperGameProperties.getNewGameMode());
        yDir=Utils.yDirAccordingToGameMode(minesweeperGameProperties.getNewGameMode());

        int neighboursCount=0;
        for(int i=0;i<8;i++){
            int newX= tileX+xDir[i];
            int newY= tileY+yDir[i];

            if(newX>=0 && newX<minesweeperGameProperties.getNewGameHeight() && newY>=0 && newY<minesweeperGameProperties.getNewGameWidth()){
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
                String.valueOf(minesweeperGameProperties.getNewGameHeight())+
                        "//"+String.valueOf(minesweeperGameProperties.getNewGameWidth()));

        String s="\n";
        for(int i=0;i<minesweeperGameProperties.getNewGameHeight();i++){

            for(int j=0;j<minesweeperGameProperties.getNewGameWidth();j++){
                s=s+ Integer.toString(newGamePattern[i][j])+'\t';
            }
            s=s+'\n';

        }
        Log.d("Output pattern",s);
    }
    private void confirmNewGameToGameManager(){
         minesweeperGameManager.confirmNewGame(newGamePattern);
    }
}
