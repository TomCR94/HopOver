package me.leacoighear.hopover.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import me.leacoighear.hopover.GameView;

/**
 * Created by Tom on 08/03/2015.
 */
public class Sprite {
    int x = 0;
    int y = 0;
    final int width;
    final int height;
    final GameView gameView;
    Bitmap bmp;
    /** The source frame of the bitmap that should be drawn */
    Rect src;

    /** The destination area that the frame should be drawn to */
    Rect dst;

    Sprite() {
        this.gameView = null;
        this.bmp = null;
        height = 0;
        width = 0;
    }

    Sprite(GameView gameView, Bitmap bmp) {
        this.gameView=gameView;
        this.bmp=bmp;
        height = bmp.getHeight();
        width = bmp.getWidth();
    }

    void update(Canvas canvas) {
        x = canvas.getWidth()/2 - bmp.getWidth()/2;
        y = canvas.getHeight() - bmp.getHeight();

    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bmp, x , y, null);
    }

    public void onDraw(Canvas canvas) {
        if (!gameView.isPaused())
        update(canvas);

        draw(canvas);
    }

    Rect getBounds() {
        return new Rect(x, y, x + width, y + height);
    }

    boolean checkCollision(Sprite sprite) {
        Rect mySprite = this.getBounds();
        Rect myCollisionObject = sprite.getBounds();
        return mySprite.intersect(myCollisionObject);
    }

    boolean isOutOfRange() {
        return this.x + width < 0;
    }

    public boolean isOnGround()
    {
        return this.y == 0;
    }

    public boolean isColliding(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

}
