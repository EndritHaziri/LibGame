package com.example.endrithaziri.libgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Endrit Haziri on 26.04.2018.
 */

public class ChangeLanguage extends Activity implements View.OnClickListener {

    /**
     * VARIABLE DECLARATION
     */
    private TextView txt_hello;
    private Button btn_en, btn_ru, btn_fr, btn_de;
    private Locale myLocale;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homepage = new Intent (ChangeLanguage.this,Home.class);
                    ChangeLanguage.this.startActivity(homepage);
                    return true;
            }
            return false;
        }
    };

    /**
     * ON CREATE METHOD
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        this.btn_en = (Button)findViewById(R.id.btn_en);
        this.btn_fr = (Button)findViewById(R.id.btn_fr);


        this.btn_en.setOnClickListener(this);
        this.btn_fr.setOnClickListener(this);


        loadLocale();
        /**
         * NAVIGATION BAR
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    private void updateTexts() {

        btn_en.setText(R.string.btn_en);
        btn_fr.setText(R.string.btn_fr);

    }

    public void onClick(View v) {
        String lang = "en";
        switch (v.getId()) {
            case R.id.btn_en:
                lang = "en";
                break;
            case R.id.btn_fr:
                lang = "fr";
                break;
            default:
                break;
        }
        changeLang(lang);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


