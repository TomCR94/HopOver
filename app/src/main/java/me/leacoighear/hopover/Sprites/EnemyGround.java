package me.leacoighear.hopover.Sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

import me.leacoighear.hopover.GameView;
import me.leacoighear.hopover.R;

/**
 * Created by Tom on 18/03/2015.
 */
public class EnemyGround extends Sprite {

    private int distanceMoved = 0;
    private Random rand;

    public EnemyGround(GameView gameView, Bitmap bmp) {
        super(gameView, bmp);
    }

    @Override
    public void update(Canvas canvas) {
        if (this.distanceMoved % 5 == 0)
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.slimewalkup);
        else
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.slimewalkdown);

        if (gameView.sprite.jumpHeight > 0)
            this.distanceMoved += 15;
        rand = new Random();
        x = canvas.getWidth() + bmp.getWidth() - distanceMoved;
        y = canvas.getHeight() - bmp.getHeight();
        distanceMoved += 1 + rand.nextInt(3);

        if (this.isOutOfRange()) {
            this.distanceMoved = -rand.nextInt(500);
            gameView.getGameActivity().incrementScore();
        }

        if (this.checkCollision(gameView.sprite)) {
            this.distanceMoved = -rand.nextInt(500);
            if (gameView.remainingBoost > -2000 * gameView.getDifficultyMultiplier())
                gameView.editRemainingBoost((int) (-2000 * gameView.getDifficultyMultiplier()));
            else
                gameView.gameOver();
        }
    }

}
