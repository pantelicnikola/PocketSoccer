package com.etf.nikolapantelic.pocketsoccer.game;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.etf.nikolapantelic.pocketsoccer.model.Ball;
import com.etf.nikolapantelic.pocketsoccer.model.Game;

import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowHeight;
import static com.etf.nikolapantelic.pocketsoccer.game.GameActivity.windowWidth;

public class GamePhysics {

    private static final float TRACTION_FACTOR = 0.99f;
    private static final float MOVING_THRESHOLD = 1.0f;

    public static void moveBalls() {
        for (Ball ball : Game.getAllBalls()) {
            move(ball);
        }
    }

    private static void move(@NonNull Ball ball) {
        if (ball.isMoving()) {
            ball.setVelX(ball.getVelX() * TRACTION_FACTOR);
            ball.setVelY(ball.getVelY() * TRACTION_FACTOR);

            // check if ball should stop
            if (Math.sqrt(Math.pow(ball.getVelX(), 2) + Math.pow(ball.getVelY(), 2)) < MOVING_THRESHOLD) {
                forceStop(ball);
                return;
            }


            for (int i = 0; i < 10; i++){
                float x = ball.getImageView().getX();
                float y = ball.getImageView().getY();
                ball.getImageView().setX(x + ball.getVelX() / 10);
                ball.getImageView().setY(y + ball.getVelY() / 10);
                // check for left and right bounds
                if (x < 0 || x > windowWidth - ball.getRadius()) {
                    ball.getImageView().setX(x - ball.getVelX() / 10);
                    ball.getImageView().setY(y - ball.getVelY() / 10);
                    ball.setVelX(-ball.getVelX());
                }

                // check for top and bottom bounds
                if (y < 0 || y > windowHeight - ball.getRadius()) {
                    ball.getImageView().setX(x - ball.getVelX() / 10);
                    ball.getImageView().setY(y - ball.getVelY() / 10);
                    ball.setVelY(-ball.getVelY());
                }
                Ball b;
                if ((b = getCollidingBall(ball)) != null) {
                    ball.getImageView().setX(x - ball.getVelX() / 10);
                    ball.getImageView().setY(y - ball.getVelY() / 10);
//                    b.getImageView().setX(x + b.getVelX() / 10);
//                    b.getImageView().setY(y + b.getVelY() / 10);

                    calculateCollision(b, ball);
//                    littleMove(ball);
//                    littleMove(b);
                    break;
                }
            }
//            Ball b;
//            while ((b = getCollidingBall(ball)) != null) {
//                calculateCollision(b, ball);
//                littleMove(ball);
//                littleMove(b);
//            }


//            ball.getImageView().setX(x + ball.getVelX() / 2);
//            ball.getImageView().setY(y + ball.getVelY() / 2);
        }
    }

    private static void calculateWallCollision() {

    }

    private static void littleMove(@NonNull Ball ball) {
        float x = ball.getImageView().getX();
        float y = ball.getImageView().getY();

        // check if ball should stop
        if (Math.sqrt(Math.pow(ball.getVelX(), 2) + Math.pow(ball.getVelY(), 2)) < MOVING_THRESHOLD) {
            forceStop(ball);
            return;
        }

        // check for left and right bounds
        if (x < 0 || x > windowWidth - ball.getRadius()) {
            ball.setVelX(-ball.getVelX());
        }

        // check for top and bottom bounds
        if (y < 0 || y > windowHeight - ball.getRadius()) {
            ball.setVelY(-ball.getVelY());
        }

        ball.getImageView().setX(x + ball.getVelX() / 7);
        ball.getImageView().setY(y + ball.getVelY() / 7);
    }

    private static void forceStop(@NonNull Ball ball) {
        ball.setVelX(0);
        ball.setVelY(0);
    }

    public static void calculateCollisions() {
        Ball[] balls = Game.getAllBalls();
        for (int i = 0; i < balls.length - 1; i++){
            for (int j = i + 1; j < balls.length; j++) {
                if (balls[i].isMoving() || balls[j].isMoving()) {
                    if (areColliding(balls[i], balls[j])) {
                        calculateCollision(balls[i], balls[j]);
                    }
                }
            }
        }
    }

