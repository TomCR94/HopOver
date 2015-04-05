package me.leacoighear.hopover.Sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import me.leacoighear.hopover.GameView;
import me.leacoighear.hopover.R;

/**
 * Created by Tom on 18/03/2015.
 */
public class EnemyAir extends Sprite {

    private int distanceMoved = 0;
    private Random rand;

    public EnemyAir(GameView gameView, Bitmap bmp) {
        super(gameView, bmp);
    }

    @Override
    public void update(Canvas canvas) {
        if (this.distanceMoved % 9 == 0)
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fly1);
        else
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fly2);

        if (gameView.sprite.jumpHeight > 0)
            this.distanceMoved += 15;
        rand = new Random();
        x = canvas.getWidth() + bmp.getWidth() - distanceMoved;
        y = canvas.getHeight() / 2 - bmp.getHeight() + rand.nextInt(20);
        distanceMoved += 7;

        if (this.isOutOfRange()) {
            this.distanceMoved = -rand.nextInt(500);
            gameView.getGameActivity().incrementScore();
        }

        if (this.checkCollision(gameView.sprite)) {
            this.distanceMoved = -rand.nextInt(500);
            if (gameView.remainingBoost - (2000 * gameView.getDifficultyMultiplier()) > 0)
                gameView.editRemainingBoost((int) (-2000 * gameView.getDifficultyMultiplier()));
            else
                gameView.gameOver();
        }
    }

    @Override
    public Rect getBounds() {
        return new Rect(x + 17, y, x + width, y + height / 2);
    }

}
