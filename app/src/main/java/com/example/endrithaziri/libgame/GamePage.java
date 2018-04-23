package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.Serializable;

import entity.Game;
import view_model.DeveloperViewModel;
import view_model.GameViewModel;
import view_model.PublisherViewModel;

public class GamePage extends AppCompatActivity {

    private Game g;
    private int id;

    private TextView description, title, publisher, developer;
    private BitmapDrawable pic;

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
                    return true;

                case R.id.navigation_remove:
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

        description = findViewById(R.id.gameDescription);
        description.setText(g.getDescription());

        developer = findViewById(R.id.gamedevelopper);
        developer.setText(developerViewModel.getDevById(g.getDeveloper_id()).getName());

        publisher = findViewById(R.id.gamePublisher);
        publisher.setText(publisherViewModel.getPubById(g.getPublisher_id()).getName());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.game_page_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}