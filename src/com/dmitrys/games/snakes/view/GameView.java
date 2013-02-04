package com.dmitrys.games.snakes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.dmitrys.games.snakes.R;
import com.dmitrys.games.snakes.manager.GameThread;
import com.dmitrys.games.snakes.view.hero.Enemy;
import com.dmitrys.games.snakes.view.hero.GameCharacter;
import com.dmitrys.games.snakes.view.hero.Hero;
import com.dmitrys.games.snakes.view.weapon.Bullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Administrator
 * Date: 02.02.13
 * Time: 11:20
 */
public class GameView extends SurfaceView {

    private SurfaceHolder holder;

    private GameThread gameThread;

    private List<GameCharacter> heroes = new ArrayList<GameCharacter>();
    private List<GameCharacter> enemies = new ArrayList<GameCharacter>();


    private long gameTime = 0;
    private long lastClickTime = 0;

    public int shotX;
    public int shotY;
    public int counter = 0;

    public GameView(Context context) {
        super(context);

        gameThread = new GameThread(this);

        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createHero();
                createEnemies();
                gameThread.setIsRunning(true);
                gameThread.start();
                gameTime = System.currentTimeMillis();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameThread.setIsRunning(false);
                while (retry) {
                    try {
                        gameThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        System.out.println("[ERROR] [GameView] Unexpected error.");
                    }
                }
            }
        });
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();

        if (enemies.size() < 3 ) {
            createEnemies();
        }

        if (System.currentTimeMillis() - gameTime > 30000 && enemies.size() < 5) {
            createEnemies();
            gameTime = System.currentTimeMillis();
        }

        for (GameCharacter hero : heroes) {
            hero.onDraw(canvas);
        }

        for (GameCharacter hero : enemies) {
            hero.onDraw(canvas);
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        paint.setTextSize(25);
        canvas.drawText("Score: " + this.counter, 25, 40, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("clear score", this.getWidth() - 50, 40, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (System.currentTimeMillis() - lastClickTime <= 300)
//            return true;

        lastClickTime = System.currentTimeMillis();
        synchronized (getHolder()) {
            shotX = (int) event.getX();
            shotY = (int) event.getY();

            if ((event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_CANCEL) &&
                    shotX >= 400 && shotY <= 40) {
                this.counter = 0;
                return true;
            }

            for (int i = heroes.size() - 1; i >= 0; i--) {
                Hero hero = (Hero) heroes.get(i);
//                if (hero.isCollision(event.getX(), event.getY())) {
                    hero.addBullet(createBullet(R.drawable.bullet, hero.getX(), hero.getY()));
//                }
            }
            for (int i = enemies.size() - 1; i >= 0; i--) {
                GameCharacter hero = enemies.get(i);
                if (hero.isEnemy() && hero.isCollision(event.getX(), event.getY())) {
                    enemies.remove(hero);
                    break;
                }
            }
        }
        return true;
    }

    public List<GameCharacter> getHeroes() {
        return heroes;
    }

    public List<GameCharacter> getEnemies() {
        return enemies;
    }

    private GameCharacter createHero(int resourceId) {
        return new Hero(this, resourceId);
    }

    private GameCharacter createEnemy(int resourceId) {
        return new Enemy(this, resourceId);
    }

    private void createHero() {
        heroes.add(createHero(R.drawable.hero));
    }

    private void createEnemies() {
        enemies.add(createHero(R.drawable.hero));

//        enemies.add(createEnemy(R.drawable.ghost));
//        enemies.add(createEnemy(R.drawable.ghost));
//        enemies.add(createEnemy(R.drawable.ghost));
//        enemies.add(createEnemy(R.drawable.ghost));
//        enemies.add(createEnemy(R.drawable.ghost));
    }

    public Bullet createBullet(final int resourceId, int x, int y) {
        return new Bullet(this, resourceId, x, y);
    }
}
