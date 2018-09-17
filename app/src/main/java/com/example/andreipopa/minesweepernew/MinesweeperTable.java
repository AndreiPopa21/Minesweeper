package com.example.andreipopa.minesweepernew;

import java.util.Vector;

public class MinesweeperTable {

    private int tableHeight; //in relation to the x-coordinate
    private int tableWidth; //in relation to the y-coordinate
    private int tileSize_dp; //the current size of a tile in dp
    private int bombsCount; //the current number of bombs on the table

    private Vector<Tile> tableTiles= new Vector<Tile>(64,1);

    public MinesweeperTable(int tableHeight,
                 int tableWidth,
                 int tileSize_dp,
                 int bombsCount){

       /* this.tableHeight=tableHeight;
        this.tableWidth=tableWidth;
        this.tileSize_dp=tileSize_dp;
        this.bombsCount= bombsCount;

        emptyTileVector();*/
    }

    public Vector<Tile> getTableTiles(){
        return this.tableTiles;
    }

    public Tile getTileAtPosition(int xCoord, int yCoord){
        try{
            int positionInVector=xCoord*tableWidth+yCoord;
            return tableTiles.elementAt(positionInVector);
        }catch (Exception e){
            throw new RuntimeException("X is: "+ String.valueOf(xCoord)+"// "+
                    "Y is: "+String.valueOf(yCoord));
        }

    }

    public void modifyTileIconAt(int xCoord, int yCoord, int newIcon){
        int positionInVector= xCoord*tableWidth+yCoord;
        Tile tile= tableTiles.elementAt(positionInVector);
        tile.setTileIcon(newIcon);
    }

    public void createNewTile(int xCoord, int yCoord, int icon,int value){
        Tile tile= new Tile(value,icon,xCoord,yCoord);
        tableTiles.add(tile);
    }

    public void emptyTileVector(){
        tableTiles.clear();
    }
}
