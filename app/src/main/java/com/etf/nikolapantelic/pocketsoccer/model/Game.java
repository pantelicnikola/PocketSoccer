package com.etf.nikolapantelic.pocketsoccer.model;

public class Game {

    public static boolean paused;

    public static Player player1;
    public static Player player2;

    public static int goalsPlayer1;
    public static int goalsPlayer2;

    public static Turn playing, waiting;
    public static Winner winner;

    public static Ball football;

    public static OpponentType opponent;

    public static void reset() {
        paused = false;

        goalsPlayer1 = 0;
        goalsPlayer2 = 0;

        winner = null;

        playing = Turn.PLAYER1;
        waiting = Turn.PLAYER2;
        opponent = null;
    }

    public static Ball[] getAllBalls() {
        return new Ball[] {
            football,
            player1.getBall(0),
            player1.getBall(1),
            player1.getBall(2),
            player2.getBall(0),
            player2.getBall(1),
            player2.getBall(2)
        };
    }

    public static String getPlayersId() {
        return Integer.toHexString(player1.getName().hashCode() ^ player2.getName().hashCode());
    }

    public enum OpponentType {
        PVP, PVE
    }

    public enum Turn {
        PLAYER1, PLAYER2
    }

    public enum Winner {
        ONE, TWO, DRAW
    }
}