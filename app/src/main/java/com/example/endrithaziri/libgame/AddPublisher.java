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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import entity.Publisher;

public class AddPublisher extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private String name;
    private EditText etName;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    add();
                    Intent addgamepage = new Intent (AddPublisher.this,AddGame.class);
                    AddPublisher.this.startActivity(addgamepage);
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
        etName = findViewById(R.id.editTextPublisher);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("publishers");

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
            /**
             * INSERT THE NEW PUBLISHER - FIREBASE
             */
            Map<String,Object> pub = new HashMap<>();
            pub.put("name", name);

            if(name.trim().equals("")) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            } else {
                myRef.child(name).updateChildren(pub);
            }
            /**
             * SHOW INFORMATION AND CLOSE
             */
            Toast.makeText(AddPublisher.this, R.string.publisher_saved, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
