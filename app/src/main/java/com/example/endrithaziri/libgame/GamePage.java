package com.example.endrithaziri.libgame;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entity.Game;

public class GamePage extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference gsReference;
    private Game game;
    private TextView description, title, publisher, developer;
    private ImageButton pic;
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
                    editGame.putExtra("game", game);
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
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("games");

        /** GET THE GAME BY ID */
        game = (Game)getIntent().getSerializableExtra("game");

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        title.setText(game.getTitle());
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(game.getUrl_image());
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(gsReference)
                .into(pic);
        pic.setImageDrawable(getResources().getDrawable(R.drawable.hearthstone_legende));
        description.setText(game.getDescription());

        try {
            developer.setText(game.getDeveloper_id());
        } catch (NullPointerException n) {
            developer.setText("UNKNOWN DEVELOPER");
        }

        try {
            publisher.setText(game.getPublisher_id());
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
        myRef.child(game.getId()).removeValue();

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(GamePage.this, R.string.game_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}