package com.etf.nikolapantelic.pocketsoccer.model;

public class Player {
    private String name;
    private Country country;
    private boolean isHuman;

    public Player(String name, Country country, boolean isHuman) {
        this.name = name;
        this.country = country;
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
