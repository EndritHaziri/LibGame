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

    private Game g;
    private int id;

    private TextView description, title;

    private GameViewModel gameViewModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        id = getIntent().getIntExtra("id", 0);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        g = gameViewModel.getGameById(id);

        title = findViewById(R.id.etEditTitle);
        title.setText(g.getName());
        description = findViewById(R.id.etEditDescription);
        description.setText(g.getDescription());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void update() {
        String newTitle, newDescription;
        newTitle = title.getText().toString();
        newDescription = description.getText().toString();

        gameViewModel.update(g.getId(), newTitle, newDescription);

        Toast.makeText(EditGame.this, "Game edited", Toast.LENGTH_SHORT).show();
        finish();
    }

}
