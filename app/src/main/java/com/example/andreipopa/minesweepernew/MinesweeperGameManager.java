package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.Random;
import java.util.Vector;

public class MinesweeperGameManager implements MinesweeperAdapter.TileClickListener {

    private MinesweeperTable minesweeperTable;
    private MinesweeperGameGenerator minesweeperGameGenerator;
    private MinesweeperGameProperties minesweeperGameProperties;
    private MinesweeperAdapter minesweeperAdapter;

    private Context applicationContext;
    private boolean isDetonateInputType;
    private int currentInputType;
    private  int[][] newGamePattern;


    public MinesweeperGameManager(Context context){
         this.applicationContext=context;
         this.isDetonateInputType=true;
         this.currentInputType=InputType.DETONATE;
    }


    public MinesweeperTable getMinesweeperTable() {
        return this.minesweeperTable;
    }
    public MinesweeperGameProperties getMinesweeperGameProperties() {
        return this.minesweeperGameProperties;
    }
    public Context getApplicationContext() {
        return this.applicationContext;
    }
    public int[][] getNewGamePattern(){
        return this.newGamePattern;
    }


    public void attachMinesweeperTableObject(MinesweeperTable minesweeperTable){
        this.minesweeperTable=minesweeperTable;
    }
    public void attachNewMinesweeperGameProperties(MinesweeperGameProperties minesweeperGameProperties){
        this.minesweeperGameProperties=minesweeperGameProperties;
        this.minesweeperTable.emptyTileVector();
    }
    public void attachMinesweeperGameGeneratorObject(MinesweeperGameGenerator gameGenerator){
        this.minesweeperGameGenerator=gameGenerator;
    }
    public void attachMinesweeperAdapter(MinesweeperAdapter minesweeperAdapter){this.minesweeperAdapter=minesweeperAdapter;}

    public void confirmNewGame(int[][] newGamePattern) {
        this.newGamePattern=newGamePattern;
        this.getMinesweeperTable().emptyTileVector();
        this.generateTheTileClasses();
    }

    public void generateTheTileClasses(){

        for(int i=0;i<getMinesweeperGameProperties().getNewGameHeight();i++){
            for(int j=0;j<getMinesweeperGameProperties().getNewGameWidth();j++){
                int value= newGamePattern[i][j];

                int valueType= Utils.patternValueToValue(value);
                if(getMinesweeperTable()!=null){
                    getMinesweeperTable().createNewTile(i,j,IconType.HIDDEN,valueType);
                }else{
                    throw new RuntimeException("There is no MinesweeperTable object attached to the MinesweeperGameManager");
                }
            }
        }
    }

    public void changeInputType(View button){
        this.isDetonateInputType= !isDetonateInputType;
        if(this.isDetonateInputType){
            currentInputType=InputType.DETONATE;
            button.setBackground(applicationContext.getResources().getDrawable(R.drawable.new_bomb_tile));
        }else{
            currentInputType=InputType.FLAG;
            button.setBackground(applicationContext.getResources().getDrawable(R.drawable.new_flag_input));
        }
    }

