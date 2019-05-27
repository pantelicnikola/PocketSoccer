package com.etf.nikolapantelic.pocketsoccer.model;

public class Player {
    private String name;
    private Country country;
    private boolean isHuman;
    private Ball[] balls;

    public Player(String name, Country country, boolean isHuman) {
        this.name = name;
        this.country = country;
        this.isHuman = isHuman;
        balls = new Ball[3];
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

    public Ball[] getBalls() {
        return balls;
    }

    public void setBalls(Ball[] balls) {
        this.balls = balls;
    }

    public Ball getBall(int i) {
        return balls[i];
    }
}
