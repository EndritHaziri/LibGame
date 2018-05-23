package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import entity.Developer;

public class AddDeveloper extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private String name;
    //private DeveloperViewModel devViewModel;
    private EditText etName;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    add();
                case R.id.navigation_cancel:
                    finish();
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
        setContentView(R.layout.activity_add_developer);

        /**
         *  PREPARE VARIABLES
         */
        //devViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        etName = findViewById(R.id.editTextDevelopper);

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO ADD A NEW DEVELOPER
     */
    protected void add() {
        /**
         * GET THE NEW TEXT
         */
        name = etName.getText().toString();

        /**
         * INSERT A NEW DEVELOPER
         */
        if(name.trim().equals(""))
            Toast.makeText(AddDeveloper.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        else {
            //devViewModel.insert(new Developer(name));

            /**
             * SHOW INFORMATION AND CLOSE
             */
            Toast.makeText(AddDeveloper.this, R.string.developer_saved, Toast.LENGTH_SHORT).show();
            finish();
        }


    }

}
