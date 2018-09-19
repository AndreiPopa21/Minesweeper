package com.example.andreipopa.minesweepernew;

import android.content.Context;

import java.util.Vector;

public class MinesweeperTable {

    private MinesweeperGameManager minesweeperGameManager;
    private Vector<Tile> tableTiles= new Vector<Tile>(64,1);
    private Context applicationContext;

    public MinesweeperTable(MinesweeperGameManager minesweeperGameManager){
        if(minesweeperGameManager==null){
            throw new RuntimeException("Invalid or null MinesweeperGameManager object in MinesweeperTable() constructor");
        }
        this.minesweeperGameManager=minesweeperGameManager;
        this.applicationContext=this.minesweeperGameManager.getApplicationContext();
        this.minesweeperGameManager.attachMinesweeperTableObject(this);
    }

    public Vector<Tile> getTableTiles(){
        return this.tableTiles;
    }

    public Tile getTileAtPosition(int xCoord, int yCoord){
        try{
            int positionInVector=xCoord*minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth()+yCoord;
            return tableTiles.elementAt(positionInVector);
        }catch (Exception e){
            throw new RuntimeException("X is: "+ String.valueOf(xCoord)+"// "+
                    "Y is: "+String.valueOf(yCoord));
        }
    }

    public void modifyTileIconAt(int xCoord, int yCoord, int newIcon){
        int positionInVector= xCoord*minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth()+yCoord;
        Tile tile= tableTiles.elementAt(positionInVector);
        tile.setTileIcon(newIcon);
    }

    public void createNewTile(int xCoord, int yCoord, int icon,int value){
        Tile tile= new Tile(value,icon,xCoord,yCoord,applicationContext);
        tableTiles.add(tile);
    }

    public void emptyTileVector(){
        tableTiles.clear();
    }

    public void hideIllusionAll(){
        for(int i=0;i<tableTiles.size();i++){
            tableTiles.elementAt(i).hideTile();
        }
    }
}
