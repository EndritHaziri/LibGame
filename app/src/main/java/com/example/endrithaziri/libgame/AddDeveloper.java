package com.example.endrithaziri.libgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddDeveloper extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private static final String TAG = "AddDeveloper";

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
        etName = findViewById(R.id.editTextDevelopper);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("developers");

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
            /**
             * INSERT THE NEW DEVELOPER - FIREBASE
             */
            Map<String,Object> dev = new HashMap<>();
            dev.put("name", name);

            if(name.trim().equals("")) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            } else {
                myRef.child(name).updateChildren(dev);
                Log.d(TAG, "Developer successfully added");
            }

            /**
             * SHOW INFORMATION AND CLOSE
             */
            Toast.makeText(AddDeveloper.this, R.string.developer_saved, Toast.LENGTH_SHORT).show();
            finish();
        }


    }

}
