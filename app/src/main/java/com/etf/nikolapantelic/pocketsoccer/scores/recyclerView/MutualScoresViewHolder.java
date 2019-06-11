package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;

public class MutualScoresViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewResult;
    private TextView textViewTime;

    public MutualScoresViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewPlayer1 = itemView.findViewById(R.id.textViewPlayer1);
        textViewPlayer2 = itemView.findViewById(R.id.textViewPlayer2);
        textViewResult = itemView.findViewById(R.id.textViewResult);
        textViewTime = itemView.findViewById(R.id.textViewTime);
    }

    public TextView getTextViewPlayer1() {
        return textViewPlayer1;
    }

    public TextView getTextViewPlayer2() {
        return textViewPlayer2;
    }

    public TextView getTextViewResult() {
        return textViewResult;
    }

    public TextView getTextViewTime() {
        return textViewTime;
    }
}
