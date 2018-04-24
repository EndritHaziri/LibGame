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

    private Developer dev;
    private int id;
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
                case R.id.navigation_cancel:
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_developer);

        developerViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        id = getIntent().getIntExtra("id", 0);
        dev = developerViewModel.getDevById(id);

        name = findViewById(R.id.editTextDev);
        name.setText(dev.getName());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void update() {
        String newName;
        newName = name.getText().toString();

        developerViewModel.update(dev.getId(), newName);

        Toast.makeText(EditDeveloper.this, "Developer edited", Toast.LENGTH_SHORT).show();
        finish();
    }

}
