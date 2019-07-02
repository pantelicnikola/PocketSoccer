package com.etf.nikolapantelic.pocketsoccer.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import com.etf.nikolapantelic.pocketsoccer.common.db.model.ResultModel;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.ArrayList;
import java.util.List;

public class ResultsDAO {

    public static void replace(Context context) {

        ResultsDbHelper dbHelper = new ResultsDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String resultId;
        String player1Name = Game.player1.getName();
        String player2Name = Game.player2.getName();
        String playersId = Game.getPlayersId();
        int player1Wins = 0;
        int player2Wins = 0;

        String[] projection = {
                BaseColumns._ID,
                ResultsContract.ResultsEntry.COLUMN_PLAYER1_WINS,
                ResultsContract.ResultsEntry.COLUMN_PLAYER2_WINS
        };

        String selection = ResultsContract.ResultsEntry.COLUMN_PLAYERS_ID + " = ?";
        String[] selectionArgs = { playersId };

        Cursor cursor = db.query(
                ResultsContract.ResultsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        ContentValues values2 = new ContentValues();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            player1Wins = cursor.getInt(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYER1_WINS));
            player2Wins = cursor.getInt(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYER2_WINS));
            resultId = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
            values2.put(BaseColumns._ID, resultId);
        }

        cursor.close();

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

        values2.put(ResultsContract.ResultsEntry.COLUMN_PLAYER1, player1Name);
        values2.put(ResultsContract.ResultsEntry.COLUMN_PLAYER2, player2Name);
        values2.put(ResultsContract.ResultsEntry.COLUMN_PLAYERS_ID, playersId);
        values2.put(ResultsContract.ResultsEntry.COLUMN_PLAYER1_WINS, player1Wins);
        values2.put(ResultsContract.ResultsEntry.COLUMN_PLAYER2_WINS, player2Wins);

        db.replace(ResultsContract.ResultsEntry.TABLE_NAME, null, values2);
    }

    public static List<ResultModel> getAll(Context context) {
        ResultsDbHelper dbHelper = new ResultsDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ResultModel> allScores = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                ResultsContract.ResultsEntry.COLUMN_PLAYER1_WINS,
                ResultsContract.ResultsEntry.COLUMN_PLAYER2_WINS,
                ResultsContract.ResultsEntry.COLUMN_PLAYER1,
                ResultsContract.ResultsEntry.COLUMN_PLAYER2,
                ResultsContract.ResultsEntry.COLUMN_PLAYERS_ID
        };

        Cursor cursor = db.query(
                ResultsContract.ResultsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                ResultModel model = new ResultModel(
                        cursor.getString(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYER1)),
                        cursor.getString(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYER2)),
                        cursor.getString(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYER1_WINS)),
                        cursor.getString(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYER2_WINS)),
                        cursor.getString(cursor.getColumnIndex(ResultsContract.ResultsEntry.COLUMN_PLAYERS_ID))
                );
                allScores.add(model);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return allScores;
    }

    static public void deleteByPlayersId(Context context, String playersId) {
        ResultsDbHelper dbHelper = new ResultsDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = ResultsContract.ResultsEntry.COLUMN_PLAYERS_ID + " = ? ";
        String[] whereArguments = new String[]{playersId};
        db.delete(
                ResultsContract.ResultsEntry.TABLE_NAME,
                whereClause,
                whereArguments
        );
    }

}
