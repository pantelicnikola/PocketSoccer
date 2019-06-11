package com.etf.nikolapantelic.pocketsoccer.scores;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.etf.nikolapantelic.pocketsoccer.common.db.GamesContract.GamesEntry;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.db.GamesDbHelper;
import com.etf.nikolapantelic.pocketsoccer.scores.recyclerView.MutualScoresAdapter;

import java.util.ArrayList;
import java.util.List;

public class MutualScoresActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_scores);

        Intent intent = getIntent();
        String playersId = intent.getStringExtra("playersId");

        List<String> mutualScores = getGamesByPlayersId(playersId);
        MutualScoresAdapter adapter = new MutualScoresAdapter(mutualScores);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMutualScores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private List<String> getGamesByPlayersId(String playersId) {
        GamesDbHelper dbHelper = new GamesDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> mutualScores = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                GamesEntry.COLUMN_PLAYER1,
                GamesEntry.COLUMN_PLAYER2,
                GamesEntry.COLUMN_RESULT,
                GamesEntry.COLUMN_TIME
        };

        String selection = GamesEntry.COLUMN_PLAYERS_ID + " = ?";
        String[] selectionArgs = { playersId };

        Cursor cursor = db.query(
                GamesEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                StringBuilder scoreString = new StringBuilder();
                scoreString.append(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_PLAYER1)));
                scoreString.append(" ");
                scoreString.append(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_PLAYER2)));
                scoreString.append(" ");
                scoreString.append(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_RESULT)));
                scoreString.append(" ");
                scoreString.append(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_TIME)));

//                MutualScoreModel model = new MutualScoreModel(
//                    cursor.getString(cursor.getColumnIndex(BaseColumns._ID)),
//                    cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_PLAYER1)),
//                    cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_PLAYER2)),
//                    cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_RESULT)),
//                    cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_TIME))
//                );
                mutualScores.add(scoreString.toString());
            } while (cursor.moveToNext());
        }

        return mutualScores;
    }
}
