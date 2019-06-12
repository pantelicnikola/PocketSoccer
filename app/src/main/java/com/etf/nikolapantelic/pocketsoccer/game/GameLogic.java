package com.etf.nikolapantelic.pocketsoccer.game;

import android.content.Context;

import com.etf.nikolapantelic.pocketsoccer.common.db.GamesDAO;
import com.etf.nikolapantelic.pocketsoccer.common.db.ResultsDAO;
import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;
import com.etf.nikolapantelic.pocketsoccer.common.GamePreferencesHelper;
import com.etf.nikolapantelic.pocketsoccer.model.Player;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private static Timer turnTimer = new Timer();
    private static GamePreferencesHelper.EndType endType;
    static boolean timerFinished = false;

    static void changeTurn() {
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
        for (Ball ball : getPlayerByTurn(turn).getBalls()) {
            ball.enable();
        }
    }

    private static void deactivatePlayer(Game.Turn turn) {
        for (Ball ball : getPlayerByTurn(turn).getBalls()) {
            ball.disable();
        }
    }

    static void stopGame() {
        turnTimer.cancel();
        for (Ball b : Game.getAllBalls()) {
            GamePhysics.forceStop(b);
        }
        deactivatePlayer(Game.Turn.PLAYER1);
        deactivatePlayer(Game.Turn.PLAYER2);
    }

    static boolean goalOccurred(float leftPostX, float rightPostX) {
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

    static void setEndType(GamePreferencesHelper.EndType endType) {
        GameLogic.endType = endType;
    }

    static GamePreferencesHelper.EndType getEndType() {
        return endType;
    }

    static void setTurn(@NotNull Game.Turn player) {
        if (player.equals(Game.Turn.PLAYER1)) {
            Game.playing = Game.Turn.PLAYER1;
            Game.waiting = Game.Turn.PLAYER2;
        } else {
            Game.playing = Game.Turn.PLAYER2;
            Game.waiting = Game.Turn.PLAYER1;
        }
        deactivatePlayer(Game.waiting);
        activatePlayer(Game.playing);
    }

    public static String getResultMessage() {
        return Game.goalsPlayer1 + " - " + Game.goalsPlayer2;
    }

    static boolean isGameOver() {
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

    static void persistGame(Context context) {
        GamesDAO.insertCurrentGame(context);
        ResultsDAO.replace(context);
    }

    static void pause() {
        Game.paused = true;
    }

    static void resume() {
        Game.paused = false;
    }

    private static Player getPlayerByTurn(Game.Turn turn) {
        if (turn.equals(Game.Turn.PLAYER1)) {
            return Game.player1;
        } else {
            return Game.player2;
        }
    }
}
