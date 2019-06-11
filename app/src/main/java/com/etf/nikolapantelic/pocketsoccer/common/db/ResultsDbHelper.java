package com.etf.nikolapantelic.pocketsoccer.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.etf.nikolapantelic.pocketsoccer.R;

import static com.etf.nikolapantelic.pocketsoccer.common.db.ResultsContract.ResultsEntry;

public class ResultsDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ResultsEntry.TABLE_NAME + " (" +
                    ResultsEntry._ID + " INTEGER PRIMARY KEY," +
                    ResultsEntry.COLUMN_PLAYER1 + " TEXT," +
                    ResultsEntry.COLUMN_PLAYER2 + " TEXT," +
                    ResultsEntry.COLUMN_PLAYER1_WINS + " INT," +
                    ResultsEntry.COLUMN_PLAYER2_WINS + " INT," +
                    ResultsEntry.COLUMN_PLAYERS_ID + " TEXT)" ;

    public ResultsDbHelper(Context context) {
        super(
                context,
                context.getResources().getString(R.string.database_name),
                null,
                context.getResources().getInteger(R.integer.database_version)
        );
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
