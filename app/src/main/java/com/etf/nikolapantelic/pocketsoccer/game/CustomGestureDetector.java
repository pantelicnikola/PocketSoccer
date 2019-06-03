package com.etf.nikolapantelic.pocketsoccer.game;

import android.content.SharedPreferences;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;

class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private Ball ball;

    public CustomGestureDetector(Ball ball) {
        this.ball = ball;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

//        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getResources().getString(R.string.game_preferences), MODE_PRIVATE);


        ball.setVelX(velocityX/400); // ovo treba premestiti u GamePhysics
        ball.setVelY(velocityY/400);
        GameLogic.changeTurn();
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
