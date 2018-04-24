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
import view_model.DeveloperViewModel;
import view_model.GameViewModel;
import view_model.PublisherViewModel;

public class GamePage extends AppCompatActivity {

    private Game g;
    private int id;

    private TextView description, title, publisher, developer;
    private ImageButton pic;
    private InputStream stream;

    private GameViewModel gameViewModel;
    private DeveloperViewModel developerViewModel;
    private PublisherViewModel publisherViewModel;

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
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamepage);

        id = getIntent().getIntExtra("id", 0);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        developerViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);

        g = gameViewModel.getGameById(id);

        Bitmap bitmap = AddGame.decodeToBase64(g.getUrl_image().trim());

        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        try {
            stream = getContentResolver().openInputStream(Uri.parse(g.getUrl_image()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap realImage = BitmapFactory.decodeStream(stream);

        pic = findViewById(R.id.imageGame);

        pic.setImageDrawable(drawable);

        description = findViewById(R.id.gameDescription);
        description.setText(g.getDescription());

        developer = findViewById(R.id.gamedevelopper);
        developer.setText(developerViewModel.getDevById(g.getDeveloper_id()).getName());

        publisher = findViewById(R.id.gamePublisher);
        publisher.setText(publisherViewModel.getPubById(g.getPublisher_id()).getName());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.game_page_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void remove() {
        gameViewModel.deleteGame(g.getId());
        Toast.makeText(GamePage.this, "Game deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

}