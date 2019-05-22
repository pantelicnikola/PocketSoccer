package com.etf.nikolapantelic.pocketsoccer.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

public class GameActivity extends AppCompatActivity {

    public static int windowHeight;
    public static int windowWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        windowWidth = size.x;
        windowHeight = size.y;

        ImageView t1p1 = findViewById(R.id.image_view_team1_player1);
        ImageView t1p2 = findViewById(R.id.image_view_team1_player2);
        ImageView t1p3 = findViewById(R.id.image_view_team1_player3);
        ImageView t2p1 = findViewById(R.id.image_view_team2_player1);
        ImageView t2p2 = findViewById(R.id.image_view_team2_player2);
        ImageView t2p3 = findViewById(R.id.image_view_team2_player3);
        ImageView ball = findViewById(R.id.image_view_ball);

        t1p1.setImageResource(Game.player1.getCountry().getFlag());
        t1p2.setImageResource(Game.player1.getCountry().getFlag());
        t1p3.setImageResource(Game.player1.getCountry().getFlag());

        t2p1.setImageResource(Game.player2.getCountry().getFlag());
        t2p2.setImageResource(Game.player2.getCountry().getFlag());
        t2p3.setImageResource(Game.player2.getCountry().getFlag());

        ball.setImageResource(R.mipmap.ic_launcher_round);

        Game.balls[0] = new Ball(ball);
        Game.balls[1] = new Ball(t1p1);
        Game.balls[2] = new Ball(t1p2);
        Game.balls[3] = new Ball(t1p3);
        Game.balls[4] = new Ball(t2p1);
        Game.balls[5] = new Ball(t2p2);
        Game.balls[6] = new Ball(t2p3);

    }







//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        return super.onTouchEvent(event);
//    }

//    class Ball {
//        float x;
//        float y;
//        float radius;
//
//        public Ball(float x, float y, float radius) {
//            this.x = x;
//            this.y = y;
//            this.radius = radius;
//        }
//    }


}



