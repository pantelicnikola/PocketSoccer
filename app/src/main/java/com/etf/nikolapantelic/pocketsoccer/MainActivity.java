package com.etf.nikolapantelic.pocketsoccer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.etf.nikolapantelic.pocketsoccer.newgame.NewGameActivity;
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsActivity;
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button continueButton = findViewById(R.id.button_continue);
        Button newGameButton = findViewById(R.id.button_new_game);
        Button scoresButton = findViewById(R.id.button_scores);
        Button settingsButton = findViewById(R.id.button_settings);

        initGamePreferences();

        if (savedInstanceState == null) {
            continueButton.setEnabled(false);
        } else {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickContinue(v);
                }
            });
        }

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSettings(v);
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewGame(v);
            }
        });
    }

    private void initGamePreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.game_preferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!preferences.contains("initialized")) {
            editor.putBoolean("initialized", true);
            SettingsModel settingsModel = new SettingsModel();
            editor.putString(getString(R.string.key_field_type), settingsModel.getFieldType().toString());
            editor.putString(getString(R.string.key_game_speed), settingsModel.getGameSpeed().toString());
            editor.putString(getString(R.string.key_end_type), settingsModel.getEndType().toString());
            editor.apply();
        }
    }

    public void onClickContinue(View view) {
        // nesto
    }



    public void onClickNewGame(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    private void onClickSettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
