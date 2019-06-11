package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.RecyclerViewClickListener;
import com.etf.nikolapantelic.pocketsoccer.scores.MutualScoresActivity;

import java.util.ArrayList;
import java.util.List;

import static com.etf.nikolapantelic.pocketsoccer.common.db.ResultsContract.ResultsEntry;

public class AllScoresAdapter extends RecyclerView.Adapter<AllScoresViewHolder> {

    private List<String> allScoresRows;
    private List<String> allPlayersIds;
    private Context context;

    public AllScoresAdapter(Context context, Cursor cursor) {

        this.context = context;
        // tuple?
        allScoresRows = new ArrayList<>();
        allPlayersIds = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String player1 = cursor.getString(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER1));
                String player2 = cursor.getString(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER2));
                Integer player1Wins = cursor.getInt(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER1_WINS));
                Integer player2Wins = cursor.getInt(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYER2_WINS));
                String playersId = cursor.getString(cursor.getColumnIndex(ResultsEntry.COLUMN_PLAYERS_ID));

                String rowContent = player1 + " " + player1Wins + " - " + player2Wins + " " + player2;
                allScoresRows.add(rowContent);

                allPlayersIds.add(playersId);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @NonNull
    @Override
    public AllScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.scores_row, viewGroup, false);
        return new AllScoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllScoresViewHolder allScoresViewHolder, int i) {
        allScoresViewHolder.getTextViewScore().setText(allScoresRows.get(i));
        allScoresViewHolder.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, MutualScoresActivity.class);
                intent.putExtra("playersId", allPlayersIds.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allScoresRows.size();
    }
}
