package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import entity.Game;

public class GamePage extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Game g;
    private int id;
    private TextView description, title, publisher, developer;
    private ImageButton pic;
    private Bitmap bitmap;
    private Drawable drawable;
    private InputStream stream;
    //private GameViewModel gameViewModel;
    //private DeveloperViewModel developerViewModel;
    //private PublisherViewModel publisherViewModel;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    finish();
                    return true;

                case R.id.navigation_edit:
                    Intent editGame = new Intent (GamePage.this, EditGame.class);
                    editGame.putExtra("id", g.getId());
                    GamePage.this.startActivity(editGame);
                    return true;

                case R.id.navigation_remove:
                    remove();
                    Intent homepage = new Intent (GamePage.this,Home.class);
                    GamePage.this.startActivity(homepage);
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
        setContentView(R.layout.activity_gamepage);

        /**
         *  PREPARE VARIABLES
         */
        title = findViewById(R.id.textViewTitle);
        pic = findViewById(R.id.imageGame);
        description = findViewById(R.id.gameDescription);
        developer = findViewById(R.id.gamedevelopper);
        publisher = findViewById(R.id.gamePublisher);
        //gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        //developerViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        //publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);

        /** GET THE GAME BY ID */
        id = getIntent().getIntExtra("id", 0);
        //g = gameViewModel.getGameById(id);

        /**
         *  GET AND DECODE THE PICTURE OF THE GAME
         */
        bitmap = AddGame.decodeToBase64(g.getUrl_image().trim());
        drawable = new BitmapDrawable(getResources(), bitmap);

        try {
            stream = getContentResolver().openInputStream(Uri.parse(g.getUrl_image()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        title.setText(g.getTitle());
        pic.setImageDrawable(drawable);
        description.setText(g.getDescription());

        try {
            //developer.setText(developerViewModel.getDevById(g.getDeveloper_id()).getName());
        } catch (NullPointerException n) {
            developer.setText("UNKNOWN DEVELOPER");
        }

        try {
            //publisher.setText(publisherViewModel.getPubById(g.getPublisher_id()).getName());
        } catch (NullPointerException n) {
            publisher.setText("UNKNOWN PUBLISHER");
        }

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.game_page_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO REMOVE THE GAME
     */
    public void remove() {
        /**
         * DELETE THE GAME
         */
        //gameViewModel.deleteGame(g.getId());

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(GamePage.this, R.string.game_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}