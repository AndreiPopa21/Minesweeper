package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class MinesweeperGameProperties {
    private int newGameHeight;
    private int newGameWidth;
    private int newGameDifficulty;
    private int newTileSize;
    private int newGameMode;
    private int gameHashCode;
    private int bombsCount;

    private Context newGameContext;

    public MinesweeperGameProperties(Context newGameContext){
        this.newGameContext=newGameContext;

        this.setNewGameHeight(-1);
        this.setNewGameWidth(-1);
        this.setNewGameMode(GameMode.UNDEFINED_GAMEMODE);
        this.setNewGameDifficulty(DifficultyType.UNDEFINED_DIFFICULTY);
        this.setNewTileSize(-1);

        this.generateGameHash();
    }

    public int getNewGameHeight(){return this.newGameHeight;}
    public int getNewGameWidth(){return  this.newGameWidth;}
    public int getNewGameDifficulty(){return this.newGameDifficulty;}
    public int getNewTileSize(){return this.newTileSize;}
    public int getNewGameMode(){return this.newGameMode;}
    public int getGameHashCode(){return this.gameHashCode;}
    public int getBombsCount(){return this.bombsCount;}
    public Context getNewGameContext(){return this.newGameContext;}

    public void setNewGameHeight(int newGameHeight){this.newGameHeight=newGameHeight;}
    public void setNewGameWidth(int newGameWidth){this.newGameWidth=newGameWidth;}
    public void setNewGameDifficulty(int newGameDifficulty){this.newGameDifficulty=newGameDifficulty;}
    public void setNewTileSize(int newTileSize){this.newTileSize=newTileSize;}
    public void setNewGameMode(int newGameMode){this.newGameMode=newGameMode;}

    private void generateGameHash(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.gameHashCode=timeStamp.hashCode();
    }

    public void decideTheBombsCount(){

        float minCount=0.0f;
        float maxCount=0.0f;

        checkAllParametersValid();

        TypedValue tempVal = new TypedValue();

        switch(getNewGameDifficulty()){
            case DifficultyType.EASY:
                getNewGameContext().getResources().getValue(R.dimen.experimental_min_easy_bombs_to_tiles_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                getNewGameContext().getResources().getValue(R.dimen.experimental_max_easy_bombs_to_tiles_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            case DifficultyType.MEDIUM:
                getNewGameContext().getResources().getValue(R.dimen.experimental_min_medium_bombs_to_tile_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                getNewGameContext().getResources().getValue(R.dimen.experimental_max_medium_bombs_to_tile_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            case DifficultyType.HARD:
                getNewGameContext().getResources().getValue(R.dimen.experimental_min_hard_bombs_to_tile_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                getNewGameContext().getResources().getValue(R.dimen.experimental_max_hard_bombs_to_tile_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            default:
                throw new RuntimeException("There is something wrong with the difficulty");
        }

        minCount=minCount* getNewGameWidth()*getNewGameHeight();
        maxCount=maxCount* getNewGameWidth()*getNewGameHeight();

        Random rand= new Random();
        int random_integer=rand.nextInt(Math.round(maxCount)-Math.round(minCount))+Math.round(minCount);

        this.bombsCount=random_integer;
    }

    private void checkAllParametersValid(){

        if(this.getNewGameHeight()<=0){
            throw new RuntimeException("The newGameHeight in GameProperties is invalid");
        }
        if(this.getNewGameWidth()<=0){
            throw new RuntimeException("The newGameWidth in GameProperties is invalid");
        }
        if(this.getNewGameDifficulty()==DifficultyType.UNDEFINED_DIFFICULTY){
            throw new RuntimeException("The newGameDifficulty in GameProperties is invalid");
        }
        if(this.getNewGameMode()==GameMode.UNDEFINED_GAMEMODE){
            throw new RuntimeException("The newGameMode in GameProperties is invalid");
        }
        if(this.getNewTileSize()<=0){
            throw new RuntimeException("The newTileSize in GameProperties is invalid");
        }
    }

}
