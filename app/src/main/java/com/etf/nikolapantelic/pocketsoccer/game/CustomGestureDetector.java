package com.etf.nikolapantelic.pocketsoccer.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.settings.SettingsModel;

class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private Ball ball;
    private Context context;

    public CustomGestureDetector(Ball ball, Context context) {
        this.ball = ball;
        this.context = context;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float speedDenominator;
        SettingsModel settingsModel = SettingsModel.getInstance(context);
        switch (settingsModel.getGameSpeed()) {
            case SLOW:
                speedDenominator = 600;
                break;
            case MEDIUM:
                speedDenominator = 400;
                break;
            case FAST:
                speedDenominator = 200;
                break;
            default:
                speedDenominator = 0;
        }

        ball.setVelX(velocityX / speedDenominator);
        ball.setVelY(velocityY / speedDenominator);

        GameLogic.changeTurn();
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
