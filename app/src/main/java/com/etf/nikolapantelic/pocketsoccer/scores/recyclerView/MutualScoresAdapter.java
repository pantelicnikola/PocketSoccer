package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.db.model.GameModel;

import java.util.List;

public class MutualScoresAdapter extends RecyclerView.Adapter<MutualScoresViewHolder> {

    List<GameModel> mutualScores;

    public MutualScoresAdapter(List<GameModel> mutualScores) {
        this.mutualScores = mutualScores;
    }

    @NonNull
    @Override
    public MutualScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.scores_row, viewGroup, false);
        return new MutualScoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MutualScoresViewHolder mutualScoresViewHolder, int i) {
        mutualScoresViewHolder.getScore().setText(mutualScores.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return mutualScores.size();
    }
}
