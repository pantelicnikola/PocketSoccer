package com.etf.nikolapantelic.pocketsoccer.scores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.newgame.CountriesViewHolder;
import com.etf.nikolapantelic.pocketsoccer.newgame.CountryClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.etf.nikolapantelic.pocketsoccer.scores.ResultsContract.ResultsEntry;

public class AllScoresAdapter extends RecyclerView.Adapter<AllScoresViewHolder> {

    List<String> allScoresRows;
    List<String> allScoresIds;

    public AllScoresAdapter(Context context) {
        allScoresRows = new ArrayList<>();
        allScoresIds = new ArrayList<>();
        ResultsDbHelper dbHelper = new ResultsDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ResultsEntry.COLUMN_PLAYER1_WINS,
                ResultsEntry.COLUMN_PLAYER2_WINS,
                ResultsEntry.COLUMN_PLAYER1,
                ResultsEntry.COLUMN_PLAYER2,
                ResultsEntry.COLUMN_PLAYERS_ID
        };

        Cursor cursor = db.query(
                ResultsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String player1 = cursor.getString(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER1));
                String player2 = cursor.getString(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER2));
                Integer player1Wins = cursor.getInt(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER1_WINS));
                Integer player2Wins = cursor.getInt(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER2_WINS));
                String playersId = cursor.getString(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYERS_ID));

                String rowContent = player1 + " " + player1Wins + " - " + player2Wins + " " + player2;
                allScoresRows.add(rowContent);

                allScoresIds.add(playersId);
            } while (cursor.moveToNext());
        }
    }

    @NonNull
    @Override
    public AllScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.all_scores_row, viewGroup, false);
        return new AllScoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllScoresViewHolder allScoresViewHolder, int i) {
        allScoresViewHolder.textViewScore.setText(allScoresRows.get(i));
        allScoresViewHolder.setCountryClickListener(new CountryClickListener() {
            @Override
            public void onClick(View view, int position) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return allScoresRows.size();
    }
}
