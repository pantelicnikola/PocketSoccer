package com.etf.nikolapantelic.pocketsoccer.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.etf.nikolapantelic.pocketsoccer.R;

public class GameActivity extends Activity {

//    private Ball testBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));

//        setContentView(R.layout.activity_game);
//        testBall = new Ball(100, 100, 100);

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


//        ImageView t1p1 = findViewById(R.id.image_view_team1_player1);
//        ImageView t1p2 = findViewById(R.id.image_view_team1_player2);
//        ImageView t1p3 = findViewById(R.id.image_view_team1_player3);
//        ImageView t2p1 = findViewById(R.id.image_view_team2_player1);
//        ImageView t2p2 = findViewById(R.id.image_view_team2_player2);
//        ImageView t2p3 = findViewById(R.id.image_view_team2_player3);
//        ImageView ball = findViewById(R.id.image_view_ball);
//
//        t1p1.setImageResource(Game.player1.getCountry().getFlag());
//        t1p2.setImageResource(Game.player1.getCountry().getFlag());
//        t1p3.setImageResource(Game.player1.getCountry().getFlag());
//
//        t2p1.setImageResource(Game.player2.getCountry().getFlag());
//        t2p2.setImageResource(Game.player2.getCountry().getFlag());
//        t2p3.setImageResource(Game.player2.getCountry().getFlag());
//
//        ball.setImageResource(R.mipmap.ic_launcher_round);
