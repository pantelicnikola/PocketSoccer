package com.etf.nikolapantelic.pocketsoccer.game;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

public class GameLogic {
    public static void changeTurn() {
        deactivatePlayer(Game.currentTurn);
        Game.changeTurn();
        activatePlayer(Game.currentTurn);
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
}
