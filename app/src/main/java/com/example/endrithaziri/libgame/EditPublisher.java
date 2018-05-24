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

import entity.Publisher;

public class EditPublisher extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Publisher pub;
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
                    Intent addgamepage = new Intent (EditPublisher.this,AddGame.class);
                    EditPublisher.this.startActivity(addgamepage);
                    return true;
                case R.id.navigation_remove:
                    remove();
                    Intent addgamepage2 = new Intent (EditPublisher.this,AddGame.class);
                    EditPublisher.this.startActivity(addgamepage2);
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
        setContentView(R.layout.activity_edit_publisher);

        /**
         *  PREPARE VARIABLES
         */
        name = findViewById(R.id.editTextPub);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("publishers");

        /**
         * GET PUBLISHER
         */
        pub = (Publisher)getIntent().getSerializableExtra("pub");

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        name.setText(pub.getName());

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * METHOD TO UPDATE THE CURRENT PUBLISHER
     */
    private void update() {
        /**
         * GET THE NEW TEXT
         */
        newName = name.getText().toString();
        Map<String,Object> newPub = new HashMap<>();
        newPub.put("name", newName);

        /**
         * UPDATE THE PUBLISHER
         */
        if(newName.trim().equals(""))
            Toast.makeText(EditPublisher.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        else {
            myRef.child(pub.getId()).removeValue();
            myRef.child(pub.getId()).updateChildren(newPub);

            /**
             * SHOW INFORMATIONS AND CLOSE
             */
            Toast.makeText(EditPublisher.this, R.string.publisher_edited, Toast.LENGTH_SHORT).show();
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
        myRef.child(pub.getId()).removeValue();

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(EditPublisher.this, R.string.publisher_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}
