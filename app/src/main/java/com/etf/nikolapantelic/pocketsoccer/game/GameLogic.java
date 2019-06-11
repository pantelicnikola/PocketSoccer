package com.etf.nikolapantelic.pocketsoccer.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;
import com.etf.nikolapantelic.pocketsoccer.common.db.GamesDbHelper;
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsDbHelper;
import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper;

import static com.etf.nikolapantelic.pocketsoccer.common.db.GamesContract.GamesEntry;
import static com.etf.nikolapantelic.pocketsoccer.common.db.ResultsContract.ResultsEntry;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private static Timer turnTimer = new Timer();
    private static GamePreferencesHelper.EndType endType;
    public static boolean timerFinished = false;

    public static void changeTurn() {
        setTurn(Game.waiting);
        turnTimer.cancel();
        turnTimer = new Timer();
        turnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeTurn();
            }
        }, 5000, 5000);
    }

    private static void activatePlayer(Game.Turn turn) {
        for (Ball ball : Game.getPlayerByTurn(turn).getBalls()) {
            ball.enable();
        }
    }

    private static void deactivatePlayer(Game.Turn turn) {
        for (Ball ball : Game.getPlayerByTurn(turn).getBalls()) {
            ball.disable();
        }
    }

    public static void stopGame() {
        turnTimer.cancel();
        for (Ball b : Game.getAllBalls()) {
            GamePhysics.forceStop(b);
        }
        deactivatePlayer(Game.Turn.PLAYER1);
        deactivatePlayer(Game.Turn.PLAYER2);
    }

    public static boolean goalOccurred(float leftPostX, float rightPostX) {
        Ball football = Game.football;
        float footballX = football.calculateCenterX();
//        float leftPostX1 =  GameActivity.getContext().getResources().getFraction(R.fraction.left_post_fraction, GameActivity.getWindowWidth(), 1);
//        float rightPostX1 = GameActivity.getContext().getResources().getFraction(R.fraction.right_post_fraction, GameActivity.getWindowWidth(), 1);
        if (footballX > leftPostX && footballX < rightPostX) {
            if (football.isHitTop()) {
                stopGame();
                Game.goalsPlayer1++;
                GameLogic.setTurn(Game.Turn.PLAYER2);
                return true;
            }
            if (football.isHitBottom()) {
                stopGame();
                Game.goalsPlayer2++;
                GameLogic.setTurn(Game.Turn.PLAYER1);
                return true;
            }
        }
        return false;
    }

    public static void setEndType(GamePreferencesHelper.EndType endType) {
        GameLogic.endType = endType;
    }

    public static GamePreferencesHelper.EndType getEndType() {
        return endType;
    }

    public static void setTurn(Game.Turn player) {
        Game.setTurn(player);
        deactivatePlayer(Game.waiting);
        activatePlayer(Game.playing);
    }

    public static String getResultMessage() {
        return Game.goalsPlayer1 + " - " + Game.goalsPlayer2;
    }

    public static boolean isGameOver() {

        if (endType.equals(GamePreferencesHelper.EndType.GOALS)) {
            if (Game.goalsPlayer1 == 1 || Game.goalsPlayer2 == 1) {
                if (Game.goalsPlayer1 == 1) {
                    Game.winner = Game.Winner.ONE;
                } else {
                    Game.winner = Game.Winner.TWO;
                }
                return true;
            }
        } else {
            if (timerFinished) {
                if (Game.goalsPlayer1 > Game.goalsPlayer2) {
                    Game.winner = Game.Winner.ONE;
                } else if (Game.goalsPlayer1 < Game.goalsPlayer2) {
                    Game.winner = Game.Winner.TWO;
                } else {
                    Game.winner = Game.Winner.DRAW;
                }
                return true;
            }
        }
        return false;
    }

    public static String getWinnerMessage() {
        if (Game.winner.equals(Game.Winner.ONE)) {
            return "PLAYER 1 WINS";
        } else if (Game.winner.equals(Game.Winner.TWO)) {
            return "PLAYER 2 WINS";
        } else {
            return "IT IS DRAW";
        }
    }

    public static void persistGame(Context context) {

        GamesDbHelper gamesDbHelper = new GamesDbHelper(context);
        SQLiteDatabase gamesDb = gamesDbHelper.getWritableDatabase();

        ResultsDbHelper resultsDbHelper = new ResultsDbHelper(context);
        SQLiteDatabase resultsDb = resultsDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys

        String player1Name = Game.player1.getName();
        String player2Name = Game.player2.getName();
        String resultId = null;
        String playersId = Game.getPlayersId();
        Integer player1Wins = 0;
        Integer player2Wins = 0;

        ContentValues values = new ContentValues();
        values.put(GamesEntry.COLUMN_PLAYER1, player1Name);
        values.put(GamesEntry.COLUMN_PLAYER2, player2Name);
        values.put(GamesEntry.COLUMN_TIME, Calendar.getInstance().getTime().toString());
        values.put(GamesEntry.COLUMN_PLAYERS_ID, playersId);
        values.put(GamesEntry.COLUMN_RESULT, GameLogic.getResultMessage());

//        RESTART TABLES
//        gamesDb.execSQL("delete from " + GamesEntry.TABLE_NAME);
//        resultsDb.execSQL("delete from " + ResultsEntry.TABLE_NAME);

        // Insert the new row, returning the primary key value of the new row
        gamesDb.insert(GamesEntry.TABLE_NAME, null, values);


        ///////////////////////////////////////////////////////////////////

        String[] projection = {
                BaseColumns._ID,
                ResultsEntry.COLUMN_PLAYER1_WINS,
                ResultsEntry.COLUMN_PLAYER2_WINS
        };

// Filter results WHERE "title" = 'My Title'
        String selection = ResultsEntry.COLUMN_PLAYERS_ID + " = ?";
        String[] selectionArgs = { playersId };

// How you want the results sorted in the resulting Cursor
        String sortOrder = BaseColumns._ID + " DESC";

        Cursor cursor = resultsDb.query(
                ResultsEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ContentValues values2 = new ContentValues();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            player1Wins = cursor.getInt(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER1_WINS));
            player2Wins = cursor.getInt(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER2_WINS));
            resultId = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
            values2.put(BaseColumns._ID, resultId);
        }

        switch (Game.winner) {
            case DRAW:
                player2Wins++;
                player1Wins++;
                break;
            case TWO:
                player2Wins++;
                break;
            case ONE:
                player1Wins++;
                break;
        }

        values2.put(ResultsEntry.COLUMN_PLAYER1, player1Name);
        values2.put(ResultsEntry.COLUMN_PLAYER2, player2Name);
        values2.put(ResultsEntry.COLUMN_PLAYERS_ID, playersId);
        values2.put(ResultsEntry.COLUMN_PLAYER1_WINS, player1Wins);
        values2.put(ResultsEntry.COLUMN_PLAYER2_WINS, player2Wins);

        resultsDb.replace(ResultsEntry.TABLE_NAME, null, values2);
    }
}
