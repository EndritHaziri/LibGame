package com.example.endrithaziri.libgame;

import android.content.Intent;
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

import entity.Developer;

public class EditDeveloper extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Developer dev;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String newName;
    private TextView name;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ok:
                    update();
                    Intent addgamepage = new Intent (EditDeveloper.this,AddGame.class);
                    EditDeveloper.this.startActivity(addgamepage);
                    return true;
                case R.id.navigation_remove:
                    remove();
                    Intent addgamepage2 = new Intent (EditDeveloper.this,AddGame.class);
                    EditDeveloper.this.startActivity(addgamepage2);
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
        setContentView(R.layout.activity_edit_developer);

        /**
         *  PREPARE VARIABLES
         */
        name = findViewById(R.id.editTextDev);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("developers");

        /**
         * GET DEVELOPER
         */
        dev = (Developer)getIntent().getSerializableExtra("dev");

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        name.setText(dev.getName());

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO UPDATE THE CURRENT DEVELOPER
     */
    public void update() {
        /**
         * GET THE NEW TEXT
         */
        newName = name.getText().toString();

        Map<String,Object> newDev = new HashMap<>();
        newDev.put("name", newName);

        /**
         * UPDATE THE DEVELOPER
         */
        if(newName.trim().equals(""))
            Toast.makeText(EditDeveloper.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        else {
            myRef.child(dev.getId()).removeValue();
            myRef.child(dev.getId()).updateChildren(newDev);

            /**
             * SHOW INFORMATIONS AND CLOSE
             */
            Toast.makeText(EditDeveloper.this, R.string.developer_edited, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * METHOD TO REMOVE THE CURRENT PUBLISHER
     */
    private void remove() {

        /**
         * REMOVE THE PUBLISHER
         */
        myRef.child(dev.getId()).removeValue();

        /**
         * SHOW INFORMATION AND CLOSE
         */
        Toast.makeText(EditDeveloper.this, R.string.developer_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}
