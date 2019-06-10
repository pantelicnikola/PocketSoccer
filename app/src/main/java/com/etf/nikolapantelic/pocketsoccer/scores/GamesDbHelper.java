package com.etf.nikolapantelic.pocketsoccer.scores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.etf.nikolapantelic.pocketsoccer.scores.GamesContract.GamesEntry;

public class GamesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "PocketSoccer.db";

    public GamesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GamesEntry.TABLE_NAME + " (" +
                    GamesEntry._ID + " INTEGER PRIMARY KEY," +
                    GamesEntry.COLUMN_PLAYER1 + " TEXT," +
                    GamesEntry.COLUMN_PLAYER2 + " TEXT," +
                    GamesEntry.COLUMN_TIME + " TEXT," +
                    GamesEntry.COLUMN_RESULT + " TEXT," +
                    GamesEntry.COLUMN_PLAYERS_ID + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GamesEntry.TABLE_NAME;

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
