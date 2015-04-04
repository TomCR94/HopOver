package me.leacoighear.hopover;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.leacoighear.hopover.Sprites.Background;
import me.leacoighear.hopover.Sprites.EnemyAir;
import me.leacoighear.hopover.Sprites.EnemyGround;
import me.leacoighear.hopover.Sprites.Player;

/**
 * Created by Tom on 05/03/2015.
 */

public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    public GameLoopThread gameLoopThread;
    public Player sprite;
    private EnemyGround enemyGround;
    private EnemyAir enemyAir;
    private Background bg;
    public boolean SpecialChar;
    public int score = 0;
    private boolean paused = false;

    public GameView(Context context, boolean SpecialChar) {
        super(context);
        final GameView gameView = this;
        this.SpecialChar = SpecialChar;
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                gameLoopThread.setRunning(false);
                while (gameLoopThread != null) {
                    try {
                        gameLoopThread.join();
                        gameLoopThread = null;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread = new GameLoopThread(gameView);
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                }
        });
        if (SpecialChar)
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.p3_front);
        else bmp = BitmapFactory.decodeResource(getResources(), R.drawable.p2_front);

        sprite = new Player(this, bmp, SpecialChar);
        enemyGround = new EnemyGround(this, bmp);
        enemyAir = new EnemyAir(this, bmp);
        bg = new Background(this, context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        bg.onDraw(canvas);
        sprite.onDraw(canvas);
        enemyGround.onDraw(canvas);
        enemyAir.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int i = 10;
        while(i > 0) {
            if (event.getX() < getWidth() / 2)
                sprite.jumpHeight -= i;
            else
                sprite.jumpHeight += i;

        i--;
        }
        return true;
    }

    public void StopView() {
        if (gameLoopThread != null) {
            gameLoopThread.setRunning(false);
        }
    }

    public void ResumeView() {
        if (gameLoopThread != null) {
            gameLoopThread.setRunning(true);
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

    public void unPause() {
        paused = false;
    }
}