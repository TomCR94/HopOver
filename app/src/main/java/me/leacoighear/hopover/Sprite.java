package me.leacoighear.hopover;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Tom on 08/03/2015.
 */
public class Sprite {
    public int x = 0;
    public int y = 0;
    private int width;
    private int height;
    private GameView gameView;
    public Bitmap bmp;

    public Sprite() {
        this.gameView = null;
        this.bmp = null;
        height = 0;
        width = 0;
    }

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView=gameView;
        this.bmp=bmp;
        height = bmp.getHeight();
        width = bmp.getWidth();
    }

    public void update(Canvas canvas) {
        x = canvas.getWidth()/2 - bmp.getWidth()/2;
        y = canvas.getHeight() - bmp.getHeight();

    }

    public void onDraw(Canvas canvas) {
        update(canvas);
        canvas.drawBitmap(bmp, x , y, null);
    }

    public boolean isColliding(Sprite sprite) {
        return sprite.x > x && sprite.x < x + width && sprite.y > y && sprite.y < y + height;
    }

}