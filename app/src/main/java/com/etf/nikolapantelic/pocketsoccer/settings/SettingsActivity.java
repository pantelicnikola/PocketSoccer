package com.etf.nikolapantelic.pocketsoccer.settings;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.etf.nikolapantelic.pocketsoccer.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SettingsModel model;

    private final String KEY_FIELD_TYPE = "field_type";
    private final String KEY_GAME_SPEED = "game_speed";
    private final String KEY_END_TYPE = "end_type";

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

        preferences = getApplicationContext().getSharedPreferences("GamePreferences", MODE_PRIVATE);
        model = loadModel(preferences);

        unloadModel(model);

        fieldTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_FIELD_TYPE, getFieldType(checkedId).toString());
                editor.apply();
                model = loadModel(preferences);
            }
        });

        gameSpeedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_GAME_SPEED, getGameSpeed(checkedId).toString());
                editor.apply();
                model = loadModel(preferences);
            }
        });

        endGameGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_END_TYPE, getEndType(checkedId).toString());
                editor.apply();
                model = loadModel(preferences);
            }
        });

    }

    private SettingsModel loadModel(SharedPreferences preferences) {
        SettingsModel settingsModel;
        if(preferences.contains("initialized")){
            settingsModel = new SettingsModel(
                    SettingsModel.FieldType.valueOf(preferences.getString(KEY_FIELD_TYPE, null)),
                    SettingsModel.GameSpeed.valueOf(preferences.getString(KEY_GAME_SPEED, null)),
                    SettingsModel.EndType.valueOf(preferences.getString(KEY_END_TYPE, null))
            );
        } else {
            settingsModel = new SettingsModel();
            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("initialized", true);
            editor.putString(KEY_FIELD_TYPE, settingsModel.getFieldType().toString());
            editor.putString(KEY_GAME_SPEED, settingsModel.getGameSpeed().toString());
            editor.putString(KEY_END_TYPE, settingsModel.getEndType().toString());

            editor.apply();
        }
        return settingsModel;
    }

    private void unloadModel(SettingsModel model) {
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

    private SettingsModel.FieldType getFieldType(int id) {
        switch (id) {
            case R.id.radioButtonGreen:
                return SettingsModel.FieldType.GREEN;
            case R.id.radioButtonYellow:
                return SettingsModel.FieldType.YELLOW;
            case R.id.radioButtonGrey:
                return SettingsModel.FieldType.GREY;
            default:
                return null;
        }
    }

    private SettingsModel.GameSpeed getGameSpeed(int id) {
        switch (id) {
            case R.id.radioButtonSlow:
                return SettingsModel.GameSpeed.SLOW;
            case R.id.radioButtonMedium:
                return SettingsModel.GameSpeed.MEDIUM;
            case R.id.radioButtonFast:
                return SettingsModel.GameSpeed.FAST;
            default:
                return null;
        }
    }

    private SettingsModel.EndType getEndType(int id) {
        switch (id) {
            case R.id.radioButtonGoals:
                return SettingsModel.EndType.GOALS;
            case R.id.radioButtonTime:
                return SettingsModel.EndType.TIME;
            default:
                return null;
        }
    }


}
