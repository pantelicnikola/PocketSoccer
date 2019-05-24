package com.etf.nikolapantelic.pocketsoccer.model;

import android.content.Context;
import android.widget.ImageView;

import com.etf.nikolapantelic.pocketsoccer.R;

import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowHeight;
import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowWidth;

public class Ball {
    private ImageView imageView;
    private float velX, velY;
    private float radius;
    private boolean moving;

    public Ball(ImageView imageView, float radius) {
        this.imageView = imageView;
        this.radius = radius;
        moving = false;
        velX = 0f;
        velY = 0f;
    }

    public float calculateCenterX() {
        return imageView.getX() + radius / 2;
    }
    public float calculateCenterY() {
        return imageView.getY() + radius / 2;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getRadius() {
        return radius;
    }
}
