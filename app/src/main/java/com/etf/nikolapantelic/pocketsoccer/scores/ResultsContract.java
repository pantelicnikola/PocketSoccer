package com.etf.nikolapantelic.pocketsoccer.scores;

import android.provider.BaseColumns;

public class ResultsContract {
    private ResultsContract(){}

    public static class ResultsEntry implements BaseColumns {
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_PLAYERS_ID = "playersId";
        public static final String COLUMN_PLAYER1 = "player1";
        public static final String COLUMN_PLAYER2 = "player2";
        public static final String COLUMN_PLAYER1_WINS = "player1Wins";
        public static final String COLUMN_PLAYER2_WINS = "player2Wins";

    }
}
