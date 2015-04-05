package me.leacoighear.hopover;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;


public class SettingsActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        this.sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.prefs), 0);
        this.editor = this.sharedPreferences.edit();

        Spinner fps = (Spinner) findViewById(R.id.fpsspinner);
        fps.setSelection(getIndex(R.array.fps_array, sharedPreferences.getString(getString(R.string.fps), "30")));
        fps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString(getString(R.string.fps), parent.getSelectedItem().toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner difficulty = (Spinner) findViewById(R.id.difficultyspinner);
        difficulty.setSelection(getIndex(R.array.difficulty_array, sharedPreferences.getString(getString(R.string.difficulty), "Medium")));
        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString(getString(R.string.difficulty), parent.getSelectedItem().toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public int getIndex(int ID, String value) {
        String[] array = getResources().getStringArray(ID);
        for (int x = 0; x < array.length; x++) {
            if (array[x].equals(value))
                return x;
        }
        return 0;
    }
}
