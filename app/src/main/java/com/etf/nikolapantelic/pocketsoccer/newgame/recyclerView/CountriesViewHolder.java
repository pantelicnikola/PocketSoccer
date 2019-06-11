package com.etf.nikolapantelic.pocketsoccer.newgame.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.RecyclerViewClickListener;

public class CountriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    public ImageView imageView;

    RecyclerViewClickListener recyclerViewClickListener;

    public CountriesViewHolder(@NonNull final View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewScore);
        imageView = itemView.findViewById(R.id.image_view_country_flag);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewClickListener.onClick(v, getAdapterPosition());
    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
    }
}
