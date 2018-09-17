package com.example.andreipopa.minesweepernew;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//The object that contains the new game properties
//A GameGenerator object is needed in order to generate the table according to the GameProperties values
public class GameProperties {

    private int newGameHeight;
    private int newGameWidth;
    private int newGameDifficulty;
    private int newTileSize;
    private int newGameMode;
    private int gameHashCode;

    public GameProperties(){

    }

    public int getNewGameHeight(){return this.newGameHeight;}
    public int getNewGameWidth(){return  this.newGameWidth;}
    public int getNewGameDifficulty(){return this.newGameDifficulty;}
    public int getNewTileSize(){return this.newTileSize;}
    public int getNewGameMode(){return this.newGameMode;}
    public int getGameHashCode(){return this.gameHashCode;}

    public void setNewGameHeight(int newGameHeight){this.newGameHeight=newGameHeight;}
    public void setNewGameWidth(int newGameWidth){this.newGameWidth=newGameWidth;}
    public void setNewGameDifficulty(int newGameDifficulty){this.newGameDifficulty=newGameDifficulty;}
    public void setNewTileSize(int newTileSize){this.newTileSize=newTileSize;}
    public void setNewGameMode(int newGameMode){this.newGameMode=newGameMode;}

    private void generateGameHash(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.gameHashCode=timeStamp.hashCode();
    }


}
