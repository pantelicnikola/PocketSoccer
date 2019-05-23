package com.etf.nikolapantelic.pocketsoccer.game;

import android.annotation.SuppressLint;
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

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.Timer;
import java.util.TimerTask;

import static com.etf.nikolapantelic.pocketsoccer.game.GamePhysics.checkCollisions;
import static com.etf.nikolapantelic.pocketsoccer.game.GamePhysics.moveBalls;

public class GameActivity extends AppCompatActivity {

    public static int windowHeight;
    public static int windowWidth;
    private Handler handler = new Handler();


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

        Game.reset(); // ako je continue preskociti
        setupBalls();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupBalls() {
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

        ball.setImageResource(R.drawable.footbal_default);

        Game.balls[0] = new Ball(ball, getResources().getDimension(R.dimen.football_dimension));
        Game.balls[1] = new Ball(t1p1, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.balls[2] = new Ball(t1p2, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.balls[3] = new Ball(t1p3, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.balls[4] = new Ball(t2p1, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.balls[5] = new Ball(t2p2, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.balls[6] = new Ball(t2p3, getResources().getDimension(R.dimen.player_ball_dimension));

        for (int i = 1; i < Game.balls.length; i++) {
            final GestureDetector gestureDetector = new GestureDetector(this, new CustomGestureDetector(Game.balls[i]));
            Game.balls[i].getImageView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) { // ovo se poziva kada kliknes na imageView
                    return !gestureDetector.onTouchEvent(event);
                }
            });
        }

        ball.post(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                moveBalls();
                                checkCollisions();
                            }
                        });
                    }
                }, 0, 30);
            }
        });
    }
}



