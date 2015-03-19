package me.leacoighear.hopover;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;


public class GameActivity extends Activity {

    private GameView gameView;
    private boolean specialChar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = getIntent();
        specialChar = i.getExtras().getBoolean("SpecialChar");
        gameView = new GameView(this, specialChar);
        setContentView(gameView);
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
}

