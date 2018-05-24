package com.example.endrithaziri.libgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import entity.Game;

public class EditGame extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Game g;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private TextView description, title;
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
        title = findViewById(R.id.etEditTitle);
        description = findViewById(R.id.etEditDescription);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("games");

        /**
         * GET THE GAME
         */
        g = (Game)getIntent().getSerializableExtra("game");

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        title.setText(g.getTitle());
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

        Map<String,Object> newGame = new HashMap<>();
        newGame.put("title", newTitle);
        newGame.put("description", newDescription);
        newGame.put("developer_id", g.getDeveloper_id());
        newGame.put("publisher_id", g.getPublisher_id());

        /**
         * UPDATE THE GAME
         */
        if(newTitle.trim().equals("") || newDescription.trim().equals(""))
            Toast.makeText(EditGame.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        else {
            myRef.child(g.getId()).removeValue();
            myRef.child(g.getId()).updateChildren(newGame);

            /**
             * SHOW INFORMATIONS AND CLOSE
             */
            Toast.makeText(EditGame.this, R.string.game_edited, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
