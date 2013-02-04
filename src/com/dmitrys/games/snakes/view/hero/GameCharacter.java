package com.dmitrys.games.snakes.view.hero;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.dmitrys.games.snakes.view.GameView;
import com.dmitrys.games.snakes.view.weapon.Bullet;

import java.util.Random;

/**
 * User: Administrator
 * Date: 02.02.13
 * Time: 13:01
 */
public abstract class GameCharacter {

    protected int x = 5;
    protected int y = 0;
    protected int xSpeed = 5;
    protected int ySpeed = 5;

    protected int width;
    protected int height;

    protected GameView gameView;
    protected Bitmap bmp;

    protected static final int SPRITE_ROWS = 4;
    protected static final int SPRITE_COLS = 3;

    protected static boolean isEnemy = false;

    public GameCharacter(GameView gameView, boolean isEnemy) {
        if (gameView == null)
            throw new IllegalArgumentException();

        this.gameView = gameView;
        this.isEnemy = isEnemy;

        Random random = new Random();
//        xSpeed = random.nextInt(30) - 13;
//        ySpeed = random.nextInt(30) - 13;
        xSpeed = 5;
        ySpeed = 5;
        x = random.nextInt(gameView.getWidth() - width);
        y = random.nextInt(gameView.getHeight() - height);
    }

    public abstract void onDraw(Canvas canvas);

    public boolean isCollision(float x, float y) {
//        return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
        return x > this.x - width && x < this.x + width && y > this.y - height && y < this.y + height;
    }

    public boolean isBulletCollision(int x, int y, int width, int height) {
        Rect bullet = new Rect(x, y, x + width, y + height);
        Rect character = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
        return character.intersect(bullet);
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
