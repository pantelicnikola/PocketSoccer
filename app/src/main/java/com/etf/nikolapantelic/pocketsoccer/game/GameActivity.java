package com.etf.nikolapantelic.pocketsoccer.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private static int windowHeight;
    private static int windowWidth;
    private Handler handler = new Handler();
//    private GameLogic gameLogic;
    private float leftPostX, rightPostX, postHeight;
    private TextView messageView;


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

        setupBalls();
        Game.reset(); // ako je continue preskociti
        GameLogic.setTurn(Game.playing);

        Game.football.getImageView().post(new Runnable() {
            @Override
            public void run() {

                leftPostX = findViewById(R.id.lowerLeftPost).getX();
                rightPostX = findViewById(R.id.lowerRightPost).getX();
                postHeight = getResources().getDimension(R.dimen.post_height);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                GamePhysics.moveBalls();
                                if (GameLogic.goalOccurred(leftPostX, rightPostX, postHeight)) {
                                    try {
                                        GameLogic.stopGame();
                                        Thread.sleep(1000);
//                                        message.show()
                                        Thread.sleep(2000);
//                                        message.hide()
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    setContentView(R.layout.activity_game);
                                    setupBalls();
                                }
                            }
                        });
                    }
                }, 0, 20);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupBalls() {
        final ImageView t1p1 = findViewById(R.id.image_view_team1_player1);
        final ImageView t1p2 = findViewById(R.id.image_view_team1_player2);
        final ImageView t1p3 = findViewById(R.id.image_view_team1_player3);
        final ImageView t2p1 = findViewById(R.id.image_view_team2_player1);
        final ImageView t2p2 = findViewById(R.id.image_view_team2_player2);
        final ImageView t2p3 = findViewById(R.id.image_view_team2_player3);
        final ImageView ball = findViewById(R.id.image_view_ball);

        t1p1.setImageResource(Game.player1.getCountry().getFlag());
        t1p2.setImageResource(Game.player1.getCountry().getFlag());
        t1p3.setImageResource(Game.player1.getCountry().getFlag());

        t2p1.setImageResource(Game.player2.getCountry().getFlag());
        t2p2.setImageResource(Game.player2.getCountry().getFlag());
        t2p3.setImageResource(Game.player2.getCountry().getFlag());

        ball.setImageResource(R.drawable.footbal_default);

        Game.football = new Ball(ball, getResources().getDimension(R.dimen.football_dimension));
        Game.player1.getBalls()[0] = new Ball(t1p1, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player1.getBalls()[1] = new Ball(t1p2, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player1.getBalls()[2] = new Ball(t1p3, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player2.getBalls()[0] = new Ball(t2p1, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player2.getBalls()[1] = new Ball(t2p2, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player2.getBalls()[2] = new Ball(t2p3, getResources().getDimension(R.dimen.player_ball_dimension));

        for (Ball b : Game.player1.getBalls()) {
            final GestureDetector gestureDetector = new GestureDetector(this, new CustomGestureDetector(b));
            b.getImageView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) { // ovo se poziva kada kliknes na imageView
                    return !gestureDetector.onTouchEvent(event);
                }


            });
        }

        for (Ball b : Game.player2.getBalls()) { // ovo se ne radi u slucaju PvE
            final GestureDetector gestureDetector = new GestureDetector(this, new CustomGestureDetector(b));
            b.getImageView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) { // ovo se poziva kada kliknes na imageView
                    return !gestureDetector.onTouchEvent(event);
                }
            });
        }
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }
}