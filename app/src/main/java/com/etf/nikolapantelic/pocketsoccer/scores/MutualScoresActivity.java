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
import com.etf.nikolapantelic.pocketsoccer.common.db.GamesDAO;
import com.etf.nikolapantelic.pocketsoccer.common.db.GamesDbHelper;
import com.etf.nikolapantelic.pocketsoccer.common.db.model.GameModel;
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

        List<GameModel> mutualScores = GamesDAO.getGamesByPlayersId(this, playersId);
        MutualScoresAdapter adapter = new MutualScoresAdapter(mutualScores);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMutualScores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
