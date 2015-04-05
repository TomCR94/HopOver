package me.leacoighear.hopover;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Canvas;

/**
 * Created by Tom on 05/03/2015.
 */
public class GameLoopThread extends Thread {

    private SharedPreferences sharedPreferences;
    private int FPS = 30;
    private final GameView view;
    private boolean running = false;

    public GameLoopThread(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public boolean isRunning() {
        return running;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        this.sharedPreferences = view.getContext().getSharedPreferences(view.getContext().getString(R.string.prefs), 0);
        FPS = Integer.parseInt(sharedPreferences.getString(view.getContext().getString(R.string.fps), "30"));
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        }
    }

    public int getFPS() {
        return FPS;
    }

}