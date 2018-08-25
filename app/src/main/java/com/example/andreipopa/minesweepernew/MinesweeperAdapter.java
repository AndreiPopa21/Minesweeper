package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MinesweeperAdapter extends RecyclerView.Adapter<MinesweeperAdapter.MinesweeperViewHolder>{

    private LayoutInflater mInflater;
    private Context applicationContext;
    private int tableRowsCount;
    private int tableColumnsCount;
    private int[][] tablePattern;
    private Table sessionTable;
    private TileClickListener tileClickListener;

    public interface TileClickListener{
        void onTileClick(MinesweeperViewHolder minesweeperViewHolder);
    }

    public MinesweeperAdapter(Context appContext,
                              int tableRowsCount,
                              int tableColumnsCount,
                              int[][] tablePattern,
                              Table sessionTable,
                              TileClickListener tileClickListener){
        this.applicationContext=appContext;
        this.mInflater=LayoutInflater.from(this.applicationContext);
        this.tableRowsCount=tableRowsCount;
        this.tableColumnsCount=tableColumnsCount;
        this.tablePattern=tablePattern;
        this.sessionTable=sessionTable;
        this.tileClickListener=tileClickListener;
    }


    @NonNull
    @Override
    public MinesweeperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.tile,parent,false);
        MinesweeperViewHolder viewHolder= new MinesweeperViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MinesweeperViewHolder holder, int position) {

        int columnIndex= position%tableColumnsCount;
        int rowIndex= position/tableColumnsCount;
        holder.thisTileClass=this.sessionTable.getTileAtPosition(rowIndex,columnIndex);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MinesweeperViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        public ImageView tileSprite;
        public Tile thisTileClass;

        public MinesweeperViewHolder(View itemView) {
            super(itemView);
            tileSprite=(ImageView)itemView.findViewById(R.id.tile_sprite_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            tileClickListener.onTileClick(this);
        }
    }
}
