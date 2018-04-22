package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import entity.Game;
import view_model.GameViewModel;

import static com.example.endrithaziri.libgame.AddGame.decodeToBase64;

public class Home extends AppCompatActivity {

    private GameViewModel gameViewModel;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    MenuItem item ;

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
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        linearLayout = findViewById(R.id.linearHomeLayout);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        //gameViewModel.insert(new Game("Pokemon", "Attrapez les tous", "pokemon.png", 1, 1));
        //gameViewModel.insert(new Game("Rocket League", "Le sel est présent", "rl.png", 1, 1));

        List<Game> games = gameViewModel.getAllGames();
        int cpt = games.size();
        System.out.println("nbr of games : " + cpt);
        
        for (Game g : games) {
            System.out.println(g.getName());
            ImageButton button = new ImageButton(this);
            //Bitmap bitmap = decodeToBase64(g.getUrl_image().trim());
            //button.setImageBitmap(bitmap);
            button.setImageResource(R.drawable.skyrim);
            button.setLayoutParams(params);
            button.setAdjustViewBounds(true);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent gamePage = new Intent (Home.this, GamePage.class);
                    Home.this.startActivity(gamePage);
                }
            });
            linearLayout.addView(button);
            linearLayout.addView(new View(this));
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
