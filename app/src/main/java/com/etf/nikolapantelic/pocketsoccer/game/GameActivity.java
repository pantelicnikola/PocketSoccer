package com.etf.nikolapantelic.pocketsoccer.game;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentActivity;
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
import com.etf.nikolapantelic.pocketsoccer.scores.MutualScoresActivity;
import com.etf.nikolapantelic.pocketsoccer.settings.GamePreferencesHelper;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends FragmentActivity {

    private static int WINDOW_HEIGHT;
    private static int WINDOW_WIDTH;
    private Handler handler;
    private MessageFragment messageFragment;
    private float leftPostX, rightPostX;
    //    private GameLogic gameLogic;
    private TextView textViewTimer;
    private CountDownTimer gameTimer;

    private ConstraintSet constraintSet;
    private ConstraintLayout constraintLayout;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this;

        textViewTimer = findViewById(R.id.textViewTimer);
        constraintLayout = findViewById(R.id.root);
        constraintSet = new ConstraintSet();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WINDOW_WIDTH = size.x;
        WINDOW_HEIGHT = size.y;

        setFieldColor();
        setGameEnd();

        Game.reset(); // ako je continue preskociti
        setupBalls();

        messageFragment = new MessageFragment();
        handler = new Handler();

        Game.football.getImageView().post(new Runnable() {
            @Override
            public void run() {
                saveBallParams();
                leftPostX = findViewById(R.id.lowerLeftPost).getX();
                rightPostX = findViewById(R.id.lowerRightPost).getX();
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!Game.paused) {
                                    GamePhysics.moveBalls();
                                    if (GameLogic.goalOccurred(leftPostX, rightPostX)) {
                                        Game.pause();
                                        showMessage(GameLogic.getResultMessage());
                                        restoreBallPositions();
                                        Game.resume();
                                    }
                                    if (GameLogic.isGameOver()) {
                                        timer.cancel();
                                        timer.purge();
//                                        Game.pause();
//                                        Game.finished = true;
                                        GameLogic.stopGame();
//                                        showMessage(GameLogic.getWinnerMessage());
                                        GameLogic.persistGame(getContext());
                                        moveToScores();
//                                        Game.resume();
                                        // save the result
                                        // go to scores activity
                                    }
                                }
                            }
                        });
                    }
                }, 0, 20);
            }
        });
    }

    private void showMessage(String message) {
//        messageFragment = new MessageFragment();
//        messageFragment.setMessage(message);
//
//        final FragmentManager fragmentManager = getSupportFragmentManager();
//
//        fragmentManager
//            .beginTransaction()
//            .add(R.id.fragment_container, messageFragment)
//            .commit();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fragmentManager
//                        .beginTransaction()
//                        .remove(messageFragment).commit();
//                finish();
//            }
//        }, 3000);

        new LongOperation().execute(message);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupBalls() {

        ImageView t1p1ImageView = findViewById(R.id.image_view_team1_player1);
        ImageView t1p2ImageView = findViewById(R.id.image_view_team1_player2);
        ImageView t1p3ImageView = findViewById(R.id.image_view_team1_player3);
        ImageView t2p1ImageView = findViewById(R.id.image_view_team2_player1);
        ImageView t2p2ImageView = findViewById(R.id.image_view_team2_player2);
        ImageView t2p3ImageView = findViewById(R.id.image_view_team2_player3);
        ImageView ballImageView = findViewById(R.id.image_view_ball);

        t1p1ImageView.setImageResource(Game.player1.getCountry().getFlag());
        t1p2ImageView.setImageResource(Game.player1.getCountry().getFlag());
        t1p3ImageView.setImageResource(Game.player1.getCountry().getFlag());
        t2p1ImageView.setImageResource(Game.player2.getCountry().getFlag());
        t2p2ImageView.setImageResource(Game.player2.getCountry().getFlag());
        t2p3ImageView.setImageResource(Game.player2.getCountry().getFlag());
        ballImageView.setImageResource(R.drawable.footbal_default);

        Game.football = new Ball(ballImageView, getResources().getDimension(R.dimen.football_dimension));
        Game.player1.getBalls()[0] = new Ball(t1p1ImageView, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player1.getBalls()[1] = new Ball(t1p2ImageView, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player1.getBalls()[2] = new Ball(t1p3ImageView, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player2.getBalls()[0] = new Ball(t2p1ImageView, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player2.getBalls()[1] = new Ball(t2p2ImageView, getResources().getDimension(R.dimen.player_ball_dimension));
        Game.player2.getBalls()[2] = new Ball(t2p3ImageView, getResources().getDimension(R.dimen.player_ball_dimension));

        GameLogic.setTurn(Game.playing);
        GameLogic.timerFinished = false;

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

    private void setGameEnd() {
        GameLogic.setEndType(GamePreferencesHelper.getInstance(this).getEndType());
        if (GameLogic.getEndType().equals(GamePreferencesHelper.EndType.TIME)) {
            gameTimer = new CountDownTimer(300000, 1000) {

                public void onTick(long millisUntilFinished) {
                    String timeString = millisUntilFinished / 60000 + " : " + millisUntilFinished / 1000 % 60;
                    textViewTimer.setText(timeString);
                }

                public void onFinish() {
                    GameLogic.timerFinished = true;
                }

            }.start();
        } else {
            textViewTimer.setVisibility(View.INVISIBLE);
        }
    }

    private void setFieldColor() {
        switch (GamePreferencesHelper.getInstance(this).getFieldType()) {
            case GREEN:
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.grass_field, null));
                break;
            case YELLOW:
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.parquet_field, null));
                break;
            case GREY:
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.concrete_field, null));
                break;
        }
    }

    private void moveToScores() {
//        Intent intent = new Intent(this, MutualScoresActivity.class);
//        startActivity(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(GameActivity.this, MutualScoresActivity.class);
                startActivity(start);
                finish();
            }
        }, 4000);
    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                messageFragment.setMessage(strings[0]);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, messageFragment)
                        .commit();

                Thread.sleep(3000);

                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(messageFragment).commit();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    private void restoreBallPositions() {
        constraintSet.applyTo(constraintLayout);
    }

    private void saveBallParams() {
        constraintSet.clone(constraintLayout);
    }

    public static Context getContext() {
        return context;
    }

    //    private class LongOperation2 extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            moveToScores();
//            return null;
//        }
//

//    }
//    private void asd() {
//        new LongOperation2().execute();
//    }
}