package com.etf.nikolapantelic.pocketsoccer.game;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
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
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsModel;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends FragmentActivity {

    private static int WINDOW_HEIGHT;
    private static int WINDOW_WIDTH;
    private Handler handler;
    private MessageFragment messageFragment;
    private float leftPostX, rightPostX, postHeight;
    //    private GameLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WINDOW_WIDTH = size.x;
        WINDOW_HEIGHT = size.y;

        setFieldColor();

        Game.reset(); // ako je continue preskociti
        setupBalls();
//        GameLogic.setTurn(Game.playing);

        messageFragment = new MessageFragment();
        handler = new Handler();

        Game.football.getImageView().post(new Runnable() {
            @Override
            public void run() {
                leftPostX = findViewById(R.id.lowerLeftPost).getX();
                rightPostX = findViewById(R.id.lowerRightPost).getX();
                postHeight = getResources().getDimension(R.dimen.post_height);
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!Game.paused) {
                                    GamePhysics.moveBalls();
                                    if (GameLogic.goalOccurred(leftPostX, rightPostX, postHeight)) {
                                        Game.pause();
                                        showMessage(GameLogic.getResultMessage());
                                        setContentView(R.layout.activity_game);
                                        setupBalls();
                                        Game.resume();
                                    }
                                }
                            }
                        });
                    }
                }, 0, 20);
            }
        });
    }

    private void setFieldColor() {
        SettingsModel settingsModel = SettingsModel.getInstance(this);
        View layout = getWindow().getDecorView();
        switch (settingsModel.getFieldType()) {
            case GREEN:
                layout.setBackgroundColor(Color.GREEN);
                break;
            case YELLOW:
                layout.setBackgroundColor(Color.YELLOW);
                break;
            case GREY:
                layout.setBackgroundColor(Color.GRAY);
                break;
        }
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.game_preferences), MODE_PRIVATE);
//        SettingsModel.FieldType fieldType = SettingsModel.FieldType.valueOf(preferences.getString(getString(R.string.key_field_type), null));
//        View layout = getWindow().getDecorView();
//        switch (fieldType) {
//            case GREEN:
//                layout.setBackgroundColor(Color.GREEN);
//                break;
//            case YELLOW:
//                layout.setBackgroundColor(Color.YELLOW);
//                break;
//            case GREY:
//                layout.setBackgroundColor(Color.GRAY);
//                break;
//        }
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

        GameLogic.setTurn(Game.playing);

        for (Ball b : Game.player1.getBalls()) {
            final GestureDetector gestureDetector = new GestureDetector(this, new CustomGestureDetector(b, this));
            b.getImageView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) { // ovo se poziva kada kliknes na imageView
                    return !gestureDetector.onTouchEvent(event);
                }
            });
        }

        for (Ball b : Game.player2.getBalls()) { // ovo se ne radi u slucaju PvE
            final GestureDetector gestureDetector = new GestureDetector(this, new CustomGestureDetector(b, this));
            b.getImageView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) { // ovo se poziva kada kliknes na imageView
                    return !gestureDetector.onTouchEvent(event);
                }
            });
        }
    }

    private void showMessage(String message) {
        messageFragment.setMessage(message);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, messageFragment)
                .commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(messageFragment).commit();
            }
        }, 3000);
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }
}