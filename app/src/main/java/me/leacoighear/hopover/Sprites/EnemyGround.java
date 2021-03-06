package me.leacoighear.hopover.Sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import me.leacoighear.hopover.GameView;
import me.leacoighear.hopover.R;
import me.leacoighear.hopover.Utils;

/**
 * Created by Tom on 18/03/2015.
 */
public class EnemyGround extends Sprite {

    private int distanceMoved = 0;
    private Random rand;
    private Context context;

    public EnemyGround(GameView gameView, Bitmap bmp, Context context) {
        super(gameView, bmp);
        this.context = context;
    }

    @Override
    public void update(Canvas canvas) {
        if (this.distanceMoved % 5 == 0)
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.slimewalkup);
        else
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.slimewalkdown);

        if (gameView.sprite.jumpHeight > 0)
            this.distanceMoved += Utils.getScaledHoriontalMovement(context);
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
            if (gameView.remainingBoost - (2000 * gameView.getDifficultyMultiplier()) > 0)
                gameView.editRemainingBoost((int) (-2000 * gameView.getDifficultyMultiplier()));
            else
                gameView.gameOver();
        }
    }

    @Override
    public Rect getBounds() {
        return new Rect(x, y, x + width * 2 / 3, y + height);
    }

}
