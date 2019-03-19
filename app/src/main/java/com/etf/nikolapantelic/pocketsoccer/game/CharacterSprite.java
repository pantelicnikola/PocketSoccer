package com.etf.nikolapantelic.pocketsoccer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap image;
    private int x, y;

    public CharacterSprite(Bitmap image) {
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, 200, 200, false);
        this.image = scaledImage;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {
        y++;
    }
}
