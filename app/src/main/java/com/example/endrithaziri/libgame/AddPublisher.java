package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import entity.Publisher;
import view_model.PublisherViewModel;

public class AddPublisher extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private String name;
    private PublisherViewModel publisherViewModel;
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
        setContentView(R.layout.activity_add_publisher);

        /**
         *  PREPARE VARIABLES
         */
        publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);
        etName = findViewById(R.id.editTextPublisher);

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO ADD A NEW PUBLISHER
     */
    protected void add() {
        /**
         * GET THE NEW TEXT
         */
        name = etName.getText().toString();

        /**
         * INSERT THE NEW PUBLISHER
         */
        if(name.trim().equals(""))
            Toast.makeText(AddPublisher.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        else {
            publisherViewModel.insert(new Publisher(name));

            /**
             * SHOW INFORMATION AND CLOSE
             */
            Toast.makeText(AddPublisher.this, R.string.publisher_saved, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
