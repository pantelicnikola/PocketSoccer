package com.etf.nikolapantelic.pocketsoccer.game;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private static Timer turnTimer = new Timer();

    public static void changeTurn() {
        setTurn(Game.waiting);
        turnTimer.cancel();
        turnTimer = new Timer();
        turnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeTurn();
            }
        }, 5000, 5000);
    }

    public static void stopTurnTimer() {
        turnTimer.cancel();
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
                Game.goalsPlayer1++;
                return true;
            }
            if (footballY > GameActivity.getWindowHeight() - postHeight) {
                Game.goalsPlayer2++;
                return true;
            }
        }
        return false;
    }

//    public static void goalScored(@NonNull Game.Turn player) {
//        if (player.equals(Game.Turn.PLAYER1)) {
//            Game.goalsPlayer1++;
//            GameLogic.setTurn(Game.Turn.PLAYER2);
//        } else {
//            Game.goalsPlayer2++;
//            GameLogic.setTurn(Game.Turn.PLAYER1);
//        }
//    }

    public static void setTurn(Game.Turn player) {
        Game.setTurn(player);
        deactivatePlayer(Game.waiting);
        activatePlayer(Game.playing);
    }

    public static String getResultMessage() {
        return Game.goalsPlayer1 + " - " + Game.goalsPlayer2;
    }
}
