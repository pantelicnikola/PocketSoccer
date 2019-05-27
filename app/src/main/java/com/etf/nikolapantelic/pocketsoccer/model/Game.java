package com.etf.nikolapantelic.pocketsoccer.model;

public class Game {

    public static Player player1;
    public static Player player2;

    public static int goalsPlayer1;
    public static int goalsPlayer2;

    public static int timeElapsed; // time elapsed in game
    public static Turn currentTurn; // whose turn is it?

    public static Ball football;

    public static OpponentType opponent;

    public static void reset() {
//        player1 = null;
//        player2 = null;

        goalsPlayer1 = 0;
        goalsPlayer2 = 0;

        timeElapsed = 0;
        currentTurn = Turn.PLAYER1;
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

    public static Turn nextTurn() {
        if (currentTurn.equals(Turn.PLAYER1)) {
            return Turn.PLAYER2;
        } else {
            return Turn.PLAYER1;
        }
    }

    public static void changeTurn() {
        if (currentTurn.equals(Turn.PLAYER1)) {
            currentTurn = Turn.PLAYER2;
        } else {
            currentTurn = Turn.PLAYER1;
        }
    }

    public static Player getPlayerByTurn(Turn turn) {
        if (turn.equals(Turn.PLAYER1)) {
            return player1;
        } else {
            return player2;
        }
    }

    public enum OpponentType {
        PVP, PVE
    }

    public enum Turn {
        PLAYER1, PLAYER2;
        public Turn next() {
            if (currentTurn.equals(PLAYER1)) {
                currentTurn = PLAYER2;
                return PLAYER2;
            } else {
                currentTurn = PLAYER1;
                return PLAYER1;
            }
        }

    }



}