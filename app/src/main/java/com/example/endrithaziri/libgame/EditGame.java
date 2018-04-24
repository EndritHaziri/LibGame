package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import entity.Game;
import view_model.GameViewModel;

public class EditGame extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Game g;
    private int id;
    private TextView description, title;
    private GameViewModel gameViewModel;
    private String newTitle, newDescription;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ok:
                    update();
                    return true;
                case R.id.navigation_cancel:
                    finish();
                    return true;
            }
            return false;
        }
    };

    /**
     * ON CREATE
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        /**
         *  PREPARE VARIABLES
         */
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        title = findViewById(R.id.etEditTitle);
        description = findViewById(R.id.etEditDescription);

        /**
         * GET THE GAME
         */
        id = getIntent().getIntExtra("id", 0);
        g = gameViewModel.getGameById(id);

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        title.setText(g.getName());
        description.setText(g.getDescription());

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO UPDATE THE CURRENT GAME
     */
    public void update() {
        /**
         * GET THE NEW TEXT
         */
        newTitle = title.getText().toString();
        newDescription = description.getText().toString();

        /**
         * UPDATE THE GAME
         */
        gameViewModel.update(g.getId(), newTitle, newDescription);

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(EditGame.this, "Game edited", Toast.LENGTH_SHORT).show();
        finish();
    }

}
