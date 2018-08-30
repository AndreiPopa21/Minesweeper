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
        MinesweeperViewHolder viewHolder= new MinesweeperViewHolder(view,this.applicationContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MinesweeperViewHolder holder, int position) {

        int columnIndex= position%tableColumnsCount;
        int rowIndex= position/tableColumnsCount;
        holder.thisTileClass=this.sessionTable.getTileAtPosition(rowIndex,columnIndex);
        holder.thisTileClass.setTileView(holder.view);
        holder.thisTileClass.setContext(this.applicationContext);
        holder.putOnTheDrawable();
    }

    @Override
    public int getItemCount() {
        return tableColumnsCount*tableRowsCount;
    }

    public class MinesweeperViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        public ImageView tileSprite;
        public Tile thisTileClass;

        private String coordTag="";
        private Context applicationContext;

        private View view;

        public String getCoordTag(){
            return coordTag;
        }
        public void setCoordTag(String newTag){
            this.coordTag=newTag;
        }

        public MinesweeperViewHolder(View itemView,Context applicationContext) {
            super(itemView);
            tileSprite=(ImageView)itemView.findViewById(R.id.tile_sprite_imageView);
            this.applicationContext=applicationContext;
            itemView.setOnClickListener(this);
            this.view=itemView;
        }

        public void putOnTheDrawable(){
            tileSprite.setImageDrawable(this.applicationContext.
            getResources().getDrawable(R.drawable.new_hidden));

           /* tileSprite.setImageDrawable(this.applicationContext.
                                   getResources().
                                   getDrawable(chooseProperDrawable(thisTileClass.getTileIcon())));


        */}

        @Override
        public void onClick(View view) {
            tileClickListener.onTileClick(this);
        }
    }

    public static int chooseProperDrawable(int tileIcon){

        int drawableCode=0;
        switch (tileIcon){
            case IconAnnotations.EMPTY:
                drawableCode=R.drawable.new_empty_tile;
                break;
            case IconAnnotations.ONE:
                drawableCode=R.drawable.new_one_tile;
                break;
            case IconAnnotations.TWO:
                drawableCode=R.drawable.new_two_tile;
                break;
            case IconAnnotations.THREE:
                drawableCode=R.drawable.new_three_tile;
                break;
            case IconAnnotations.FOUR:
                drawableCode=R.drawable.new_four_tile;
                break;
            case IconAnnotations.FIVE:
                drawableCode=R.drawable.new_five_tile;
                break;
            case IconAnnotations.SIX:
                drawableCode=R.drawable.new_six_tile;
                break;
            case IconAnnotations.SEVEN:
                drawableCode=R.drawable.new_seven_tile;
                break;
            case IconAnnotations.EIGHT:
                drawableCode=R.drawable.new_eight_tile;
                break;
            case IconAnnotations.BOMB:
                drawableCode=R.drawable.bomb;
                break;
            default:
                throw new RuntimeException("Not a proper code for selecting drawable");
        }

        return drawableCode;
    }
}
