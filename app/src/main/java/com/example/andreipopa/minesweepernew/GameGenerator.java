package com.example.andreipopa.minesweepernew;

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
    }


    //function the according to the game difficulty and the table sizes, outputs
    //a specific number of bombs, later these parameters could process some bomb cluster features
    private int decideTheBombsCount(){
        //the algorithm for generating the bombs count actually computes a minCount and a maxCount value
        //the specific number of bombs is a random-chosen number in this interval
        float minCount=0.0f;
        float maxCount=0.0f;

        //{...
        // ...
        // ..}  the algorithm for deciding the two boundaries according to the 3 parameters:
        // newGameDifficulty, newGameWidth, newGameHeight

        TypedValue tempVal = new TypedValue();

        switch(gameProperties.getNewGameDifficulty()){
            case DifficultyType.EASY:
                gameManager.getAppContext().getResources().getValue(R.dimen.experimental_min_easy_bombs_to_tiles_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                gameManager.getAppContext().getResources().getValue(R.dimen.experimental_max_easy_bombs_to_tiles_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            case DifficultyType.MEDIUM:
                gameManager.getAppContext().getResources().getValue(R.dimen.experimental_min_medium_bombs_to_tile_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                gameManager.getAppContext().getResources().getValue(R.dimen.experimental_max_medium_bombs_to_tile_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            case DifficultyType.HARD:
                gameManager.getAppContext().getResources().getValue(R.dimen.experimental_min_hard_bombs_to_tile_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                gameManager.getAppContext().getResources().getValue(R.dimen.experimental_max_hard_bombs_to_tile_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            default:
                throw new RuntimeException("There is something wrong with the difficulty");
        }

        minCount=minCount* gameProperties.getNewGameWidth()*gameProperties.getNewGameHeight();
        maxCount=maxCount*gameProperties.getNewGameWidth()*gameProperties.getNewGameHeight();

        Random rand= new Random();
        int random_integer=rand.nextInt(Math.round(maxCount)-Math.round(minCount))+Math.round(minCount);

        /*Log.d("The random numbers",
                String.valueOf(maxCount)+"//"+String.valueOf(minCount));
        Log.d("The random generated no", String.valueOf(random_integer));*/
        return random_integer;
    }
}
