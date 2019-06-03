package com.etf.nikolapantelic.pocketsoccer.settings;

import android.content.SharedPreferences;

public class SettingsModel {

    private FieldType fieldType;
    private GameSpeed gameSpeed;
    private EndType endType;

    public SettingsModel() {
        this.fieldType = FieldType.initial();
        this.endType = EndType.initial();
        this.gameSpeed = GameSpeed.initial();
    }

    public SettingsModel(FieldType fieldType, GameSpeed gameSpeed, EndType endType) {
        this.fieldType = fieldType;
        this.gameSpeed = gameSpeed;
        this.endType = endType;
    }
//    public SettingsModel() {
//        this.fieldType = FieldType.GREEN;
//        this.gameSpeed = GameSpeed.MEDIUM;
//        this.endType = EndType.GOALS;

//    }


    public SettingsModel(SharedPreferences preferences) {

    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public GameSpeed getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(GameSpeed gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public EndType getEndType() {
        return endType;
    }

    public void setEndType(EndType endType) {
        this.endType = endType;
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

