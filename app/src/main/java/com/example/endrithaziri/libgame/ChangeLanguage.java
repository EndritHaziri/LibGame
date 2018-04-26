package com.example.endrithaziri.libgame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

/**
 * Created by Endrit Haziri on 26.04.2018.
 */

public class ChangeLanguage extends Activity {

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.navigation_home:
                Intent homepage = new Intent (ChangeLanguage.this,Home.class);
                ChangeLanguage.this.startActivity(homepage);
                return true;
        }
        return false;
    }
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);

            Spinner spinner = (Spinner) findViewById(R.id.spinner1);
            spinner.setPrompt("select language");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, languages);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView arg0, View arg1,
                                           int arg2, long arg3) {
                    Configuration config = new Configuration();
                    switch (arg2) {
                        case 0:
                            config.locale = Locale.ENGLISH;
                            break;
                        case 1:
                            config.locale = Locale.FRENCH;
                            break;
                        default:
                            config.locale = Locale.ENGLISH;
                            break;
                    }
                    getResources().updateConfiguration(config, null);
                }

                public void onNothingSelected(AdapterView arg0) {
                    // TODO Auto-generated method stub

                }
            });

        }

        public void onClick(View v){
            startActivity(new Intent(getBaseContext(), Settings.class));
        }
        private String[] languages = { "English", "Français"};
    }


