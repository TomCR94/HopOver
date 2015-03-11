package me.leacoighear.hopover;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
/**
 * Created by Tom on 05/03/2015.
 */

public class GameView extends SurfaceView {
    private Bitmap bmp, bgbmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private int y = 0;
    private int jump = 0;
    private int xSpeed = 1;
    public Player sprite;
    private Background bg;

    public GameView(Context context) {
        super(context);
        final GameView gameView = this;
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
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.p2_front);
        bgbmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        sprite = new Player(this,bmp);
        bg = new Background(this,bgbmp,context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        bg.onDraw(canvas);
        sprite.onDraw(canvas);
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
}