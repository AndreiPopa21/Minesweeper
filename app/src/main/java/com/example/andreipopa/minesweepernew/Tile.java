package com.example.andreipopa.minesweepernew;

public class Tile {

    private int tileType; //1 2 3 ... bomb empty
    private boolean isRevelead; // hidden,
    private int xCoord;
    private int yCoord;

    public Tile(int tileType, int xCoord, int yCoord){
        this.tileType=tileType;
        this.xCoord= xCoord;
        this.yCoord= yCoord;
    }

    public int getTileType(){
        return this.tileType;
    }
    public boolean getIsRevelead(){
        return this.isRevelead;
    }
    public int getxCoord(){
        return xCoord;
    }
    public int getyCoord(){
        return yCoord;
    }

    public void setIsRevelead(boolean value){
        this.isRevelead= value;
    }

}
