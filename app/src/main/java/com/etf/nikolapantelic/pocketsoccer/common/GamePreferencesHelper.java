package com.etf.nikolapantelic.pocketsoccer.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.etf.nikolapantelic.pocketsoccer.R;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;

public class GamePreferencesHelper {

    private static final String KEY_FIELD_TYPE = "field_type";
    private static final String KEY_GAME_SPEED = "game_speed";
    private static final String KEY_END_TYPE = "end_type";

    private static Resources resources ;
    private static SharedPreferences preferences;

    private static GamePreferencesHelper instance = null;

    private GamePreferencesHelper(@NotNull Context context) {
        resources = context.getResources();
        preferences = context.getSharedPreferences(resources.getString(R.string.game_preferences), MODE_PRIVATE);

        if (!preferences.contains("initialized")) {
            initialize();
        }
    }

    public static GamePreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GamePreferencesHelper(context);
        }
        return instance;
    }

    private void initialize() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("initialized", true);
        editor.apply();
        reset();
    }

    public void reset() {
        setFieldType(FieldType.initial());
        setGameSpeed(GameSpeed.initial());
        setEndType(EndType.initial());
    }

    public FieldType getFieldType() {
        return FieldType.valueOf(preferences.getString(KEY_FIELD_TYPE, null));
    }

    public void setFieldType(FieldType fieldType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_FIELD_TYPE, fieldType.toString());
        editor.apply();
    }

    public GameSpeed getGameSpeed() {
        return GameSpeed.valueOf(preferences.getString(KEY_GAME_SPEED, null));
    }

    public void setGameSpeed(GameSpeed gameSpeed) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_GAME_SPEED, gameSpeed.toString());
        editor.apply();
    }

    public EndType getEndType() {
        return EndType.valueOf(preferences.getString(KEY_END_TYPE, null));
    }

    public void setEndType(EndType endType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_END_TYPE, endType.toString());
        editor.apply();
    }

    public enum FieldType {
        GREEN, YELLOW, GREY;
        static FieldType initial() {
            return GREEN;
        }
    }
    public enum GameSpeed {
        MEDIUM, SLOW, FAST;
        static GameSpeed initial() {
            return MEDIUM;
        }
    }
    public enum EndType {
        GOALS, TIME;
        static EndType initial() {
            return GOALS;
        }
    }
}

