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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entity.Game;

public class Home extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    //private GameViewModel gameViewModel;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private InputStream stream;
    private ImageButton button;
    private Bitmap bitmap;
    private Drawable drawable;
    private Intent gamePage;
    private List<Game> games;
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

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("games");
        user = mAuth.getCurrentUser();
        games = new ArrayList<>();

        if(user == null) {
            Intent login = new Intent(Home.this,LoginActivity.class);
            Home.this.startActivity(login);
        } else {
            setContentView(R.layout.activity_home);

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot d: dataSnapshot.getChildren()) {
                        Game g = d.getValue(Game.class);
                        g.setId(d.getKey());
                        games.add(g);
                    }

                    buildUI(games);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println("failed");
                }
            });

            /**
             *  PREPARE VARIABLES
             */
            linearLayout = findViewById(R.id.linearHomeLayout);

            /**
             * NAVIGATION BAR
             */
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
    }

    /**
     * ON RESUME
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    
}