    //function under major reconstruction!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //this function will work with the Rules class in order to determine the flow of the game
    @Override
    public void onTileClick(MinesweeperAdapter.MinesweeperViewHolder minesweeperViewHolder) {
/*
        if(currentInputType==InputType.FLAG){

            if(minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){
                //by pressing when it is revelead, you want to undig the surrounding tiles
                if(minesweeperViewHolder.thisTileClass.getTileValue()==ValueType.EMPTY){
                    return;
                }
                if(minesweeperViewHolder.thisTileClass.getTileValue()==ValueType.BOMB
                        && minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){
                    return;
                }
                int x= minesweeperViewHolder.thisTileClass.getxCoord();
                int y= minesweeperViewHolder.thisTileClass.getyCoord();

                // Toast.makeText(this.applicationContext,"E REVELAT",Toast.LENGTH_SHORT).show();
                if(MinesweeperRules.doesItHaveEnoughFlagsSet(this, x,y,
                        minesweeperViewHolder.thisTileClass.getTileValue(),
                        this.newGameMode)){

                    //  Toast.makeText(this.applicationContext,"DA MA ARE",Toast.LENGTH_SHORT).show();
                    if(MinesweeperRules.checkWhetherAllBombsFlagged(x,y,this,this.getGameMode())){
                        Toast.makeText(this.applicationContext,"HOPAAA",Toast.LENGTH_SHORT).show();
                    }else{
                        Vector<MinesweeperAdapter.MinesweeperViewHolder> targets=
                                MinesweeperRules.uncover_empty_tile(x,y,this.getGameMode(),
                                        UncoverSituation.ON_REVEALED_TILE,this);

                        for(int i=0;i<targets.size();i++){
                            targets.elementAt(i).thisTileClass.unrevealTile();
                            targets.elementAt(i).thisTileClass.setTileIcon(MinesweeperRules.iconAccordingToValue(this.newGamePattern[x][y]));
                        }
                    }

                }else{
                    Toast.makeText(this.applicationContext,"NU, NU ARE",Toast.LENGTH_SHORT).show();
                }
                //uncover the tiles around, and if unflagged bomb is found
                //uncover only when enough flags have been set according to tile icon
                //end game, lost game
                return;
            }else{
                //Toast.makeText(this.applicationContext,"NU E RELEVAT",Toast.LENGTH_SHORT).show();
                if(!minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){
                    if(minesweeperViewHolder.thisTileClass.getIsFlagged()){
                        minesweeperViewHolder.thisTileClass.setIsFlagged(false);
                        minesweeperViewHolder.thisTileClass.setTileImageView(R.drawable.new_hidden);
                        minesweeperViewHolder.thisTileClass.setTileIcon(IconType.HIDDEN);
                    }else{
                        minesweeperViewHolder.thisTileClass.setIsFlagged(true);
                        minesweeperViewHolder.thisTileClass.setTileImageView(R.drawable.new_flagged_tile);
                        minesweeperViewHolder.thisTileClass.setTileIcon(IconType.FLAG);
                    }
                }
            }
        }

        if(currentInputType==InputType.DETONATE){

            if(minesweeperViewHolder.thisTileClass.getIsFlagged()){ return; }
            if(minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){ return;}

            if(!minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){

                int x= minesweeperViewHolder.thisTileClass.getxCoord();
                int y= minesweeperViewHolder.thisTileClass.getyCoord();

                if(MinesweeperRules.isItABomb(this,x,y)){
                    minesweeperViewHolder.thisTileClass.setTileIcon(IconType.RED_BOMB);
                    minesweeperViewHolder.thisTileClass.setTileImageView(R.drawable.new_red_bomb);
                    //END GAME
                }else{
                    if(minesweeperViewHolder.thisTileClass.getTileValue()!=ValueType.EMPTY){
                        minesweeperViewHolder.thisTileClass.unrevealTile();
                        minesweeperViewHolder.thisTileClass.
                                setTileIcon(MinesweeperRules.iconAccordingToValue(this.newGamePattern[x][y]));
                    }else{
                        Vector<MinesweeperAdapter.MinesweeperViewHolder> targets=
                                MinesweeperRules.uncover_empty_tile(x,y,this.getGameMode(),UncoverSituation.ON_EMPTY_TILE,this);
                        for(int i=0;i<targets.size();i++){
                            targets.elementAt(i).thisTileClass.unrevealTile();
                            targets.elementAt(i).thisTileClass.
                                    setTileIcon(MinesweeperRules.iconAccordingToValue(targets.elementAt(i).thisTileClass.getTileValue()));
                        }
                    }
                }
                minesweeperViewHolder.thisTileClass.setWhetherIsRevealed(true);
            }
        }*/

        if(currentInputType== InputType.FLAG){
            onFlag(minesweeperViewHolder);
        }
        if(currentInputType == InputType.DETONATE){
            onDetonate(minesweeperViewHolder);
        }
    }

    private void onFlag(@NonNull MinesweeperAdapter.MinesweeperViewHolder holder){

        if(holder.thisTileClass.getWhetherIsRevelead()){

            if(holder.thisTileClass.getTileValue()==ValueType.EMPTY){
                showToast("Attempt to flag an empty revealed tile");
                return;
            }
            if(holder.thisTileClass.getTileValue()==ValueType.BOMB
                    && holder.thisTileClass.getWhetherIsRevelead()){
                showToast("Attempt to flag a bomb revealed tile ");
                return;
            }

        }else{
            if(!holder.thisTileClass.getWhetherIsRevelead()){
                if(holder.thisTileClass.getWhetherIsFlagged()){
                    holder.thisTileClass.setWhetherIsFlagged(false);
                    holder.thisTileClass.setTileImageView(R.drawable.new_hidden);
                    holder.thisTileClass.setTileIcon(IconType.HIDDEN);
                }else{
                    holder.thisTileClass.setWhetherIsFlagged(true);
                    holder.thisTileClass.setTileImageView(R.drawable.new_flagged_tile);
                    holder.thisTileClass.setTileIcon(IconType.FLAG);
                }
            }
        }

    }

    private void onDetonate(@NonNull MinesweeperAdapter.MinesweeperViewHolder holder){

        if(holder.thisTileClass.getWhetherIsFlagged()){
            showToast("Attempt to detonate flagged object");
            return; }
        if(holder.thisTileClass.getWhetherIsRevelead()){
            showToast("Attempt to detonate already revealed object");
            return;}

        if(!holder.thisTileClass.getWhetherIsRevelead()){
            int x= holder.thisTileClass.getxCoord();
            int y= holder.thisTileClass.getyCoord();

            if(MinesweeperRules.checkWhetherTileHasSingleValue(this,x,y)){
                holder.thisTileClass.unhideTile();
                return;}

            if(MinesweeperRules.checkWhetherTileIsEmpty(this,x,y)) {
                showToast("Detonate on a surface");
                return; }

            if(MinesweeperRules.checkWhetherTileIsBomb(this,x,y)){
                showToast("You lost game, detonate on a bomb");
                return; }

            throw new RuntimeException("Not all detonate cases are covered");
        }

    }

    private void showToast(String message){
        Toast.makeText(this.applicationContext,message,Toast.LENGTH_SHORT).show();
    }
}
