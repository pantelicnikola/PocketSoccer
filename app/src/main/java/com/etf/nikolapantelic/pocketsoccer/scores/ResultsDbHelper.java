package com.etf.nikolapantelic.pocketsoccer.scores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.etf.nikolapantelic.pocketsoccer.scores.ResultsContract.ResultsEntry;

public class ResultsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PocketSoccer.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ResultsEntry.TABLE_NAME + " (" +
                    ResultsEntry._ID + " INTEGER PRIMARY KEY," +
                    ResultsEntry.COLUMN_PLAYER1 + " TEXT," +
                    ResultsEntry.COLUMN_PLAYER2 + " TEXT," +
                    ResultsEntry.COLUMN_PLAYER1_WINS + " INT," +
                    ResultsEntry.COLUMN_PLAYER1_WINS + " INT," +
                    ResultsEntry.COLUMN_PLAYERS_ID + " TEXT)" ;

    public ResultsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ResultsEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
