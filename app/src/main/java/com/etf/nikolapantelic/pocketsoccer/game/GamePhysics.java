package com.etf.nikolapantelic.pocketsoccer.game;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowHeight;
import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowWidth;
import static com.etf.nikolapantelic.pocketsoccer.model.Game.balls;

public class GamePhysics {

    public static void moveBalls() {
        for (Ball ball : balls) {
            move(ball);
        }
    }

    public static void checkCollisions() {
        for (int i = 0; i < balls.length - 1; i++){
            for (int j = i + 1; j < balls.length; j++) {
                if (checkCollision(balls[i], balls[j])) { // if both are not moving, don't check
                    collide(balls[i], balls[j]);
                }
            }
        }
    }

    public static boolean checkCollision(Ball ball1, Ball ball2) {

        float distance = (float) Math.sqrt(Math.pow((ball1.calculateCenterX() - ball2.calculateCenterX()),2) + Math.pow((ball1.calculateCenterY() - ball2.calculateCenterY()),2));

        if (distance < ball1.getRadius()) {
            return true;
        } else {
            return false;
        }
    }

    private static void collide(Ball ball, Ball ball1) {
        System.out.println("collision!!!");
    }

    public static void move(Ball ball) {
        if (ball.getVelX() == 0 && ball.getVelY() == 0) { // ako je brzina 0 ne racunaj nista
            return;
        } else {
            float x = ball.getImageView().getX();
            float y = ball.getImageView().getY();
            ball.setVelX(ball.getVelX() * 0.98f);
            ball.setVelY(ball.getVelY() * 0.98f);

            if (x < 0 || x > windowWidth - ball.getRadius()) {
                ball.setVelX(-ball.getVelX());
                ball.getImageView().setX(x + 2 * ball.getVelX()); // shift (zbog * 2)
            } else {
                ball.getImageView().setX(x + ball.getVelX());
            }

            if (y < 0 || y > windowHeight - ball.getRadius()) {
                ball.setVelY(-ball.getVelY());
                ball.getImageView().setY(y + 2 * ball.getVelY());
            } else {
                ball.getImageView().setY(y + ball.getVelY());
            }
        }
    }
}
