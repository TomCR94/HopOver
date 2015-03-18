package me.leacoighear.hopover;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;


public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameView(this);
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

