package com.example.andreipopa.minesweepernew;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    private float dpHeight;
    private float dpWidth;

    private int inputType; //the current input type (either FLAG or DETONATE)

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;

    private MinesweeperGameManager minesweeperGameManager;
    private MinesweeperAdapter minesweeperAdapter;
    private MinesweeperGameGenerator minesweeperGameGenerator;
    private MinesweeperTable minesweeperTable;

    private Button inputTypeButton;
    private Button replayButton;

    private boolean toReplay=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        dpWidth = displayMetrics.widthPixels / displayMetrics.density;


        toolbar= (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.tiles_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,
                1));

        inputTypeButton=(Button)findViewById(R.id.input_button);
        replayButton=(Button)findViewById(R.id.replay_button);
        inputTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { minesweeperGameManager.changeInputType(inputTypeButton); }
        });
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewMinesweeperGame();
            }
        });

        minesweeperGameManager= new MinesweeperGameManager(this);
        minesweeperTable= new MinesweeperTable(minesweeperGameManager);
        minesweeperGameGenerator= new MinesweeperGameGenerator(minesweeperGameManager);
        minesweeperAdapter=new MinesweeperAdapter(this,minesweeperGameManager, minesweeperTable,minesweeperGameManager);

        mRecyclerView.setAdapter(minesweeperAdapter);

        createNewMinesweeperGame();
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            hideSystemUI();
        }
    }
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                       // | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }*/

    private void createNewMinesweeperGame(){

        minesweeperTable.hideIllusionAll();

        MinesweeperGameProperties minesweeperGameProperties= new MinesweeperGameProperties(this);
        minesweeperGameProperties.setNewGameHeight(getApplicationContext().getResources().getInteger(R.integer.experimental_table_height));
        minesweeperGameProperties.setNewGameWidth(getApplicationContext().getResources().getInteger(R.integer.experimental_table_width));
        minesweeperGameProperties.setNewGameDifficulty(DifficultyType.EASY);
        minesweeperGameProperties.setNewGameMode(GameMode.CLASSICAL);
        minesweeperGameProperties.setNewTileSize(R.dimen.dimen_experimental_cell_size_dp);
        minesweeperGameProperties.decideTheBombsCount();

        minesweeperGameManager.attachNewMinesweeperGameProperties(minesweeperGameProperties);
        minesweeperGameGenerator.generateNewGame(minesweeperGameProperties);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,
                minesweeperGameManager.getMinesweeperGameProperties().getNewGameWidth()));
        //mRecyclerView.setAdapter(minesweeperAdapter);
        minesweeperAdapter.refreshTable();

    }

}
