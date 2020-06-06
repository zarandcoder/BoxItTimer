package website.zaripov.boxittimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "xx Main Activity";

    public MainActivity() {}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity: onCreate() started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar fightTimeBar;
        fightTimeBar = findViewById(R.id.seekBar);
        fightTimeBar.setProgress(3);
        fightTimeBar.setMin(1);
        fightTimeBar.setMax(15);

        final SeekBar restTimeBar;
        restTimeBar = findViewById(R.id.seekBar3);
        restTimeBar.setProgress(1);
        restTimeBar.setMin(1);
        restTimeBar.setMax(5);

        final SeekBar roundsBar;
        roundsBar = findViewById(R.id.seekBar4);
        roundsBar.setProgress(5);
        roundsBar.setMin(1);
        roundsBar.setMax(12);


        final TextView fightTimeText = findViewById(R.id.textView7);
        String sFightTime = fightTimeBar.getProgress() + ":00";
        fightTimeText.setText(sFightTime);

        final TextView restTimeText = findViewById(R.id.textView8);
        String sRestTime = restTimeBar.getProgress() + ":00";
        restTimeText.setText(sRestTime);

        final TextView roundsText = findViewById(R.id.textView9);
        roundsText.setText(String.valueOf(roundsBar.getProgress()));

        fightTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String s = progress + ":00";
                fightTimeText.setText(s);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        restTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String s = progress + ":00";
                restTimeText.setText(s);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        roundsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundsText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Button startBtn = findViewById(R.id.button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check values first
                Intent intent = new Intent(MainActivity.this, CountActivity.class);
                intent.putExtra("fightTime", fightTimeBar.getProgress());
                intent.putExtra("restTime", restTimeBar.getProgress());
                intent.putExtra("rounds", roundsBar.getProgress());

                startActivity(intent);
                finish();
            }
        });
    }
}
