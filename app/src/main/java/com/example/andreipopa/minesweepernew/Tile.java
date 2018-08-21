package com.example.andreipopa.minesweepernew;

public class Tile {

    private int tileIcon; //1 2 3 ... bomb empty
    private int xCoord; //coordinate for length
    private int yCoord; // coordinate for width

    public Tile(int tileIcon, int xCoord, int yCoord){
        this.tileIcon=tileIcon;
        this.xCoord= xCoord;
        this.yCoord= yCoord;
    }

    public int getTileIcon(){
        return this.tileIcon;
    }
    public int getxCoord(){
        return xCoord;
    }
    public int getyCoord(){
        return yCoord;
    }

    public void setTileIcon(int tileIcon){
        this.tileIcon=tileIcon;
    }

}
