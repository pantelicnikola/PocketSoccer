package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.scores.MutualScoreModel;

import java.util.List;

import static com.etf.nikolapantelic.pocketsoccer.common.db.GamesContract.GamesEntry;

public class MutualScoresAdapter extends RecyclerView.Adapter<MutualScoresViewHolder> {

    private Cursor cursor;
    List<MutualScoreModel> mutualScoreModels;

    public MutualScoresAdapter(List<MutualScoreModel> mutualScoreModels) {
        this.mutualScoreModels = mutualScoreModels;
    }

    @NonNull
    @Override
    public MutualScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.mutual_scores_row, viewGroup, false);
        return new MutualScoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MutualScoresViewHolder mutualScoresViewHolder, int i) {

        MutualScoreModel model = mutualScoreModels.get(i);

        mutualScoresViewHolder.getTextViewPlayer1().setText(model.getPlayer1());
        mutualScoresViewHolder.getTextViewPlayer2().setText(model.getPlayer2());
        mutualScoresViewHolder.getTextViewResult().setText(model.getResult());
        mutualScoresViewHolder.getTextViewTime().setText(model.getTime());

        //        if (i == 0 || i >= getItemCount()) {
//            return;
//        }
//        cursor.move(i);
//        mutualScoresViewHolder.getTextViewPlayer1().setText(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_PLAYER1)));
//        mutualScoresViewHolder.getTextViewPlayer2().setText(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_PLAYER2)));
//        mutualScoresViewHolder.getTextViewResult().setText(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_RESULT)));
//        mutualScoresViewHolder.getTextViewTime().setText(cursor.getString(cursor.getColumnIndex(GamesEntry.COLUMN_TIME)));
    }

    @Override
    public int getItemCount() {
        return mutualScoreModels.size();
    }
}
