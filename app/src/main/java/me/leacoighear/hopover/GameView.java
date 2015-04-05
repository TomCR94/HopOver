package me.leacoighear.hopover;

import android.content.Context;
import android.content.SharedPreferences;
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
    private final Bitmap bmp;
    private final SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    public final Player sprite;
    private final EnemyGround enemyGround;
    private final EnemyAir enemyAir;
    private final Background bg;
    public final boolean SpecialChar;
    public int score = 0, remainingBoost = 10000, lastScore, highScore;
    private boolean paused = false, over = false;
    private GameActivity gameActivity;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    public GameView(Context context, boolean SpecialChar, GameActivity gameActivity) {
        super(context);
        final GameView gameView = this;
        this.SpecialChar = SpecialChar;
        this.gameActivity = gameActivity;
        holder = getHolder();
        this.prefs = getContext().getSharedPreferences(getContext().getString(R.string.prefs), 0);
        this.editor = prefs.edit();
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
        if (remainingBoost < 9980 && !this.isPaused())
            remainingBoost += 15 / getDifficultyMultiplier();
        if (!this.isPaused())
            gameActivity.setBoost(remainingBoost);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int i = 10;
        if (remainingBoost <= 50)
            i = 0;
        while (i > 0) {
            if (event.getX() < getWidth() / 2)
                sprite.jumpHeight -= i;
            else
                sprite.jumpHeight += i;

            i--;
        }
        remainingBoost -= 75 * getDifficultyMultiplier();
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

    public float getDifficultyMultiplier() {
        String difficulty = prefs.getString(getContext().getString(R.string.difficulty), "Medium");
        switch (difficulty) {
            case "Easy":
                return 0.75f;
            case "Medium":
                return 1f;
            case "Hard":
                return 1.5f;
        }
        return 1;
    }

    public void editRemainingBoost(int amt) {
        remainingBoost += amt;
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void gameOver() {
        pause();
        highScore = Integer.parseInt(prefs.getString(getContext().getString(R.string.highscore), "0"));
        lastScore = gameActivity.getScore();
        editor.putString(getContext().getString(R.string.lastscore), "" + lastScore);
        if (highScore < lastScore)
            editor.putString(getContext().getString(R.string.highscore), "" + lastScore);
        editor.commit();
        this.over = true;
    }

    public boolean isOver() {
        return over;
    }

}