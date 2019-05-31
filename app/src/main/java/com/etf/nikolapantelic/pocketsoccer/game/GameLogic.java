package com.etf.nikolapantelic.pocketsoccer.game;

import android.support.annotation.NonNull;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private static Timer timer = new Timer();

    public static void changeTurn() {
        setTurn(Game.waiting);
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeTurn();
            }
        }, 5000, 5000);
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
//        float leftPostX1 =  GameActivity.getContext().getResources().getFraction(R.fraction.left_post_fraction, GameActivity.getWindowWidth(), 1);
//        float rightPostX1 = GameActivity.getContext().getResources().getFraction(R.fraction.right_post_fraction, GameActivity.getWindowWidth(), 1);
        if (footballX > leftPostX && footballX < rightPostX) {
            if (footballY < postHeight) {
                System.out.println("Gornji gooooool");
                goalScored(Game.Turn.PLAYER1);
                return true;
            }
            if (footballY > GameActivity.getWindowHeight() - postHeight) {
                System.out.println("Donji goooooool");
                goalScored(Game.Turn.PLAYER1);
                return true;
            }
        }
        return false;
    }

    public static void goalScored(@NonNull Game.Turn player) {
        if (player.equals(Game.Turn.PLAYER1)) {
            Game.goalsPlayer1++;
            GameLogic.setTurn(Game.Turn.PLAYER2);
        } else {
            Game.goalsPlayer2++;
            GameLogic.setTurn(Game.Turn.PLAYER1);
        }
    }

    public static void setTurn(Game.Turn player) {
        Game.setTurn(player);
        deactivatePlayer(Game.waiting);
        activatePlayer(Game.playing);
    }
}