    public static boolean collisionsExist() {
        Ball[] balls = Game.getAllBalls();
        for (int i = 0; i < balls.length - 1; i++){
            for (int j = i + 1; j < balls.length; j++) {
                if (balls[i].isMoving() || balls[j].isMoving()) {
                    if (areColliding(balls[i], balls[j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Nullable
    private static Ball getCollidingBall(Ball ball) {
        for (Ball b : Game.getAllBalls()) {
            if (!b.equals(ball)) {
                if (areColliding(b, ball)) {
                    return b;
                }
            }
        }
        return null;
    }

    @Nullable
    private static boolean collides(Ball ball) {
        for (Ball b : Game.getAllBalls()) {
            if (!b.equals(ball)) {
                if (areColliding(b, ball)) {
                    return true;
                }
            }
        }
        return false;
    }


    private static boolean areColliding(@NonNull Ball ball1, @NonNull Ball ball2) {
        float distance = (float) Math.sqrt(Math.pow((ball1.calculateCenterX() - ball2.calculateCenterX()),2) + Math.pow((ball1.calculateCenterY() - ball2.calculateCenterY()),2));
        float minDistance = ball1.getRadius() / 2 + ball2.getRadius() / 2;
        return (distance < minDistance);
    }

//    public static void avoidCollisions() {
//        for (Ball ball : Game.getAllBalls()) {
//            Ball b = null;
//            while ((b = getCollidingBall(ball)) != null) {
//                while (areColliding(b, ball)) {
//                    move(ball);
//                    move(b);
//                }
//            }
//        }
//    }

    private static void calculateCollision(@NonNull Ball ball1, @NonNull Ball ball2) {
        System.out.println("collision!!!");

        float resVelX1, resVelX2, resVelY1, resVelY2;

        double x1 = ball1.calculateCenterX();
        double y1 = ball1.calculateCenterY();
        double x2 = ball2.calculateCenterX();
        double y2 = ball2.calculateCenterY();


        double teta1 = Math.atan2(ball1.getVelY(), ball1.getVelX());
        double teta2 = Math.atan2(ball2.getVelY(), ball2.getVelX());
        double phi = Math.atan((y2 - y1) / (x2 - x1));
        double intVel1 = Math.sqrt(Math.pow(ball1.getVelY(), 2) + Math.pow(ball1.getVelX(), 2));
        double intVel2 = Math.sqrt(Math.pow(ball2.getVelY(), 2) + Math.pow(ball2.getVelX(), 2));

        if (!ball1.isMoving()) {
            resVelX1 = (float) (intVel2 * Math.cos(teta2 - phi) * Math.cos(phi));
            resVelY1 = (float) (intVel2 * Math.cos(teta2 - phi) * Math.sin(phi));

            resVelX2 = (float) (intVel2 * Math.sin(teta2 - phi) * Math.sin(phi));
            resVelY2 = (float) (intVel2 * Math.sin(teta2 - phi) * Math.cos(phi));

        } else if (!ball2.isMoving()) {
            resVelX1 = (float) (intVel1 * Math.sin(teta1 - phi) * Math.sin(phi));
            resVelY1 = (float) (intVel1 * Math.sin(teta1 - phi) * Math.cos(phi));

            resVelX2 = (float) (intVel1 * Math.cos(teta1 - phi) * Math.cos(phi));
            resVelY2 = (float) (intVel1 * Math.cos(teta1 - phi) * Math.sin(phi));

        } else {
            resVelX1 = (float) (intVel2 * Math.cos(teta2 - phi) * Math.cos(phi) + intVel1 * Math.sin(teta1 - phi) * Math.sin(phi));
            resVelY1 = (float) (intVel2 * Math.cos(teta2 - phi) * Math.sin(phi) + intVel1 * Math.sin(teta1 - phi) * Math.cos(phi));

            resVelX2 = (float) (intVel1 * Math.cos(teta1 - phi) * Math.cos(phi) + intVel2 * Math.sin(teta2 - phi) * Math.sin(phi));
            resVelY2 = (float) (intVel1 * Math.cos(teta1 - phi) * Math.sin(phi) + intVel2 * Math.sin(teta2 - phi) * Math.cos(phi));
        }
        ball1.setVelX(resVelX1 * 0.9f);
        ball1.setVelY(resVelY1 * 0.9f);

        ball2.setVelX(resVelX2 * 0.9f);
        ball2.setVelY(resVelY2 * 0.9f);
    }
}
