package com.etf.nikolapantelic.pocketsoccer.newgame;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;

public class CountriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    public ImageView imageView;

    CountryClickListener countryClickListener;

    public CountriesViewHolder(@NonNull final View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_view_country_name);
        imageView = itemView.findViewById(R.id.image_view_country_flag);
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
