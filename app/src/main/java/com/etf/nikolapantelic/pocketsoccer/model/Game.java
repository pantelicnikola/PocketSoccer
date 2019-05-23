package com.etf.nikolapantelic.pocketsoccer.model;

public class Game {

    public static Player player1;
    public static Player player2;

    public static int goalsPlayer1;
    public static int goalsPlayer2;

    public static int timeElapsed; // time elapsed in game
    public static Turn currentTurn; // whose turn is it?

    public static Ball[] balls;

    public static OpponentType opponent;

    public static void reset() {
//        player1 = null;
//        player2 = null;

        goalsPlayer1 = 0;
        goalsPlayer2 = 0;

        timeElapsed = 0;
        currentTurn = Turn.PLAYER1;
        balls = new Ball[7];
        opponent = null;

    }

    public enum OpponentType {
        PVP, PVE
    }

    public enum Turn {
        PLAYER1, PLAYER2;
        Turn next(Turn current) {
            if (current.equals(PLAYER1)) {
                return PLAYER2;
            } else {
                return PLAYER1;
            }
        }
    }

}