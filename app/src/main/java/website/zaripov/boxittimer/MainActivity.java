package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "xx Main Activity";
    private String fightTime;
    private String restTime;
    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity: onCreate() started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar fightTimeBar;
        fightTimeBar = findViewById(R.id.id_fight_time_seekbar);
        //fightTimeBar.setMin(1);

        final SeekBar restTimeBar;
        restTimeBar = findViewById(R.id.id_rest_time_seekbar);
        //restTimeBar.setMin(1);

        final SeekBar roundsBar;
        roundsBar = findViewById(R.id.id_round_seekbar);
        //roundsBar.setMin(1);


        final TextView fightTimeText = findViewById(R.id.id_fight_time_text);
        fightTime = fightTimeBar.getProgress() + ":00";
        fightTimeText.setText(fightTime);

        final TextView restTimeText = findViewById(R.id.id_rest_time_text);
        restTime = restTimeBar.getProgress() + ":00";
        restTimeText.setText(restTime);

        final TextView roundsText = findViewById(R.id.id_round_text);
        roundsText.setText(String.valueOf(roundsBar.getProgress()));

        fightTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress % 2 == 0) {
                    fightTime = progress/2 + ":00";
                } else {
                    fightTime = progress/2 + ":30";
                }
                fightTimeText.setText(fightTime);
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
                if(progress % 2 == 0) {
                    restTime = progress/2 + ":00";
                } else {
                    restTime = progress/2 + ":30";
                }
                restTimeText.setText(restTime);
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

        Button startBtn = findViewById(R.id.id_start_button);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(fightTime.equals("0:00") || restTime.equals("0:00") || roundsBar.getProgress() == 0) {
                    Toast.makeText(getApplicationContext(), "Value can't be 0!", Toast.LENGTH_LONG).show();
                } else {
                    //Check values first
                    final Intent intent = new Intent(MainActivity.this, CountActivity.class);
                    intent.putExtra("fightTime", ((float)fightTimeBar.getProgress())/2);
                    intent.putExtra("restTime", ((float)restTimeBar.getProgress())/2);
                    intent.putExtra("rounds", roundsBar.getProgress());
                    intent.putExtra("sFightTime", fightTime);
                    intent.putExtra("sRestTime", restTime);
                    startActivity(intent);
                }
            }
        });
    }
}
