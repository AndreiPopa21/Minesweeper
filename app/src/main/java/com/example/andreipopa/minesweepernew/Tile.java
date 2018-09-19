package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

public class Tile {

    private int tileIcon; //1 2 3 ... bomb empty hidden wrong
    private int tileValue; //1 2 3.... empty bomb
    private int xCoord; //coordinate for length
    private int yCoord; // coordinate for width

    private boolean isRevelead; //swt whether revealead or hidden
    private boolean isFlagged;

    private View tileView;
    private ImageView tileImageView;
    private Context applicationContext;

    private MinesweeperAdapter.MinesweeperViewHolder holderOfThisClass;

    public Tile(int tileValue, int tileIcon, int xCoord, int yCoord,Context applicationContext){
        this.tileIcon=tileIcon;
        this.tileValue=tileValue;
        this.xCoord= xCoord;
        this.yCoord= yCoord;
        this.isRevelead=false;
        this.isFlagged=false;
        this.applicationContext=applicationContext;
    }

    public int getxCoord(){
        return xCoord;
    }
    public int getyCoord(){
        return yCoord;
    }

    public int getTileIcon(){
        return this.tileIcon;
    }
    public void setTileIcon(int tileIcon){
        this.tileIcon=tileIcon;
    }

    public int getTileValue(){
        return this.tileValue;
    }
    public void setTileValue(int newTileValue){
        this.tileValue=newTileValue;
    }

    public void setTileView(View view){
        this.tileView=view;
        this.tileImageView=(ImageView)view.findViewById(R.id.tile_sprite_imageView);
    }
    public View getTileView(){
        return this.tileView;
    }

    public Context getTileContext(){
        return this.applicationContext;
    }

    public void unrevealTile(){

        /*if(this.isRevelead){
            return;
        }*/
        this.setWhetherIsRevealed(true);
        tileImageView.setImageDrawable(
              this.applicationContext.getResources().
                      getDrawable(Utils.valueToDrawable(this.tileValue))
        );
    }





    public boolean getWhetherIsFlagged(){
        return this.isFlagged;
    }
    public boolean getWhetherIsRevelead(){
        return this.isRevelead;
    }

    public void setWhetherIsRevealed(boolean value){
        this.isRevelead=value;
        if(value){
            this.setWhetherIsFlagged(false);
        }
    }
    public void setWhetherIsFlagged(boolean value){
        this.isFlagged=value;
        if(value){
            this.setWhetherIsRevealed(false);
        }
    }



    public void unhideTile(){
        setCurrentDrawableAccordingToValue(this.tileValue);
    }

    public void setTileImageView(int tileIcon){
        tileImageView.setImageDrawable(applicationContext.getResources().getDrawable(tileIcon));
    }
    public MinesweeperAdapter.MinesweeperViewHolder getHolderOfThisClass() {
        return holderOfThisClass;
    }
    public void setHolderOfThisClass(MinesweeperAdapter.MinesweeperViewHolder holder){
        this.holderOfThisClass=holder;
    }
    public void hideTile(){
        tileImageView.setImageDrawable(applicationContext.getResources().getDrawable(R.drawable.new_hidden));
    }

    public void setCurrentDrawableAccordingToIcon(int icon){

        int id= Utils.iconToDrawable(icon);
        if(this.tileImageView==null){
            throw new RuntimeException("Invalid imageView for this tile");
        }

        if(id==R.drawable.new_hidden){
            this.setWhetherIsRevealed(false);
            this.setWhetherIsFlagged(false);
        }else{
            if(id==R.drawable.new_flagged_tile){
                this.setWhetherIsRevealed(false);
                this.setWhetherIsFlagged(true);
            }else{
                this.setWhetherIsRevealed(true);
                this.setWhetherIsFlagged(false);
            }
        }

        this.tileImageView.setImageDrawable(this.applicationContext.getResources().getDrawable(id));
    }

    public void setCurrentDrawableAccordingToValue(int value){
        int id= Utils.valueToDrawable(value);
        if(this.tileImageView==null){
            throw new RuntimeException("Invalid imageView for this tile");
        }

        if(id==R.drawable.new_hidden){
            this.setWhetherIsRevealed(false);
            this.setWhetherIsFlagged(false);
        }else {
            if(id==R.drawable.new_flagged_tile){
                this.setWhetherIsRevealed(false);
                this.setWhetherIsFlagged(true);
            }else{
                this.setWhetherIsRevealed(true);
                this.setWhetherIsFlagged(false);
            }
        }

        this.tileImageView.setImageDrawable(this.applicationContext.getResources().getDrawable(id));
    }
}
