package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

public class Tile {

    private int tileIcon; //1 2 3 ... bomb empty
    private int xCoord; //coordinate for length
    private int yCoord; // coordinate for width

    private boolean isRevelead;
    private boolean isFlagged;

    private View tileView;
    private ImageView tileImageView;
    private Context context;

    public Tile(int tileIcon, int xCoord, int yCoord){
        this.tileIcon=tileIcon;
        this.xCoord= xCoord;
        this.yCoord= yCoord;
        this.isRevelead=false;
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

    public void setTileView(View view){
        this.tileView=view;
        this.tileImageView=(ImageView)view.findViewById(R.id.tile_sprite_imageView);
    }
    public View getTileView(){
        return this.tileView;
    }

    public void setContext(Context newContext){
        this.context=newContext;
    }
    public Context getTileContext(){
        return this.context;
    }

    public void unrevealTile(){

        if(this.isRevelead){
            return;
        }

        this.setWhetherIsRevealed(true);

        tileImageView.setImageDrawable(
              this.context.getResources().
                      getDrawable(MinesweeperAdapter.chooseProperDrawable(this.tileIcon))
        );
    }


    //when tile is flagged, of course it is not revealed
    //setting isRevealed to false is only a measure of making sure
    //the value is perfectly updated along the game
    public void setIsFlagged(boolean value){
        this.isFlagged=value;
        if(value){
            this.setWhetherIsRevealed(false);
        }
    }
    public boolean getIsFlagged(){
        return this.isFlagged;
    }
    public boolean getWhetherIsRevelead(){
        return this.isRevelead;
    }

    //when tile is revelead, any flag on the tile will be destroyed
    //therefore no need to set isFlagged to true
    public void setWhetherIsRevealed(boolean value){
        this.isRevelead=value;
        if(value){
            this.setIsFlagged(false);
        }
    }

    public void setTileImageView(int tileIcon){
        tileImageView.setImageDrawable(context.getResources().getDrawable(tileIcon));
    }


}
