package com.etf.nikolapantelic.pocketsoccer.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.etf.nikolapantelic.pocketsoccer.R;

import static com.etf.nikolapantelic.pocketsoccer.common.db.GamesContract.GamesEntry;

public class GamesDbHelper extends SQLiteOpenHelper {

    public GamesDbHelper(Context context) {
        super(
                context,
                context.getResources().getString(R.string.database_name),
                null,
                context.getResources().getInteger(R.integer.database_version)
        );
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
