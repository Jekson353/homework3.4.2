package com.samoylenko.homework332;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private Spinner language;
    public int appLang;
    public int applyMargin;
    private Spinner margin;
    private String loc;

    private SharedPreferences myNoteSharedPref;
    private static String MARGIN_TEXT = "select_margin";
    private static String LANG_TEXT = "select_language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        language = findViewById(R.id.languageSpinner);
        margin = findViewById(R.id.marginSpinner);

        myNoteSharedPref = getSharedPreferences("lang", MODE_PRIVATE);
        initSpinnerLanguages();
        initSpinnerMargin();


        findViewById(R.id.applyLang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Применено", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor myEditor = myNoteSharedPref.edit();
                int langTxt = appLang;
                int thmTxt = applyMargin;
                myEditor.putInt(MARGIN_TEXT, thmTxt);
                myEditor.putInt(LANG_TEXT, langTxt);
                myEditor.apply();
                switchLocale(appLang);
                switchTheme(applyMargin);
            }
        });
    }

    private void initSpinnerLanguages() {
        ArrayAdapter<CharSequence> adapterCountries = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapterCountries);
        getDateFromSharedPref(LANG_TEXT, language);
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                appLang = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initSpinnerMargin() {
        ArrayAdapter<CharSequence> adapterMargin = ArrayAdapter.createFromResource(this, R.array.margin, android.R.layout.simple_spinner_item);
        adapterMargin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        margin.setAdapter(adapterMargin);
        getDateFromSharedPref(MARGIN_TEXT, margin);
        margin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                applyMargin = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void switchLocale(int language){
        switch (language){
            case 0:
                loc = "ru";
                break;
            case 1:
                loc = "en";
                break;
            case 2:
                loc = "fr";
                break;
        }
        Locale locale = new Locale(loc);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void switchTheme (int id){
        switch (id)
        {
            case 0:
                Utils.changeToTheme(MainActivity.this, Utils.THEME_DEFAULT);
                break;
            case 1:
                Utils.changeToTheme(MainActivity.this, Utils.THEME_MARGIN1);
                break;
            case 2:
                Utils.changeToTheme(MainActivity.this, Utils.THEME_MARGIN3);
                break;
            case 3:
                Utils.changeToTheme(MainActivity.this, Utils.THEME_MARGIN10);
                break;
        }
    }

    private void getDateFromSharedPref(String txt, Spinner val){
        int noteTxt = myNoteSharedPref.getInt(txt, 0);
        val.setSelection(noteTxt);
    }

}
