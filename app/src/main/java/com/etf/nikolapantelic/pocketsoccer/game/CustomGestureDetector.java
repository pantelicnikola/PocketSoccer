package com.etf.nikolapantelic.pocketsoccer.game;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;

class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private Ball ball;

    public CustomGestureDetector(Ball ball) {
        this.ball = ball;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        ball.setVelX(velocityX/500); // ovo treba premestiti u GamePhysics
        ball.setVelY(velocityY/500);
        ball.setMoving(true);
        GameLogic.changeTurn();
        return super.onFling(e1, e2, velocityX, velocityY);
    }


}
