package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import entity.Publisher;
import view_model.PublisherViewModel;

public class EditPublisher extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Publisher pub;
    private int id;
    private String newName;
    private TextView name;
    private PublisherViewModel publisherViewModel;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ok:
                    update();
                    return true;
                case R.id.navigation_remove:
                    remove();
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
        publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);
        name = findViewById(R.id.editTextPub);

        /**
         * GET PUBLISHER
         */
        id = getIntent().getIntExtra("id", 0);
        pub = publisherViewModel.getPubById(id);

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

        /**
         * UPDATE THE PUBLISHER
         */
        publisherViewModel.update(pub.getId(), newName);

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(EditPublisher.this, R.string.publisher_edited, Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * METHOD TO REMOVE THE CURRENT PUBLISHER
     */
    private void remove() {

        /**
         * REMOVE THE PUBLISHER
         */
        publisherViewModel.deletePublisher(pub.getId());

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(EditPublisher.this, R.string.publisher_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}
