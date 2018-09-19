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
        this.minesweeperGameManager=minesweeperGameManager;
        this.minesweeperTable=minesweeperTable;
        this.tileClickListener=tileClickListener;

    }

    @NonNull
    @Override
    public MinesweeperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.tile,parent,false);
        MinesweeperViewHolder viewHolder= new MinesweeperViewHolder(view,this.applicationContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MinesweeperViewHolder holder, int position) {

        int columnIndex= position%minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        int rowIndex= position/minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth();
        holder.thisTileClass=this.minesweeperTable.getTileAtPosition(rowIndex,columnIndex);
        holder.thisTileClass.setTileView(holder.itemView);
        holder.thisTileClass.setContext(this.applicationContext);
        holder.thisTileClass.setHolderOfThisClass(holder);
        holder.putOnTheDrawable();
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

        public MinesweeperViewHolder(View itemView,Context applicationContext) {
            super(itemView);
            tileImageView=(ImageView)itemView.findViewById(R.id.tile_sprite_imageView);
            itemView.setOnClickListener(this);
            this.itemView=itemView;
        }

        public void putOnTheDrawable(){
            tileImageView.setImageDrawable(this.thisTileClass.getTileContext().
            getResources().getDrawable(R.drawable.new_hidden));
             /*
            tileImageView.setImageDrawable(this.applicationContext.
                                   getResources().
                                   getDrawable(chooseProperDrawable(thisTileClass.getTileValue())));

            */
        }

        @Override
        public void onClick(View view) {
            tileClickListener.onTileClick(this);
        }
    }

    /*public static int chooseProperDrawable(int tileValue){

        int drawableCode=0;
        switch (tileValue){
            case ValueType.EMPTY:
                drawableCode=R.drawable.new_empty_tile;
                break;
            case ValueType.ONE:
                drawableCode=R.drawable.new_one_tile;
                break;
            case ValueType.TWO:
                drawableCode=R.drawable.new_two_tile;
                break;
            case ValueType.THREE:
                drawableCode=R.drawable.new_three_tile;
                break;
            case ValueType.FOUR:
                drawableCode=R.drawable.new_four_tile;
                break;
            case ValueType.FIVE:
                drawableCode=R.drawable.new_five_tile;
                break;
            case ValueType.SIX:
                drawableCode=R.drawable.new_six_tile;
                break;
            case ValueType.SEVEN:
                drawableCode=R.drawable.new_seven_tile;
                break;
            case ValueType.EIGHT:
                drawableCode=R.drawable.new_eight_tile;
                break;
            case ValueType.BOMB:
                drawableCode=R.drawable.bomb;
                break;
            default:
                throw new RuntimeException("Not a proper code for selecting drawable");
        }

        return drawableCode;
    }*/
}
