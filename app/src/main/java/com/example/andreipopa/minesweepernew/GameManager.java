package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.andreipopa.minesweepernew.Rules;
import com.example.andreipopa.minesweepernew.Rules.MiniTileInfo;


public class GameManager implements MinesweeperAdapter.TileClickListener {

    private int newGameMode; //the current game mode of this session
    private int newGameWidth; //corresponding to the y-coord
    private int newGameHeight; //corresponding to the x-coord
    private int newGameDifficulty; //the current game difficulty, decisive factor
                                   //when generating the newGame recipe
                                   //could be used for the count of the bombs as well as for
                                   //their positioning
    private int newBombsCount;
    private int newTileSize; //the tileSize is actually the defining factor for the height and
                             // width of the table. the different tile sizes are predefined sizes
                             //which will be selected from the preferences menu

    private  int[][] newGamePattern;

    private Table table;

    private boolean isDetonateInputType;
    private int currentInputType;

    private Context applicationContext;
    //the GameManager class is initialized only once during the entire app lifecycle
    public GameManager(Context context){
         this.applicationContext=context;
         this.isDetonateInputType=true;
         this.currentInputType=InputType.DETONATE;
    }

    public void setGameMode(int newGameMode){
        this.newGameMode=newGameMode;
    }
    public int getGameMode(){
        return this.newGameMode;
    }
    public void setNewGameWidth(int newGameWidth){
        this.newGameWidth=newGameWidth;
    }
    public int getNewGameWidth(){
        return this.newGameWidth;
    }
    public void setNewGameHeight(int newGameHeight){
        this.newGameHeight=newGameHeight;
    }
    public int getNewGameHeight(){
        return this.newGameHeight;
    }
    public void setNewGameDifficulty(int newGameDifficulty){
        this.newGameDifficulty=newGameDifficulty;
    }
    public int getNewGameDifficulty(){
        return this.newGameDifficulty;
    }
    public int getNewBombsCount(){
        return this.newBombsCount;
    }
    public void setNewBombsCount(int newBombsCount){
        this.newBombsCount=newBombsCount;
    }
    public void setNewTileSize(int newTileSize){
        this.newTileSize=newTileSize;
    }
    public int getNewTileSize(){
        return this.newTileSize;
    }
    public Table getTheTable(){
        return table;
    }
    public Context getAppContext() {
        return this.applicationContext;
    }
    public void setApplicationContext(Context newContext){
        this.applicationContext=newContext;
    }
    public int[][] getNewGamePattern(){
        return newGamePattern;
    }

    //this is called every time we require a new game session
    //!!!! THE FUNCTION HAS TO RETURN AN ADAPTER IN THE MAINACTIVITY FOR THE RECYCLERVIEW
    public MinesweeperAdapter generateNewConfiguration(int newGameHeight,
                                            int newGameWidth,
                                            int newGameMode,
                                            int newGameDifficulty,
                                            int newTileSize){

        setNewGameHeight(newGameHeight);
        setNewGameWidth(newGameWidth);
        setGameMode(newGameMode);
        setNewGameDifficulty(newGameDifficulty);
        setNewTileSize(newTileSize);

        //Log.d("Output test",String.valueOf(newGameHeight)+"//"+String.valueOf(newGameWidth));
        newGamePattern= new int[newGameHeight][newGameWidth];
        resetNewConfiguration();
       // outputTablePattern();

        int bombsCount=decideTheBombsCount();
        setTheBombs(bombsCount);
        //outputTablePattern();

        for(int i=0;i<newGameHeight;i++){
            for(int j=0;j<newGameWidth;j++){

                if(newGamePattern[i][j]==0){ //if it is a free tile
                    int neighboursCount=countEachTileNeighbours(i,j);
                    newGamePattern[i][j]=neighboursCount;
                }
            }
        }
      //  outputTablePattern();
        this.table= new Table(getNewGameHeight(),
                         getNewGameWidth(),
                         getNewTileSize(),
                         bombsCount);

        generateTheTileClasses();

        return generateTheAdapter();
    }

    private void outputTablePattern(){

        Log.d("Output pattern",
                String.valueOf(this.getNewGameHeight())+
                        "//"+String.valueOf(this.getNewGameWidth()));

        String s="\n";
        for(int i=0;i<getNewGameHeight();i++){

            for(int j=0;j<getNewGameWidth();j++){
                s=s+ Integer.toString(newGamePattern[i][j])+'\t';
            }
            s=s+'\n';

        }
        Log.d("Output pattern",s);
    }

    private void resetNewConfiguration(){

       setNewBombsCount(0);
       for(int i=0;i<this.newGameHeight;i++){
           for(int j=0;j<this.newGameWidth;j++){
               newGamePattern[i][j]=0;
           }
       }
    }

