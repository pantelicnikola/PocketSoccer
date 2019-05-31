package com.etf.nikolapantelic.pocketsoccer.game;

import com.etf.nikolapantelic.pocketsoccer.R;
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

    public static void stopGame() {
        for (Ball b : Game.getAllBalls()) {
            GamePhysics.forceStop(b);
        }
    }

    public static boolean goalOccurred(float leftPostX, float rightPostX, float postHeight) {
        float footballX = Game.football.calculateCenterX();
        float footballY = Game.football.calculateCenterY();
//        float leftPostX =  GameActivity.getContext().getResources().getFraction(R.fraction.left_post_bias, GameActivity.getWindowWidth(), 1);
//        float rightPostX = GameActivity.getContext().getResources().getFraction(R.fraction.right_post_bias, GameActivity.getWindowWidth(), 1);
        if (footballX > leftPostX && footballX < rightPostX) {
            if (footballY < postHeight) {
                System.out.println("Gornji gooooool");
                return true;
            }
            if (footballY > GameActivity.getWindowHeight() - postHeight) {
                System.out.println("Donji goooooool");
                return true;
            }
        }
        return false;
    }
}
