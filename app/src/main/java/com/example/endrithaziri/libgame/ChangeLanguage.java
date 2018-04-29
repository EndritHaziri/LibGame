package com.example.endrithaziri.libgame;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static locale.LocaleHelper.updateLanguage;

/**
 * Created by Endrit Haziri on 26.04.2018.
 */

public class ChangeLanguage extends Activity {

    /**
     * VARIABLE DECLARATION
     */
    private ListView lang;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    finish();
                    return true;
            }
            return false;
        }
    };

    /**
     * ON CREATE METHOD
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        /**
         *  PREPARE VARIABLES
         */
        lang = findViewById(R.id.listViewLang);
        lang.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item,getResources().getStringArray(R.array.list_lang)));
        lang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        changeLanguage("fr");
                        return;
                    case 1:
                        changeLanguage("en");
                        return;
                }
            }
        });

        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * CHANGE THE LANGUAGE OF THE APP
     */
    protected void changeLanguage(String lang) {
        updateLanguage(this, lang);
    }
}


