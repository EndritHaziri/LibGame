package com.example.endrithaziri.libgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageButton imageButton;
    MenuItem item ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_add:
                    mTextMessage.setText(R.string.title_add);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageButton = findViewById(R.id.imageGame1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gamepage = new Intent (Home.this,GamePage.class);
                Home.this.startActivity(gamepage);

            }
        });

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            int id = item.getItemId();
          //      case R.id.navigation_home:
          //          Intent homepage = new Intent (Home.this,Home.class);
          //          this.startActivity(homepage);
          //          return true;
              if(id = R.id.navigation_add){
                    Intent addgame = new Intent (Home.this,AddGame.class);
                    startActivity(addgame);
                    return true;

                    return super.onOptionsItemSelected(item);}
            }
        }


        mTextMessage = (TextView) findViewById(R.id.message);
  //      BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    //    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
