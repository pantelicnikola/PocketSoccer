package com.etf.nikolapantelic.pocketsoccer.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper;
import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper.EndType;
import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper.FieldType;
import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper.GameSpeed;

import com.etf.nikolapantelic.pocketsoccer.R;

public class SettingsActivity extends AppCompatActivity {

    private GamePreferencesHelper model;

    private RadioGroup fieldTypeGroup;
    private RadioGroup gameSpeedGroup;
    private RadioGroup endGameGroup;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fieldTypeGroup = findViewById(R.id.radioGroupField);
        gameSpeedGroup = findViewById(R.id.radioGroupSpeed);
        endGameGroup = findViewById(R.id.radioGroupEnd);
        resetButton = findViewById(R.id.buttonResetSettings);

        model = GamePreferencesHelper.getInstance(this);
        populateView();

        fieldTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                model.setFieldType(getFieldType(checkedId));
            }
        });

        gameSpeedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                model.setGameSpeed(getGameSpeed(checkedId));
            }
        });

        endGameGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                model.setEndType(getEndType(checkedId));
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.reset();
                populateView();
            }
        });
    }

    private void populateView() {
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
            default:
                return null;
        }
    }

    private GameSpeed getGameSpeed(int id) {
        switch (id) {
            case R.id.radioButtonSlow:
                return GameSpeed.SLOW;
            case R.id.radioButtonMedium:
                return GameSpeed.MEDIUM;
            case R.id.radioButtonFast:
                return GameSpeed.FAST;
            default:
                return null;
        }
    }

    private EndType getEndType(int id) {
        switch (id) {
            case R.id.radioButtonGoals:
                return EndType.GOALS;
            case R.id.radioButtonTime:
                return EndType.TIME;
            default:
                return null;
        }
    }
}
