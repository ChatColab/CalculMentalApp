package com.example.calculmentalapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.calculmentalapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private boolean isLanguageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        mediaPlayer = MediaPlayer.create(this, R.raw.carte_aux_adieux);
        mediaPlayer.setLooping(true);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isMusicEnabled = sharedPreferences.getBoolean("MusicEnabled", true);
        if (isMusicEnabled) {
            mediaPlayer.start();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        getSupportActionBar().setTitle(R.string.app_name);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            showAboutPopup();
            return true;
        }

        if (id == R.id.action_settings) {
            showSettingsPopup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAboutPopup(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_about, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(findViewById(R.id.toolbar), Gravity.TOP | Gravity.START, 0, (getSupportActionBar().getHeight()) + 100);
    }

    private void showSettingsPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_settings, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // MUSIC
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isMusicEnabled = sharedPreferences.getBoolean("MusicEnabled", true);
        SwitchCompat switchMusic = popupView.findViewById(R.id.switch_music);
        switchMusic.setChecked(isMusicEnabled);
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
            editor.putBoolean("MusicEnabled", isChecked);
            editor.apply();
            if (isChecked) {
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
            }
        });

        // NIGHT MODE
        SwitchCompat switchNightMode = popupView.findViewById(R.id.switch_night_mode);
        switchNightMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        switchNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            popupWindow.dismiss();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // CHANGE LANGUAGE
        Spinner switchLanguage = popupView.findViewById(R.id.spinner_language);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switchLanguage.setAdapter(adapter);
        switchLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();
                switch (selectedLang) {
                    case "Français":
                        setLocale("fr");
                        break;
                    case "English":
                        setLocale("en");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        // Afficher le PopupWindow sous la barre d'outils
        popupWindow.showAtLocation(findViewById(R.id.toolbar), Gravity.TOP | Gravity.START, 0, (getSupportActionBar().getHeight()) + 100);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (isLanguageChanged) {
                    recreate();
                    isLanguageChanged = false;
                }
                SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
                boolean isMusicEnabled = sharedPreferences.getBoolean("MusicEnabled", true);
                if (isMusicEnabled) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
            }
        });

    }

    public void setLocale(String lang) {
        String currentLang = getSharedPreferences("Settings", MODE_PRIVATE).getString("My_Lang", "");
        if (!currentLang.equals(lang)) {
            isLanguageChanged = true;
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            // Save data to shared preferences
            SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
            editor.putString("My_Lang", lang);
            editor.apply();
        }
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}