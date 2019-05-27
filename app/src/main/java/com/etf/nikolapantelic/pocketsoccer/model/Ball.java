package com.etf.nikolapantelic.pocketsoccer.model;

import android.widget.ImageView;

public class Ball {
    private ImageView imageView;
    private float velX, velY;
    private float radius;
//    private boolean moving;

    public Ball(ImageView imageView, float radius) {
        this.imageView = imageView;
        this.radius = radius;
//        moving = false;
        velX = 0f;
        velY = 0f;
    }

    public void enable() {
        imageView.setEnabled(true);
        imageView.setAlpha(255);
    }

    public void disable() {
        imageView.setEnabled(false);
        imageView.setAlpha(127);
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
        return (velX != 0 || velY != 0);
    }

    public float getRadius() {
        return radius;
    }
}
