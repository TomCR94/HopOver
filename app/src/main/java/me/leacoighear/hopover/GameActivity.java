package me.leacoighear.hopover;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class GameActivity extends Activity implements View.OnClickListener {

    private GameView gameView;
    private boolean specialChar;
    private ImageView boost;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = getIntent();
        specialChar = i.getExtras().getBoolean("SpecialChar");

        FrameLayout game = new FrameLayout(this);
        RelativeLayout gameWidgets = new RelativeLayout(this);
        Button endGameButton = new Button(this);
        boost = new ImageView(this);
        boost.setImageResource(R.drawable.boostbar);
        endGameButton.setPadding(30, 30, 30, 30);
        boost.setPadding(30, 30, 30, 30);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        boost.setLayoutParams(params);


        setBoostPercentage(5000);
        endGameButton.setWidth(60);
        endGameButton.setX(30);
        endGameButton.setY(30);
        endGameButton.setText("||");
        endGameButton.setBackground(getResources().getDrawable(R.drawable.custombutton));
        gameWidgets.addView(endGameButton);
        gameWidgets.addView(boost);




        gameView = new GameView(this, specialChar);

        game.addView(gameView);
        game.addView(gameWidgets);
        setContentView(game);
        endGameButton.setOnClickListener(this);


    }


    @Override
    protected void onPause() {
        super.onPause();
        gameView.StopView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.ResumeView();
    }

    public void onClick(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Game Paused");
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.unPause();
                dialog.dismiss();
            }
        });
        gameView.pause();
        dialog.show();
    }

    public void setBoostPercentage(int amt) {
        boost.setImageLevel(amt);
    }
}

