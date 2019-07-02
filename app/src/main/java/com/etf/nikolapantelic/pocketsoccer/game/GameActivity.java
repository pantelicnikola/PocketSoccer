package com.etf.nikolapantelic.pocketsoccer.game;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
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
import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper;

import java.util.Timer;
import java.util.TimerTask;
//import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//import java.util.concurrent.locks.StampedLock;

@RequiresApi(api = Build.VERSION_CODES.N)
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

    private Handler msgHandler;

    private final Lock lock = new ReentrantLock();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        msgHandler = new Handler();

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
                                        GameLogic.pause();
//
                                        showMessage2(GameLogic.getResultMessage());

                                        restoreBallPositions();
                                        GameLogic.resume();
                                    }
                                    if (GameLogic.isGameOver()) {
                                        GameLogic.pause();
//
////                                        GameModel.finished = true;
                                        timer.cancel();
                                        timer.purge();
                                        GameLogic.stopGame();
                                        moveToScores();
                                        GameLogic.persistGame(getApplicationContext());
////                                        GameLogic.resume();
                                    }
                                }
                            }
                        });
                    }
                }, 0, 20);
            }
        });
    }

    private void showMessage(final String message) {
        new LongOperation().execute(message);
    }

    public void showMessage1(final String message) {
        final MessageFragment msgFragment = new MessageFragment();
        msgFragment.setMessage(message);
        Runnable runnableShow = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("locked");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, msgFragment)
                        .commit();
            }
        };

        Runnable runnableHide = new Runnable() {
            @Override
            public void run() {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                lock.unlock();
                System.out.println("unlocked");

            }
        };
        msgHandler.post(runnableShow);
        msgHandler.postDelayed(runnableHide, 3000);
    }

    public void showMessage2(String message) {
        new ShowMsg(message).start();
    }

    class ShowMsg extends Thread {

        private String message;
        private MessageFragment msgFragment;

        public ShowMsg(String message) {
            this.message = message;
            this.msgFragment = new MessageFragment();
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
//                lock.lock();
//                System.out.println("lock");
                this.msgFragment.setMessage(message);

                msgHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, msgFragment)
                                .commit();
                    }
                });

                msgHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }
                }, 3000);
            } catch (Exception e) {
                System.out.println("asd");
            } finally {
//                lock.unlock();
//                System.out.println("unlock");
            }
        }
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                lock.lock();
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
            } finally {
                lock.unlock();
            }
            return null;
        }
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameActivity.this, MutualScoresActivity.class);
                intent.putExtra("playersId", Game.getPlayersId());
                startActivity(intent);
            }
        }, 6000);
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
}