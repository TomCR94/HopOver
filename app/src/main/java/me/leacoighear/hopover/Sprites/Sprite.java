package me.leacoighear.hopover.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import me.leacoighear.hopover.GameView;

/**
 * Created by Tom on 08/03/2015.
 */
public class Sprite {
    public int x = 0;
    public int y = 0;
    public int width;
    public int height;
    public GameView gameView;
    public Bitmap bmp;
    /** The source frame of the bitmap that should be drawn */
    protected Rect src;

    /** The destination area that the frame should be drawn to */
    protected Rect dst;

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
    public void draw(Canvas canvas){
        canvas.drawBitmap(bmp, x , y, null);
    }
    public void onDraw(Canvas canvas) {
        update(canvas);
        draw(canvas);
    }

    public Rect getBounds() {
        return new Rect(x, y, x + width, y + height);
    }

    public boolean checkCollision(Sprite sprite) {
        Rect mySprite = this.getBounds();
        Rect myCollisionObject = sprite.getBounds();
        if (mySprite.intersect(myCollisionObject)) return true;
        return false;
    }

    public boolean isOutOfRange(){
        return this.x + width < 0;
    }

    public boolean isOnGround()
    {
        return this.y == 0;
    }

}
