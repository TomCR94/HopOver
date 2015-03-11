package me.leacoighear.hopover;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Tom on 09/03/2015.
 */
public class Player extends Sprite{

    public int jumpHeight = 0;

    public Player(GameView gameView, Bitmap bmp)
    {
        super(gameView, bmp);
    }

    @Override
    public void update(Canvas canvas)
    {
        super.update(canvas);

        jumpHeight-=4;

        if (jumpHeight < 0)
            jumpHeight = 0;
    }

    @Override
    public void onDraw(Canvas canvas) {
        update(canvas);
        canvas.drawBitmap(bmp, x , y - jumpHeight, null);
    }

}
