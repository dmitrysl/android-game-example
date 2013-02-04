package com.dmitrys.games.snakes.view.hero;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.dmitrys.games.snakes.view.GameView;

/**
 * User: Administrator
 * Date: 03.02.13
 * Time: 17:41
 */
public class Enemy extends GameCharacter {

    public Enemy(GameView gameView, int resourceId) {
        super(gameView, true);

        this.bmp = BitmapFactory.decodeResource(gameView.getResources(), resourceId);

        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    @Override
    public void onDraw(Canvas canvas) {
        updateCoordinates();
        int srcX = width;
        int srcY = height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dest = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dest, null);
    }

    protected void updateCoordinates() {
        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0)
            xSpeed = -xSpeed;
        x = x + xSpeed;
        if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0)
            ySpeed = -ySpeed;
        y = y + ySpeed;
    }
}
