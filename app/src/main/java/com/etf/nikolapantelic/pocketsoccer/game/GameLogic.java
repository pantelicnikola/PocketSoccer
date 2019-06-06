package com.etf.nikolapantelic.pocketsoccer.game;

import android.os.CountDownTimer;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;
import com.etf.nikolapantelic.pocketsoccer.settings.GamePreferencesHelper;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private static Timer turnTimer = new Timer();
    private static GamePreferencesHelper.EndType endType;
    public static boolean timerFinished = false;

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
        turnTimer.cancel();
        for (Ball b : Game.getAllBalls()) {
            GamePhysics.forceStop(b);
        }
        deactivatePlayer(Game.Turn.PLAYER1);
        deactivatePlayer(Game.Turn.PLAYER2);
    }

    public static boolean goalOccurred(float leftPostX, float rightPostX) {
        Ball football = Game.football;
        float footballX = football.calculateCenterX();
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

    public static void setEndType(GamePreferencesHelper.EndType endType) {
        GameLogic.endType = endType;
    }

    public static GamePreferencesHelper.EndType getEndType() {
        return endType;
    }

    public static void setTurn(Game.Turn player) {
        Game.setTurn(player);
        deactivatePlayer(Game.waiting);
        activatePlayer(Game.playing);
    }

    public static String getResultMessage() {
        return Game.goalsPlayer1 + " - " + Game.goalsPlayer2;
    }

    public static boolean isGameOver() {

        if (endType.equals(GamePreferencesHelper.EndType.GOALS)) {
            if (Game.goalsPlayer1 == 1 || Game.goalsPlayer2 == 1) {
                if (Game.goalsPlayer1 == 1) {
                    Game.winner = Game.Winner.ONE;
                } else {
                    Game.winner = Game.Winner.TWO;
                }
                return true;
            }
        } else {
            if (timerFinished) {
                if (Game.goalsPlayer1 > Game.goalsPlayer2) {
                    Game.winner = Game.Winner.ONE;
                } else if (Game.goalsPlayer1 < Game.goalsPlayer2) {
                    Game.winner = Game.Winner.TWO;
                } else {
                    Game.winner = Game.Winner.DRAW;
                }
                return true;
            }
        }
        return false;
    }

    public static String getWinnerMessage() {
        if (Game.winner.equals(Game.Winner.ONE)) {
            return "PLAYER 1 WINS";
        } else if (Game.winner.equals(Game.Winner.TWO)) {
            return "PLAYER 2 WINS";
        } else {
            return "IT IS DRAW";
        }
    }
}
