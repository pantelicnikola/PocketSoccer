package com.etf.nikolapantelic.pocketsoccer.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.etf.nikolapantelic.pocketsoccer.common.db.model.GameModel;
import com.etf.nikolapantelic.pocketsoccer.game.GameLogic;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GamesDAO {
    public static long insertCurrentGame(Context context) {

        GamesDbHelper gamesDbHelper = new GamesDbHelper(context);
        SQLiteDatabase db = gamesDbHelper.getWritableDatabase();

        String player1Name = Game.player1.getName();
        String player2Name = Game.player2.getName();
        String playersId = Game.getPlayersId();

        ContentValues values = new ContentValues();
        values.put(GamesContract.GamesEntry.COLUMN_PLAYER1, player1Name);
        values.put(GamesContract.GamesEntry.COLUMN_PLAYER2, player2Name);
        values.put(GamesContract.GamesEntry.COLUMN_TIME, Calendar.getInstance().getTime().toString());
        values.put(GamesContract.GamesEntry.COLUMN_PLAYERS_ID, playersId);
        values.put(GamesContract.GamesEntry.COLUMN_RESULT, GameLogic.getResultMessage());

        return db.insert(GamesContract.GamesEntry.TABLE_NAME, null, values);
    }



    public static List<GameModel> getGamesByPlayersId(Context context, String playersId) {
        GamesDbHelper dbHelper = new GamesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<GameModel> mutualScores = new ArrayList<>();


        String[] projection = {
                BaseColumns._ID,
                GamesContract.GamesEntry.COLUMN_PLAYER1,
                GamesContract.GamesEntry.COLUMN_PLAYER2,
                GamesContract.GamesEntry.COLUMN_RESULT,
                GamesContract.GamesEntry.COLUMN_TIME
        };

        String selection = GamesContract.GamesEntry.COLUMN_PLAYERS_ID + " = ?";
        String[] selectionArgs = { playersId };

        Cursor cursor = db.query(
                GamesContract.GamesEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                GameModel model = new GameModel(
                    cursor.getString(cursor.getColumnIndex(BaseColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_PLAYER1)),
                    cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_PLAYER2)),
                    cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_RESULT)),
                    cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_TIME))
                );
                mutualScores.add(model);
            } while (cursor.moveToNext());
        }

        return mutualScores;
    }
}
