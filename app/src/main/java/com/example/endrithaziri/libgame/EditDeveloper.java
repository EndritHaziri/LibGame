package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import entity.Developer;
import view_model.DeveloperViewModel;
import view_model.GameViewModel;

public class EditDeveloper extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private Developer dev;
    private int id;
    private String newName;
    private TextView name;
    private DeveloperViewModel developerViewModel;
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
        setContentView(R.layout.activity_edit_developer);

        /**
         *  PREPARE VARIABLES
         */
        developerViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        name = findViewById(R.id.editTextDev);

        /**
         * GET DEVELOPER
         */
        id = getIntent().getIntExtra("id", 0);
        dev = developerViewModel.getDevById(id);

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

        /**
         * UPDATE THE DEVELOPER
         */
        developerViewModel.update(dev.getId(), newName);

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(EditDeveloper.this, R.string.developer_edited, Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * METHOD TO REMOVE THE CURRENT PUBLISHER
     */
    private void remove() {

        /**
         * REMOVE THE PUBLISHER
         */
        developerViewModel.deleteDeveloper(dev.getId());

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(EditDeveloper.this, R.string.developer_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}
