package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import entity.Game;
import view_model.GameViewModel;

public class Home extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private GameViewModel gameViewModel;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private InputStream stream;
    private ImageButton button;
    private Bitmap bitmap;
    private Drawable drawable;
    private Intent gamePage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homepage = new Intent (Home.this,Home.class);
                    Home.this.startActivity(homepage);
                    return true;

                case R.id.navigation_add:
                    Intent addgame = new Intent (Home.this,AddGame.class);
                    Home.this.startActivity(addgame);
                    return true;

                case R.id.navigation_settings:
                    Intent settings = new Intent(Home.this,Settings.class);
                    Home.this.startActivity(settings);
                    return true;

            }
            return false;
        }
    };


    /**
     * ON CREATE METHOD
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         *  PREPARE VARIABLES
         */
        linearLayout = findViewById(R.id.linearHomeLayout);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
    }

    /**
     * ON RESUME
     */
    @Override
    protected void onResume() {
        super.onResume();

        linearLayout.removeAllViews();

        /** GET ALL GAMES */
        List<Game> games = gameViewModel.getAllGames();

        /** BUILD UI */
        buildUI(games);

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO BUILD UI (1 IMAGE BY GAME)
     * @param games
     */
    protected void buildUI(List<Game> games) {
        for (final Game g : games) {
            button = new ImageButton(this);
            bitmap = AddGame.decodeToBase64(g.getUrl_image().trim());
            drawable = new BitmapDrawable(getResources(), bitmap);
            try {
                stream = getContentResolver().openInputStream(Uri.parse(g.getUrl_image()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            button.setImageDrawable(drawable);
            button.setLayoutParams(params);
            button.setAdjustViewBounds(true);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    gamePage = new Intent (Home.this, GamePage.class);
                    gamePage.putExtra("id", g.getId());
                    System.out.println(g.getId());
                    Home.this.startActivity(gamePage);
                }
            });
            linearLayout.addView(button);
            linearLayout.addView(new View(this));
        }
    }
}
