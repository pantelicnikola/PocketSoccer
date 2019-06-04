package com.etf.nikolapantelic.pocketsoccer.settings;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.etf.nikolapantelic.pocketsoccer.settings.SettingsModel.EndType;
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsModel.FieldType;
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsModel.GameSpeed;

import com.etf.nikolapantelic.pocketsoccer.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SettingsModel model;

    private RadioGroup fieldTypeGroup;
    private RadioGroup gameSpeedGroup;
    private RadioGroup endGameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fieldTypeGroup = findViewById(R.id.radioGroupField);
        gameSpeedGroup = findViewById(R.id.radioGroupSpeed);
        endGameGroup = findViewById(R.id.radioGroupEnd);

        preferences = getApplicationContext().getSharedPreferences(getString(R.string.game_preferences), MODE_PRIVATE);
        model = SettingsModel.getInstance(this);
        populateView(model);

        fieldTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FieldType fieldType = getFieldType(checkedId);
                model.setFieldType(fieldType);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.key_field_type), fieldType.toString());
                editor.apply();
            }
        });

        gameSpeedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                GameSpeed gameSpeed = getGameSpeed(checkedId);
                model.setGameSpeed(gameSpeed);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.key_game_speed), gameSpeed.toString());
                editor.apply();
            }
        });

        endGameGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EndType endType = getEndType(checkedId);
                model.setEndType(endType);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.key_end_type), endType.toString());
                editor.apply();
            }
        });
    }

    private void populateView(SettingsModel model) {
        switch (model.getFieldType()) {
            case GREEN:
                fieldTypeGroup.check(R.id.radioButtonGreen);
                break;
            case YELLOW:
                fieldTypeGroup.check(R.id.radioButtonYellow);
                break;
            case GREY:
                fieldTypeGroup.check(R.id.radioButtonGrey);
                break;
        }

        switch (model.getGameSpeed()) {
            case SLOW:
                gameSpeedGroup.check(R.id.radioButtonSlow);
                break;
            case MEDIUM:
                gameSpeedGroup.check(R.id.radioButtonMedium);
                break;
            case FAST:
                gameSpeedGroup.check(R.id.radioButtonFast);
                break;
        }

        switch (model.getEndType()) {
            case TIME:
                endGameGroup.check(R.id.radioButtonTime);
                break;
            case GOALS:
                endGameGroup.check(R.id.radioButtonGoals);
                break;
        }
    }

    private FieldType getFieldType(int id) {
        switch (id) {
            case R.id.radioButtonGreen:
                return FieldType.GREEN;
            case R.id.radioButtonYellow:
                return FieldType.YELLOW;
            case R.id.radioButtonGrey:
                return FieldType.GREY;
        }
        return null;
    }

    private GameSpeed getGameSpeed(int id) {
        switch (id) {
            case R.id.radioButtonSlow:
                return GameSpeed.SLOW;
            case R.id.radioButtonMedium:
                return GameSpeed.MEDIUM;
            case R.id.radioButtonFast:
                return GameSpeed.FAST;
        }
        return null;
    }

    private EndType getEndType(int id) {
        switch (id) {
            case R.id.radioButtonGoals:
                return EndType.GOALS;
            case R.id.radioButtonTime:
                return EndType.TIME;
        }
        return null;
    }


}
