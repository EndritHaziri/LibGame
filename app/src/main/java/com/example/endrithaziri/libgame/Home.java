package com.example.endrithaziri.libgame;

import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import entity.Game;
import view_model.GameViewModel;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageButton imageButton;
    private GameViewModel gameViewModel;

    MenuItem item ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            System.out.println("Plop");

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homepage = new Intent (Home.this,Home.class);
                    Home.this.startActivity(homepage);
                    return true;

                case R.id.navigation_add:
                    Intent addgame = new Intent (Home.this,AddGame.class);
                    Home.this.startActivity(addgame);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
<<<<<<< HEAD
=======

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                //adapter.setGames(games);
            }
        });
>>>>>>> e17598791eb5a0f7a2fa4e9f1f9f7ecbf909e46d

       /* imageButton = findViewById(R.id.imageGame1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gamepage = new Intent (Home.this,GamePage.class);
                Home.this.startActivity(gamepage);

            }
<<<<<<< HEAD

        });*/



=======
        });*/

>>>>>>> e17598791eb5a0f7a2fa4e9f1f9f7ecbf909e46d

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
