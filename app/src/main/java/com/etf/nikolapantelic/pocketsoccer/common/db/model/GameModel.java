package com.etf.nikolapantelic.pocketsoccer.common.db.model;

import com.etf.nikolapantelic.pocketsoccer.common.db.GamesContract;

public class GameModel {
    private String id;
    private String player1;
    private String player2;
    private String result;
    private String time;

    public GameModel(String id, String player1, String player2, String result, String time) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getResult() {
        return result;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        StringBuilder scoreString = new StringBuilder();
        scoreString.append(player1);
        scoreString.append(" ");
        scoreString.append(player2);
        scoreString.append(" ");
        scoreString.append(result);
        scoreString.append(" ");
        scoreString.append(time);
        return scoreString.toString();
    }
}
