package com.etf.nikolapantelic.pocketsoccer.game;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private static Timer turnTimer = new Timer();
    private static Timer gameTimer = new Timer();

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
        stopTurnTimer();
        for (Ball b : Game.getAllBalls()) {
            GamePhysics.forceStop(b);
        }
        deactivatePlayer(Game.Turn.PLAYER1);
        deactivatePlayer(Game.Turn.PLAYER2);
    }

    public static boolean goalOccurred(float leftPostX, float rightPostX, float postHeight) {
        Ball football = Game.football;
        float footballX = football.calculateCenterX();
        float footballY = football.calculateCenterY();
//        float leftPostX1 =  GameActivity.getContext().getResources().getFraction(R.fraction.left_post_fraction, GameActivity.getWindowWidth(), 1);
//        float rightPostX1 = GameActivity.getContext().getResources().getFraction(R.fraction.right_post_fraction, GameActivity.getWindowWidth(), 1);
        if (footballX > leftPostX && footballX < rightPostX) {
            if (football.isHitTop()) {
                stopGame();
                Game.goalsPlayer1++;
                GameLogic.setTurn(Game.Turn.PLAYER2);
                return true;
            }
            if (football.isHitBottom()) {
                stopGame();
                Game.goalsPlayer2++;
                GameLogic.setTurn(Game.Turn.PLAYER1);
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
