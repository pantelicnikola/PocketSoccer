package com.etf.nikolapantelic.pocketsoccer.model;

import com.etf.nikolapantelic.pocketsoccer.model.Country;
import com.etf.nikolapantelic.pocketsoccer.model.Player;

public class Game {
    public static Country currentCountry = null;

    public static Player player1 = null;
    public static Player player2 = null;

    public static int goalsPlayer1 = 0;
    public static int goalsPlayer2 = 0;

    public static int timeElapsed = 0;

    public static OpponentType opponent;

    public enum OpponentType {
        PVP, PVE
    }

}
