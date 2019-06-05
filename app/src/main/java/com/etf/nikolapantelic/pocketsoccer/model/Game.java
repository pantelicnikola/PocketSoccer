package com.etf.nikolapantelic.pocketsoccer.model;

import android.support.annotation.NonNull;

public class Game {

    public static boolean paused;

    public static Player player1;
    public static Player player2;

    public static int goalsPlayer1;
    public static int goalsPlayer2;

    public static int timeElapsed; // time elapsed in game
    public static Turn playing, waiting; // whose turn is it?

    public static Ball football;

    public static OpponentType opponent;

    public static void reset() {

        paused = false;

        goalsPlayer1 = 0;
        goalsPlayer2 = 0;

        timeElapsed = 0;
        playing = Turn.PLAYER1;
        waiting = Turn.PLAYER2;
        opponent = null;

    }

    public static Ball[] getAllBalls() {
        Ball[] balls = new Ball[7];
        balls[0] = football;
        balls[1] = player1.getBall(0);
        balls[2] = player1.getBall(1);
        balls[3] = player1.getBall(2);
        balls[4] = player2.getBall(0);
        balls[5] = player2.getBall(1);
        balls[6] = player2.getBall(2);
        return balls;
    }

    public static void setTurn(Turn turn) {
        if (turn.equals(Turn.PLAYER1)) {
            playing = Turn.PLAYER1;
            waiting = Turn.PLAYER2;
        } else {
            playing = Turn.PLAYER2;
            waiting = Turn.PLAYER1;
        }
    }

    public static Player getPlayerByTurn(@NonNull Turn turn) {
        if (turn.equals(Turn.PLAYER1)) {
            return player1;
        } else {
            return player2;
        }
    }

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        paused = false;
    }

    public enum OpponentType {
        PVP, PVE
    }

    public enum Turn {
        PLAYER1, PLAYER2
    }
}