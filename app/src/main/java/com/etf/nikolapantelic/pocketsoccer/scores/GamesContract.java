package com.etf.nikolapantelic.pocketsoccer.scores;

import android.provider.BaseColumns;

public class GamesContract {

    private GamesContract(){}

    public static class GamesEntry implements BaseColumns {
        public static final String TABLE_NAME = "games";
        public static final String COLUMN_PLAYER1 = "player1";
        public static final String COLUMN_PLAYER2 = "player2";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_RESULT = "result";
        public static final String COLUMN_PLAYERS_ID = "playersId";

    }
}
