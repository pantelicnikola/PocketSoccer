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

    private static final float TRACTION_FACTOR = 0.98f;

    public Ball(ImageView imageView, float radius) {
        this.imageView = imageView;
        this.radius = radius;
        moving = false;
        velX = 0;
        velY = 0;
    }

    public float calculateCenterX() {
        return imageView.getX() + radius / 2;
    }
    public float calculateCenterY() {
        return imageView.getY() + radius / 2;
    }

    public void move() {
        if (velX == 0 && velY == 0) { // ako je brzina 0 ne racunaj nista
            return;
        } else {
            float x = imageView.getX();
            float y = imageView.getY();
            velX *= TRACTION_FACTOR;
            velY *= TRACTION_FACTOR;

            if (x <= 0 || x >= windowWidth - radius) {
                velX = -velX;
                imageView.setX(x + 2 * velX);
            } else {
                imageView.setX(x + velX);
            }

            if (y <= 0 || y >= windowHeight - radius) {
                velY = -velY;
                imageView.setY(y + 2 * velY);
            } else {
                imageView.setY(y + velY);
            }
        }
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

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
