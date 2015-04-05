package me.leacoighear.hopover;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends Activity implements View.OnClickListener {

    private GameView gameView;
    private boolean specialChar;
    private ImageView boost;
    private TextView score;
    private int remainingBoost = 0, scoreNo = 0;
    final Handler myHandler = new Handler();
    private Dialog pauseDialog, gameOverDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = getIntent();
        specialChar = i.getExtras().getBoolean("SpecialChar");


        pauseDialog = new Dialog(this);
        pauseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pauseDialog.setContentView(R.layout.pausedialog);
        pauseDialog.setCancelable(false);
        Button button = (Button) pauseDialog.findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.unPause();
                pauseDialog.dismiss();
            }
        });

        gameOverDialog = new Dialog(this);
        gameOverDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameOverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gameOverDialog.setContentView(R.layout.gameoverdialog);
        gameOverDialog.setCancelable(false);
        Button menuButton = (Button) gameOverDialog.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(i);
                finish();
            }
        });


        FrameLayout game = new FrameLayout(this);
        RelativeLayout gameWidgets = new RelativeLayout(this);

        score = new TextView(this);
        score.setText("0");
        score.setPadding(30, 30, 30, 30);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        score.setLayoutParams(textParams);

        Button endGameButton = new Button(this);
        boost = new ImageView(this);
        boost.setImageResource(R.drawable.barboost);
        endGameButton.setPadding(30, 30, 30, 30);
        boost.setPadding(30, 30, 30, 30);

        RelativeLayout.LayoutParams boostParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        boostParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        boost.setLayoutParams(boostParams);


        endGameButton.setWidth(60);
        endGameButton.setX(30);
        endGameButton.setY(30);
        endGameButton.setText("||");
        endGameButton.setBackground(getResources().getDrawable(R.drawable.buttoncustom));
        gameWidgets.addView(endGameButton);
        gameWidgets.addView(boost);
        gameWidgets.addView(score);


        gameView = new GameView(this, specialChar, this);

        game.addView(gameView);
        game.addView(gameWidgets);
        setContentView(game);
        endGameButton.setOnClickListener(this);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateGUI();
            }
        }, 0, 100);


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
        gameView.pause();
        pauseDialog.show();
    }

    private void UpdateGUI() {
        //tv.setText(String.valueOf(i));
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            boost.setImageLevel(remainingBoost);
            score.setText("" + scoreNo);
            if (gameView.isOver())
                gameOverDialog.show();
        }
    };

    public void setBoost(int boost) {
        this.remainingBoost = boost;
    }

    public void incrementScore() {
        scoreNo++;
    }

}

