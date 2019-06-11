package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;

public class MutualScoresViewHolder extends RecyclerView.ViewHolder {

    private TextView score;

    public MutualScoresViewHolder(@NonNull View itemView) {
        super(itemView);
        score = itemView.findViewById(R.id.textViewScore);
    }

    public TextView getScore() {
        return score;
    }
}
