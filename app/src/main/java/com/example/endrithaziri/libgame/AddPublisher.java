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

    private PublisherViewModel publisherViewModel;

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
        setContentView(R.layout.activity_add_publisher);

        publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected void add() {
        String name;
        EditText etName = findViewById(R.id.editTextDevelopper);
        name = etName.getText().toString();
        publisherViewModel.insert(new Publisher(name));
        Toast.makeText(AddPublisher.this, "Publisher saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
