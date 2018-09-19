package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MinesweeperAdapter extends RecyclerView.Adapter<MinesweeperAdapter.MinesweeperViewHolder>{

    private LayoutInflater mInflater;
    public Context applicationContext;
    private MinesweeperTable minesweeperTable;
    private MinesweeperGameManager minesweeperGameManager;
    private TileClickListener tileClickListener;

    public interface TileClickListener{
        void onTileClick(MinesweeperViewHolder minesweeperViewHolder);
    }

    public MinesweeperAdapter(Context applicationContext,
                              MinesweeperGameManager minesweeperGameManager,
                              MinesweeperTable minesweeperTable,
                              TileClickListener tileClickListener){
        this.applicationContext=applicationContext;
        this.mInflater=LayoutInflater.from(this.applicationContext);

        if(minesweeperGameManager==null){
            throw new RuntimeException("Passed invalid MinesweeperGameManager object to MinesweeperAdapter constructor");
        }
        if(minesweeperTable==null){
            throw new RuntimeException("Passed invalid MinesweeperTable object to MinesweeperAdapter constructor");
        }

        this.minesweeperGameManager=minesweeperGameManager;
        this.minesweeperTable=minesweeperTable;
        this.tileClickListener=tileClickListener;
        this.minesweeperGameManager.attachMinesweeperAdapter(this);
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

        int columnIndex= position%minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int rowIndex= position/minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        holder.thisTileClass=this.minesweeperTable.getTileAtPosition(rowIndex,columnIndex);
        holder.thisTileClass.setTileView(holder.itemView);
        holder.thisTileClass.setHolderOfThisClass(holder);
        holder.thisTileClass.setCurrentDrawableAccordingToIcon(IconType.HIDDEN);
        //holder.thisTileClass.setCurrentDrawableAccordingToValue(holder.thisTileClass.getTileValue());
    }

    @Override
    public int getItemCount() {
        return minesweeperTable.getTableTiles().size();
    }

    public void refreshTable(){
        notifyDataSetChanged();
    }

    public class MinesweeperViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        public ImageView tileImageView;
        public Tile thisTileClass;
        private View itemView;

        public MinesweeperViewHolder(View itemView) {
            super(itemView);
            tileImageView=(ImageView)itemView.findViewById(R.id.tile_sprite_imageView);
            itemView.setOnClickListener(this);
            this.itemView=itemView;
        }

        @Override
        public void onClick(View view) {
            tileClickListener.onTileClick(this);
        }
    }

}
