package com.etf.nikolapantelic.pocketsoccer.scores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.newgame.CountryClickListener;

public class AllScoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CountryClickListener countryClickListener;
    TextView textViewScore;

    public AllScoresViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewScore = itemView.findViewById(R.id.textViewScore);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        countryClickListener.onClick(v, getAdapterPosition());
    }

    public void setCountryClickListener(CountryClickListener countryClickListener) {
        this.countryClickListener = countryClickListener;
    }
}
