package com.etf.nikolapantelic.pocketsoccer.scores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.etf.nikolapantelic.pocketsoccer.MainActivity;
import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.db.GamesDAO;
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsDAO;
import com.etf.nikolapantelic.pocketsoccer.common.db.model.GameModel;
import com.etf.nikolapantelic.pocketsoccer.scores.recyclerView.MutualScoresAdapter;

import java.util.List;

public class MutualScoresActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_scores);

        final String playersId = getIntent().getStringExtra("playersId");

        List<GameModel> mutualScores = GamesDAO.getGamesByPlayersId(this, playersId);
        MutualScoresAdapter adapter = new MutualScoresAdapter(mutualScores);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMutualScores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button mainMenuButton = findViewById(R.id.buttonMainMenu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button resetButton = findViewById(R.id.buttonResetScores);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamesDAO.deleteByPlayersId(getApplicationContext(), playersId);
                ResultsDAO.deleteByPlayersId(getApplicationContext(), playersId);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
