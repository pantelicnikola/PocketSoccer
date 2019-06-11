package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.RecyclerViewClickListener;

public class AllScoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewClickListener recyclerViewClickListener;
    private TextView textViewScore;

    public AllScoresViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewScore = itemView.findViewById(R.id.textViewScore);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewClickListener.onClick(v, getAdapterPosition());
    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener countryClickListener) {
        this.recyclerViewClickListener = countryClickListener;
    }

    public TextView getTextViewScore() {
        return textViewScore;
    }
}
