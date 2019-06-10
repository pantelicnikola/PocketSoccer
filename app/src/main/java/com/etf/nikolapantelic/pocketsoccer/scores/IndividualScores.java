package com.etf.nikolapantelic.pocketsoccer.scores;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.etf.nikolapantelic.pocketsoccer.scores.GamesContract.GamesEntry;

import com.etf.nikolapantelic.pocketsoccer.R;

public class IndividualScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_scores);


    }
}
