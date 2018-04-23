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
import view_model.DeveloperViewModel;

public class AddDeveloper extends AppCompatActivity {

    private DeveloperViewModel devViewModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_developer);

        devViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected void add() {
        String name;
        EditText etName = findViewById(R.id.editTextDevelopper);

        name = etName.getText().toString();

        devViewModel.insert(new Developer(name));
        Toast.makeText(AddDeveloper.this, "Developer saved", Toast.LENGTH_SHORT).show();
    }

}
