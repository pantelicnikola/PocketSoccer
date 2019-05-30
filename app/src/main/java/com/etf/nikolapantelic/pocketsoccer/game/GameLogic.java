package com.etf.nikolapantelic.pocketsoccer.game;

import android.content.Context;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {
    private static Timer timer = new Timer();

    public static void changeTurn() {
//        timer
        deactivatePlayer(Game.playing);
        activatePlayer(Game.waiting);
        Game.changeTurn();

//        Timer timer = new Timer();
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeTurn();
            }
        }, 5000, 5000);
    }

    public static void initialSetup() {
        deactivatePlayer(Game.waiting);
    }

    private static void activatePlayer(Game.Turn turn) {
        for (Ball ball : Game.getPlayerByTurn(turn).getBalls()) {
            ball.enable();
        }
    }

    private static void deactivatePlayer(Game.Turn turn) {
        for (Ball ball : Game.getPlayerByTurn(turn).getBalls()) {
            ball.disable();
        }
    }

    public static boolean goalDetected() {
        return false;
    }
}
