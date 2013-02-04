package com.dmitrys.games.snakes.view.hero;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.dmitrys.games.snakes.view.GameView;
import com.dmitrys.games.snakes.view.weapon.Bullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Administrator
 * Date: 03.02.13
 * Time: 17:41
 */
public class Hero extends GameCharacter {

    private int currentFrame = 0;
    private int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};

    private List<Bullet> bullets = new ArrayList<Bullet>();

    public Hero(GameView gameView, int resourceId) {
        super(gameView, false);

        this.bmp = BitmapFactory.decodeResource(gameView.getResources(), resourceId);

        this.width = bmp.getWidth() / SPRITE_COLS;
        this.height = bmp.getHeight() / SPRITE_ROWS;
        this.xSpeed = 3;
        this.ySpeed = 3;
    }

    @Override
    public void onDraw(Canvas canvas) {
        updateCoordinates();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dest = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dest, null);

        Iterator<Bullet> j = bullets.iterator();
        while (j.hasNext()) {
            Bullet b = j.next();
            if (b.x >= 0 && b.x < 600 || b.y >= 0 && b.y < 1000) {
                b.onDraw(canvas);
            } else {
                j.remove();
            }
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void updateCoordinates() {
        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0)
            xSpeed = -xSpeed;
        x = x + xSpeed;
        if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0)
            ySpeed = -ySpeed;
        y = y + ySpeed;
        currentFrame = ++currentFrame % SPRITE_COLS;
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % SPRITE_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }
}
