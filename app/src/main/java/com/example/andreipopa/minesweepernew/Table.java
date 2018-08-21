package com.example.andreipopa.minesweepernew;

import java.util.Vector;

public class Table {

    private int tableLength; //in relation to the x-coordinate
    private int tableWidth; //in relation to the y-coordinate

    private int tileSize_dp; //the current size of a tile in dp

    private int bombsCount; //the current number of bombs on the table

    private Vector<Tile> tableTiles= new Vector<Tile>(64,1);

    public Table(int tableLength,
                 int tableWidth,
                 int tileSize_dp,
                 int bombsCount){

        this.tableLength=tableLength;
        this.tableWidth=tableWidth;
        this.tileSize_dp=tileSize_dp;
        this.bombsCount= bombsCount;

    }

    public Vector<Tile> getTableTiles(){
        return this.tableTiles;
    }

    public Tile getTileAtPosition(int xCoord, int yCoord){
        int positionInVector=xCoord*tableWidth+yCoord;
        return tableTiles.elementAt(positionInVector);
    }

    public void modifyTileIconAt(int xCoord, int yCoord, int newIcon){
        int positionInVector= xCoord*tableWidth+yCoord;
        Tile tile= tableTiles.elementAt(positionInVector);
        tile.setTileIcon(newIcon);
    }

    public void createNewTile(int xCoord, int yCoord, int icon){
        Tile tile= new Tile(icon,xCoord,yCoord);
        tableTiles.add(tile);
    }

    public void emptyTileVector(){
        tableTiles.clear();
    }
}