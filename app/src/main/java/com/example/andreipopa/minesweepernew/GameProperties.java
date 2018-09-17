package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


//The object that contains the new game properties
//A GameGenerator object is needed in order to generate the table according to the GameProperties values
public class GameProperties {

    private int newGameHeight;
    private int newGameWidth;
    private int newGameDifficulty;
    private int newTileSize;
    private int newGameMode;
    private int gameHashCode;
    private int bombsCount;

    private Context newGameContext;

    public GameProperties(Context newGameContext){
       this.newGameContext=newGameContext;
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

    //function the according to the game difficulty and the table sizes, outputs
    //a specific number of bombs, later these parameters could process some bomb cluster features
    public void decideTheBombsCount(){
        //the algorithm for generating the bombs count actually computes a minCount and a maxCount value
        //the specific number of bombs is a random-chosen number in this interval
        float minCount=0.0f;
        float maxCount=0.0f;

        //{...
        // ...
        // ..}  the algorithm for deciding the two boundaries according to the 3 parameters:
        // newGameDifficulty, newGameWidth, newGameHeight

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
        /*Log.d("The random numbers",
                String.valueOf(maxCount)+"//"+String.valueOf(minCount));
        Log.d("The random generated no", String.valueOf(random_integer));*/
       // return random_integer;
    }

}
