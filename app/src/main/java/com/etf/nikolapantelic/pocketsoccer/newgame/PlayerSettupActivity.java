package com.etf.nikolapantelic.pocketsoccer.newgame;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.game.GameActivity;
import com.etf.nikolapantelic.pocketsoccer.model.Game;
import com.etf.nikolapantelic.pocketsoccer.model.Country;
import com.etf.nikolapantelic.pocketsoccer.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSettupActivity extends AppCompatActivity {

    private EditText playerName;
    private Country selectedCountry;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_settup);

        final CountriesAdapter adapter = new CountriesAdapter(this);

        actionButton = findViewById(R.id.button_new_game_settup);
        actionButton.setEnabled(false);

        playerName = findViewById(R.id.edit_text_player_name);
        playerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (selectedCountry != null && !s.toString().equals("")) {
                    actionButton.setEnabled(true);
                } else {
                    actionButton.setEnabled(false);
                }
            }
        });

        // button actions
        if (Game.opponent == Game.OpponentType.PVE || Game.player1 != null) {
            actionButton.setText("Play");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPlay();
                }
            });
        } else {
            actionButton.setText("Continue");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickContinue();
                }
            });
        }

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                selectedCountry = adapter.getSelectedCountry();
                if (!playerName.getText().toString().equals("")) {
                    actionButton.setEnabled(true);
                }
            }
        });

        // settup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_viewer_country_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onClickPlay() {
        Player player = new Player(playerName.getText().toString(), selectedCountry, true);

        if (Game.opponent.equals(Game.OpponentType.PVE)) {
            Game.player1 = player;
            Player player2 = new Player("Droid", new Country("Droid", R.mipmap.ic_launcher_round), false);
            Game.player2 = player2;
        } else {
            Game.player2 = player;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("nesto", "nesto");
        startActivity(intent);
    }


    private void onClickContinue() {
        Player player1 = new Player(playerName.getText().toString(), selectedCountry, true);
        Game.player1 = player1;

        Intent intent = new Intent(this, PlayerSettupActivity.class);
        intent.putExtra("nesto", "nesto");
        startActivity(intent);
    }
}