package me.leacoighear.hopover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tom on 18/03/2015.
 */
public class MainMenuActivity extends Activity {

    protected Button playBtn;
    protected Button optionsBtn;
    protected Button quitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        playBtn = (Button) findViewById(R.id.playButton);
        optionsBtn = (Button) findViewById(R.id.OptionsButton);
        quitBtn = (Button) findViewById(R.id.QuitButton);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
