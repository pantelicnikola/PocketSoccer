package com.etf.nikolapantelic.pocketsoccer.scores;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsContract;
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsDbHelper;
import com.etf.nikolapantelic.pocketsoccer.scores.recyclerView.AllScoresAdapter;

public class AllScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scores);

        Cursor cursor = getAllResults();
        AllScoresAdapter adapter = new AllScoresAdapter(this, cursor);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAllScores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Cursor getAllResults() {

        ResultsDbHelper dbHelper = new ResultsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ResultsContract.ResultsEntry.COLUMN_PLAYER1_WINS,
                ResultsContract.ResultsEntry.COLUMN_PLAYER2_WINS,
                ResultsContract.ResultsEntry.COLUMN_PLAYER1,
                ResultsContract.ResultsEntry.COLUMN_PLAYER2,
                ResultsContract.ResultsEntry.COLUMN_PLAYERS_ID
        };

        return db.query(
                ResultsContract.ResultsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }
}
