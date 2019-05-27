package com.etf.nikolapantelic.pocketsoccer.game;

import android.support.annotation.NonNull;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowHeight;
import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowWidth;

public class GamePhysics {

    private static final float TRACTION_FACTOR = 0.98f;
    private static final float MOVING_THRESHOLD = 1.0f;

    public static void moveBalls() {
        for (Ball ball : Game.getAllBalls()) {
            move(ball);
        }
    }

    private static void move(@NonNull Ball ball) {
        if (ball.isMoving()) {
            float x = ball.getImageView().getX();
            float y = ball.getImageView().getY();
            ball.setVelX(ball.getVelX() * TRACTION_FACTOR);
            ball.setVelY(ball.getVelY() * TRACTION_FACTOR);

            // check for left and right bounds
            if (x < 0 || x > windowWidth - ball.getRadius()) {
                ball.setVelX(-ball.getVelX());
                ball.getImageView().setX(x + 2 * ball.getVelX());
            } else {
                ball.getImageView().setX(x + ball.getVelX());
            }

            // check for top and bottom bounds
            if (y < 0 || y > windowHeight - ball.getRadius()) {
                ball.setVelY(-ball.getVelY());
                ball.getImageView().setY(y + 2 * ball.getVelY());
            } else {
                ball.getImageView().setY(y + ball.getVelY());
            }

            // check if ball should stop
            if (Math.sqrt(Math.pow(ball.getVelX(), 2) + Math.pow(ball.getVelY(), 2)) < MOVING_THRESHOLD) {
                forceStop(ball);
            }
        }
    }

    private static void forceStop(@NonNull Ball ball) {
        ball.setVelX(0);
        ball.setVelY(0);
        ball.setMoving(false);
    }

    public static void checkCollisions() {
        Ball[] balls = Game.getAllBalls();
        for (int i = 0; i < balls.length - 1; i++){
            for (int j = i + 1; j < balls.length; j++) {
                if (balls[i].isMoving() || balls[j].isMoving()) {
                    if (areColliding(balls[i], balls[j])) {
                        performCollision(balls[i], balls[j]);
                    }
                }
            }
        }
    }

    private static boolean areColliding(@NonNull Ball ball1, @NonNull Ball ball2) {
        float distance = (float) Math.sqrt(Math.pow((ball1.calculateCenterX() - ball2.calculateCenterX()),2) + Math.pow((ball1.calculateCenterY() - ball2.calculateCenterY()),2));
        return (distance < ball1.getRadius());
    }

    private static void performCollision(Ball ball, Ball ball1) {
        System.out.println("collision!!!");
    }
}
