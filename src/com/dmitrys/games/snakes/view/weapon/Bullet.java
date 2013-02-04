package com.dmitrys.games.snakes.view.weapon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.dmitrys.games.snakes.view.GameView;
import com.dmitrys.games.snakes.view.hero.GameCharacter;

import java.util.List;

/**
 * User: Administrator
 * Date: 03.02.13
 * Time: 17:28
 */
public class Bullet {

    private Bitmap bmp;

    public int x;
    public int y;

    private int mSpeed = 25;

    public double angle;

    public int width;

    public int height;

    public GameView gameView;

    public Bullet(GameView gameView, int resourceId, int x, int y) {
        this.gameView = gameView;
        this.bmp = BitmapFactory.decodeResource(gameView.getResources(), resourceId);

        this.x = x;
        this.y = y;
        this.width = 27;
        this.height = 40;

        angle = Math.atan((double) (this.y - gameView.shotY) / (this.x - gameView.shotX));
        if (this.x - gameView.shotX > 0)
            angle -= Math.PI;
    }

    private void update() {
        this.x += mSpeed * Math.cos(angle);
        this.y += mSpeed * Math.sin(angle);
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, this.x, this.y, null);

        List<GameCharacter> enemies = this.gameView.getEnemies();
        for (int i = enemies.size() - 1; i >= 0; i--) {
            GameCharacter hero = enemies.get(i);
            if (hero.isBulletCollision(this.x, this.y, bmp.getWidth(), bmp.getHeight())) {
                this.gameView.getEnemies().remove(hero);
                this.gameView.counter++;
                break;
            }
        }
    }
}
