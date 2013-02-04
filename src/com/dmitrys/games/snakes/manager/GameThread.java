package com.dmitrys.games.snakes.manager;

import android.graphics.Canvas;
import com.dmitrys.games.snakes.view.GameView;

/**
 * User: Administrator
 * Date: 02.02.13
 * Time: 12:37
 */
public class GameThread extends Thread {

    private GameView view;

    private boolean isRunning = false;
    private static final long FPS = 80L;

    public GameThread(GameView view) {
        this.view = view;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (isRunning) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(canvas);
                }
            } catch (Exception e) {
                System.out.println("[ERROR] [GameThread] Unexpected exception. ");
            } finally {
                if (canvas != null)
                    view.getHolder().unlockCanvasAndPost(canvas);
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (InterruptedException e) {
                System.out.println("[ERROR] [GameThread] Unexpected exception while trying to sleep the game. ");
            }
        }
    }
}
