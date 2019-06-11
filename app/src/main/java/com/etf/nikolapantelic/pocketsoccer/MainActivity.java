package com.etf.nikolapantelic.pocketsoccer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.etf.nikolapantelic.pocketsoccer.newgame.NewGameActivity;
import com.etf.nikolapantelic.pocketsoccer.scores.AllScoresActivity;
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsActivity;
import com.etf.nikolapantelic.pocketsoccer.settings.GamePreferencesHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button continueButton = findViewById(R.id.button_continue);
        Button newGameButton = findViewById(R.id.button_new_game);
        Button scoresButton = findViewById(R.id.button_scores);
        Button settingsButton = findViewById(R.id.button_settings);

        // init game preferences
        GamePreferencesHelper.getInstance(this);

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

        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickScores(v);
            }
        });
    }

    private void onClickScores(View v) {
        Intent intent = new Intent(this, AllScoresActivity.class);
        startActivity(intent);
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
