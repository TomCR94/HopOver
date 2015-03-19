package me.leacoighear.hopover.Sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import me.leacoighear.hopover.GameView;
import me.leacoighear.hopover.R;

/**
 * Created by Tom on 09/03/2015.
 */
public class Player extends Sprite {

    public int jumpHeight = 0;
    public GameView gameView;
    private boolean SpecialChar;

    public Player(GameView gameView, Bitmap bmp, boolean SpecialChar) {
        super(gameView, bmp);
        this.gameView = gameView;
        this.SpecialChar = SpecialChar;
    }

    @Override
    public void update(Canvas canvas) {
        super.update(canvas);
        jumpHeight -= 12;

        if (jumpHeight < 0)
            jumpHeight = 0;

        if (jumpHeight > canvas.getHeight() - this.bmp.getHeight())
            jumpHeight = canvas.getHeight() - this.bmp.getHeight();


        if (this.jumpHeight != 0) {
            if (gameView.SpecialChar)
                this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.p3_jump);
            else
                this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.p2_jump);
        } else {
            if (gameView.SpecialChar)
                this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.p3_front);
            else
                this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.p2_front);
        }


    }

    @Override
    public void onDraw(Canvas canvas) {
        update(canvas);
        canvas.drawBitmap(bmp, x, y - jumpHeight, null);
    }

    @Override
    public Rect getBounds() {
        return new Rect(x, y - jumpHeight, x + width, y + height - jumpHeight);
    }
}
