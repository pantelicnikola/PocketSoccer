package com.etf.nikolapantelic.pocketsoccer.common.db.model;

public class ResultModel {
    private String id;
    private String player1;
    private String player2;
    private String player1Wins;
    private String player2Wins;
    private String playersId;

    public ResultModel(String player1, String player2, String player1Wins, String player2Wins, String playersId) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Wins = player1Wins;
        this.player2Wins = player2Wins;
        this.playersId = playersId;
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

    public String getPlayer1Wins() {
        return player1Wins;
    }

    public String getPlayer2Wins() {
        return player2Wins;
    }

    public String getPlayersId() {
        return playersId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(player1);
        stringBuilder.append(" ");
        stringBuilder.append(player1Wins);
        stringBuilder.append(" --- ");
        stringBuilder.append(player2Wins);
        stringBuilder.append(" ");
        stringBuilder.append(player2);
        return stringBuilder.toString();
    }
}