    //function the according to the game difficulty and the table sizes, outputs
    //a specific number of bombs, later these parameters could process some bomb cluster features
    private int decideTheBombsCount(){
        //the algorithm for generating the bombs count actually computes a minCount and a maxCount value
        //the specific number of bombs is a random-chosen number in this interval
        float minCount=0.0f;
        float maxCount=0.0f;

        //{...
        // ...
        // ..}  the algorithm for deciding the two boundaries according to the 3 parameters:
        // newGameDifficulty, newGameWidth, newGameHeight

        TypedValue tempVal = new TypedValue();

        switch(newGameDifficulty){
            case DifficultyType.EASY:
                getAppContext().getResources().getValue(R.dimen.experimental_min_easy_bombs_to_tiles_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                getAppContext().getResources().getValue(R.dimen.experimental_max_easy_bombs_to_tiles_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            case DifficultyType.MEDIUM:
                getAppContext().getResources().getValue(R.dimen.experimental_min_medium_bombs_to_tile_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                getAppContext().getResources().getValue(R.dimen.experimental_max_medium_bombs_to_tile_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            case DifficultyType.HARD:
                getAppContext().getResources().getValue(R.dimen.experimental_min_hard_bombs_to_tile_ratio,tempVal,true);
                minCount=tempVal.getFloat();
                getAppContext().getResources().getValue(R.dimen.experimental_max_hard_bombs_to_tile_ratio,tempVal,true);
                maxCount=tempVal.getFloat();
                break;
            default:
                throw new RuntimeException("There is something wrong with the difficulty");
        }

        minCount=minCount*newGameWidth*newGameHeight;
        maxCount=maxCount*newGameWidth*newGameHeight;

        Random rand= new Random();
        int random_integer=rand.nextInt(Math.round(maxCount)-Math.round(minCount))+Math.round(minCount);

        /*Log.d("The random numbers",
                String.valueOf(maxCount)+"//"+String.valueOf(minCount));
        Log.d("The random generated no", String.valueOf(random_integer));*/
        return random_integer;
    }

    //position each bomb in a selected position
    private void setTheBombs(int bombsCount){

        Random rand=new Random();
        while(bombsCount>0){
            int randX=rand.nextInt(newGameHeight-1);
            int randY=rand.nextInt(newGameWidth-1);

            if(randX<0 && randX>=newGameHeight && randY<0 && randY>=newGameWidth){
                continue;
            } else{
                if(newGamePattern[randX][randY]!=0){
                    continue;
                }else{
                    if(newGamePattern[randX][randY]==0){
                        newGamePattern[randX][randY]= -1;
                        bombsCount-=1;
                    }
                }
            }
        }
    }

    //count each tile neighbours according to the current game mode
    // e.g. CLASSICAL counts only the eight-adjacent neighbours
    // e.g. KNIGHTPATHS counts the eight neighbours  at a chess-knight-movement position
    // begin count the neighbour directions clockwise starting from 12 o'clock
    private int countEachTileNeighbours(int tileX,int tileY){
        int[] xDir= new int[]{0,0,0,0,0,0,0,0};
        int[] yDir= new int[]{0,0,0,0,0,0,0,0};

        switch (this.newGameMode){
            case GameMode.CLASSICAL:
                xDir= new int[]{-1,-1,0,1,1,1,0,-1};
                yDir= new int[]{0,1,1,1,0,-1,-1,-1};
                break;
            case GameMode.KNIGHTPATHS:
                xDir= new int[]{-2,-1,1,2,2,1,-1,-2};
                yDir= new int[]{1,2,2,1,-1,-2,-2,-1};
                break;
            default:
                throw new RuntimeException("This game mode is either obsolete or does not exist");
        }

        int neighboursCount=0;
        for(int i=0;i<8;i++){
            int newX= tileX+xDir[i];
            int newY= tileY+yDir[i];

            if(newX>=0 && newX<newGameHeight && newY>=0 && newY<newGameWidth){
                if(newGamePattern[newX][newY]== -1){
                    neighboursCount+=1;
                }else{
                    neighboursCount+=0;
                }
            }else{
                neighboursCount+=0;
            }
        }

        return neighboursCount;
    }

    public void generateTheTileClasses(){

        for(int i=0;i<getNewGameHeight();i++){
            for(int j=0;j<getNewGameWidth();j++){
                int value= newGamePattern[i][j];
                int icon= IconAnnotations.UNDEFINED_ICON;

                switch (value){
                    case -1:
                        icon=IconAnnotations.BOMB;
                        break;
                    case 0:
                        icon=IconAnnotations.EMPTY;
                        break;
                    case 1:
                        icon=IconAnnotations.ONE;
                        break;
                    case 2:
                        icon= IconAnnotations.TWO;
                        break;
                    case 3:
                        icon=IconAnnotations.THREE;
                        break;
                    case 4:
                        icon=IconAnnotations.FOUR;
                        break;
                    case 5:
                        icon=IconAnnotations.FIVE;
                        break;
                    case 6:
                        icon=IconAnnotations.SIX;
                        break;
                    case 7:
                        icon=IconAnnotations.SEVEN;
                        break;
                    case 8:
                        icon=IconAnnotations.EIGHT;
                        break;
                }

                table.createNewTile(i,j,icon);
            }
        }
    }

    public void changeInputType(View button){
        this.isDetonateInputType= !isDetonateInputType;
        if(this.isDetonateInputType){
            currentInputType=InputType.DETONATE;
            button.setBackground(applicationContext.getResources().getDrawable(R.drawable.new_bomb_tile));
        }else{
            currentInputType=InputType.FLAG;
            button.setBackground(applicationContext.getResources().getDrawable(R.drawable.new_flag_input));
        }
    }

    public MinesweeperAdapter generateTheAdapter(){
         return new MinesweeperAdapter(getAppContext(),
                                       getNewGameHeight(),
                                       getNewGameWidth(),
                                       getNewGamePattern(),
                                       getTheTable(),
                                       this) ;
    }


    //function under major reconstruction!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //this function will work with the Rules class in order to determine the flow of the game
    @Override
    public void onTileClick(MinesweeperAdapter.MinesweeperViewHolder minesweeperViewHolder) {


        if(currentInputType==InputType.FLAG){

            if(minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){
                if(Rules.doesItHaveEnoughFlagsSet(this,
                        minesweeperViewHolder.thisTileClass.getxCoord(),
                        minesweeperViewHolder.thisTileClass.getyCoord(),
                        minesweeperViewHolder.thisTileClass.getTileIcon(),
                        this.newGameMode)){

                    Toast.makeText(this.applicationContext,"DA MA ARE",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this.applicationContext,"NU, NU ARE",Toast.LENGTH_SHORT).show();
                }
                //uncover the tiles around, and if unflagged bomb is found
                //uncover only when enough flags have been set according to tile icon
                //end game, lost game
                return;
            }else{
                if(!minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){
                    if(minesweeperViewHolder.thisTileClass.getIsFlagged()){
                        minesweeperViewHolder.thisTileClass.setIsFlagged(false);
                        minesweeperViewHolder.thisTileClass.setTileImageView(R.drawable.new_hidden);
                        minesweeperViewHolder.thisTileClass.setTileIcon(IconAnnotations.HIDDEN);
                    }else{
                        minesweeperViewHolder.thisTileClass.setIsFlagged(true);
                        minesweeperViewHolder.thisTileClass.setTileImageView(R.drawable.new_flagged_tile);
                        minesweeperViewHolder.thisTileClass.setTileIcon(IconAnnotations.FLAG);
                    }
                }
            }
        }

        if(currentInputType==InputType.DETONATE){

            if(minesweeperViewHolder.thisTileClass.getIsFlagged()){
                return;
            }

            if(!minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){

                int x= minesweeperViewHolder.thisTileClass.getxCoord();
                int y= minesweeperViewHolder.thisTileClass.getyCoord();

                if(Rules.isItABomb(this,x,y)){
                    minesweeperViewHolder.thisTileClass.setTileIcon(IconAnnotations.RED_BOMB);
                    minesweeperViewHolder.thisTileClass.setTileImageView(R.drawable.new_red_bomb);
                }else{
                    if(minesweeperViewHolder.thisTileClass.getTileIcon()!=IconAnnotations.EMPTY){
                        minesweeperViewHolder.thisTileClass.unrevealTile();
                        minesweeperViewHolder.thisTileClass.setTileIcon(Rules.iconAccordingToNumber(this.newGamePattern[x][y]));
                    }else{
                        //uncover space
                        minesweeperViewHolder.thisTileClass.unrevealTile();
                    }
                }
                minesweeperViewHolder.thisTileClass.setWhetherIsRevealed(true);
            }

        }
        /*if(!minesweeperViewHolder.thisTileClass.getWhetherIsRevelead()){

           boolean value= Rules.isItABomb(
                    this,
                    minesweeperViewHolder.thisTileClass.getxCoord(),
                    minesweeperViewHolder.thisTileClass.getyCoord()
            );

            /*Toast.makeText(this.applicationContext,
                    String.valueOf(minesweeperViewHolder.thisTileClass.getxCoord())+
                            "//"+
                            String.valueOf(minesweeperViewHolder.thisTileClass.getyCoord()),
                    Toast.LENGTH_SHORT).show();

            Toast.makeText(this.applicationContext,
                    String.valueOf(value),
                            Toast.LENGTH_SHORT).show();



            minesweeperViewHolder.thisTileClass.unrevealTile();
        }*/
    }
}
