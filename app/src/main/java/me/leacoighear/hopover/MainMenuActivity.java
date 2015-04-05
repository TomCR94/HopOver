package me.leacoighear.hopover;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Tom on 18/03/2015.
 */
public class MainMenuActivity extends Activity {

    private Button playBtn;
    private Button optionsBtn;
    private TextView highScore;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        this.prefs = getSharedPreferences(getString(R.string.prefs), 0);

        playBtn = (Button) findViewById(R.id.playButton);
        optionsBtn = (Button) findViewById(R.id.OptionsButton);
        highScore = (TextView) findViewById(R.id.menuhighscore);
        highScore.setText("High Score:\n" + prefs.getString(getString(R.string.highscore), "0"));

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("SpecialChar", false);
                startActivity(i);
            }
        });
        playBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("SpecialChar", true);
                startActivity(i);
                return true;
            }
        });
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
    }


}
