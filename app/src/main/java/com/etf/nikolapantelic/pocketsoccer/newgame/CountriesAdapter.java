package com.etf.nikolapantelic.pocketsoccer.newgame;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Game;
import com.etf.nikolapantelic.pocketsoccer.model.Country;

import java.util.ArrayList;
import java.util.List;



public class CountriesAdapter extends RecyclerView.Adapter<CountriesViewHolder> {

    private List<Country> countries;
    private Country selectedCountry;

    // default -> no row selected
    private int row_index = -1;

    public CountriesAdapter(Context context) {
        String[] countryNames =  context.getResources().getStringArray(R.array.country_names);
        TypedArray countryFlags = context.getResources().obtainTypedArray(R.array.country_flags);
        countries = new ArrayList<>();

        if (countryNames.length == countryFlags.length()) {
            for (int i = 0; i < countryNames.length; i++) {
                Country country = new Country(countryNames[i], countryFlags.getResourceId(i, -1));
                countries.add(country);
            }
        }
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.countries_row, viewGroup, false);
        return new CountriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CountriesViewHolder countriesViewHolder, final int i) {
        countriesViewHolder.textView.setText(countries.get(i).getName());
        countriesViewHolder.imageView.setImageResource(countries.get(i).getFlag());

        countriesViewHolder.setCountryClickListener(new CountryClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index = position;
                selectedCountry = countries.get(i);
                notifyDataSetChanged();
            }
        });

        if (row_index == i) {
            countriesViewHolder.itemView.setBackgroundColor(Color.BLUE);
            countriesViewHolder.textView.setTextColor(Color.WHITE);
        } else {
            countriesViewHolder.itemView.setBackgroundColor(Color.WHITE);
            countriesViewHolder.textView.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }
}