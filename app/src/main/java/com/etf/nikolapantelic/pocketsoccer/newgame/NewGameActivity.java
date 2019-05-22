package com.etf.nikolapantelic.pocketsoccer.newgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Button pvpButton = findViewById(R.id.button_vs_player);
        Button pveButton = findViewById(R.id.button_vs_computer);

        pveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickVsComputer(v);
            }
        });

        pvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickVsPlayer(v);
            }
        });
    }

    public void onClickVsComputer(View view) {
        Intent intent = new Intent(this, PlayerSettupActivity.class);
//        intent.putExtra("opponent_type", Game.OpponentType.PVE.toString());
        Game.opponent = Game.OpponentType.PVE;
        startActivity(intent);
    }

    public void onClickVsPlayer(View view) {
        Intent intent = new Intent(this, PlayerSettupActivity.class);
//        intent.putExtra("opponent_type", Game.OpponentType.PVP.toString());
        Game.opponent = Game.OpponentType.PVP;
        startActivity(intent);
    }
}
