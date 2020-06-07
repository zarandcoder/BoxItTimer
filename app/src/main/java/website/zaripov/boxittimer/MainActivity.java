package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "xx Main Activity";

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
        String sFightTime = fightTimeBar.getProgress() + ":00";
        fightTimeText.setText(sFightTime);

        final TextView restTimeText = findViewById(R.id.id_rest_time_text);
        String sRestTime = restTimeBar.getProgress() + ":00";
        restTimeText.setText(sRestTime);

        final TextView roundsText = findViewById(R.id.id_round_text);
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


        Button startBtn = findViewById(R.id.id_start_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check values first
                Intent intent = new Intent(MainActivity.this, CountActivity.class);
                intent.putExtra("fightTime", fightTimeBar.getProgress());
                intent.putExtra("restTime", restTimeBar.getProgress());
                intent.putExtra("rounds", roundsBar.getProgress());

                startActivity(intent);
            }
        });
    }
}
