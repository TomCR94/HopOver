package me.leacoighear.hopover;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Tom on 09/03/2015.
 */
public class Background extends Sprite {

    public Background(GameView gameView, Bitmap bmp, Context context)
    {
        super(gameView, Utils.getScaledBitmapAlpha8(context, R.drawable.background));
    }

    @Override
    public void update(Canvas canvas)
    {
        if(gameView.sprite.jumpHeight > 0)
        this.x-=15;
    }

    @Override
    public void draw(Canvas canvas) {
        double factor = (1.0 * canvas.getHeight()) / bmp.getHeight();

        if(-x > bmp.getWidth()){
            // The first bitmap is completely out of the screen
            x += bmp.getWidth();
        }

        int endBitmap = Math.min(-x + (int) (canvas.getWidth() / factor), bmp.getWidth());
        int endCanvas = (int) ((endBitmap + x) * factor) + 1;
        src = new Rect(-x, 0, endBitmap, bmp.getHeight());
        dst = new Rect(0, 0, endCanvas, canvas.getHeight());
        canvas.drawBitmap(this.bmp, src, dst, null);

        if(endBitmap == bmp.getWidth()){
            // draw second bitmap
            src = new Rect(0, 0, (int) (canvas.getWidth() / factor), bmp.getHeight());
            dst = new Rect(endCanvas, 0, endCanvas + canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(bmp, src, dst, null);
        }
    }
}
