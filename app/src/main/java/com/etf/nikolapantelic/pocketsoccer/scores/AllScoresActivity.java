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
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsDAO;
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsDbHelper;
import com.etf.nikolapantelic.pocketsoccer.common.db.model.ResultModel;
import com.etf.nikolapantelic.pocketsoccer.scores.recyclerView.AllScoresAdapter;

import java.util.List;

public class AllScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scores);

        List<ResultModel> allScores = ResultsDAO.getAll(this);
        AllScoresAdapter adapter = new AllScoresAdapter(this, allScores);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAllScores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
