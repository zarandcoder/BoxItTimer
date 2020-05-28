package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    private static String TAG = "xx Count Activity";
    private Long millisLeft = null;
    private Long millisOnPause = null;



    public CountActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        //Boxing bell sound
        final MediaPlayer bellSound = MediaPlayer.create(this, R.raw.sample);
        final TextView timeCounter = findViewById(R.id.textView6);

        Intent intent = getIntent();

        final String fightTimeStr = intent.getStringExtra("fightTime");
        final String fightTimeStrPretty = intent.getStringExtra("fightTimePretty");

        final String restTimeStr = intent.getStringExtra("restTime");
        final String restTimeStrPretty = intent.getStringExtra("restTimePretty");

        final String roundStr = intent.getStringExtra("rounds");



        timeCounter.setText(fightTimeStrPretty);

        ImageButton playBtn = findViewById(R.id.imageButton2);
        ImageButton pauseBtn = findViewById(R.id.imageButton);
        ImageButton stopBtn = findViewById(R.id.imageButton3);

        final CountDownTimer timer;
        if(fightTimeStr != null) {

            //If user press Pause Button we have to save the time
            long fightTime;
            if(millisOnPause == null) {
               fightTime = Integer.parseInt(fightTimeStr) * 1000;
            } else fightTime = millisOnPause;

            timer = new CountDownTimer(fightTime,
                    1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    //TODO Parse time as String
                    millisLeft = millisUntilFinished;
                    timeCounter.setText(String.valueOf(millisLeft / 1000));
                }

                @Override
                public void onFinish() {
                    TextView currentState = findViewById(R.id.textView5);
                    currentState.setText(R.string.rest);
                    bellSound.start();
                }
            };
        } else throw new NullPointerException("Problem occured");


        //Event listeners for 3 Buttons
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bellSound.start();
                TextView currentState = findViewById(R.id.textView5);
                currentState.setText(getString(R.string.fight));
                timer.start();
            }
        });


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView currentState = findViewById(R.id.textView5);
                millisOnPause = millisLeft;
                timer.cancel();
                currentState.setText(getString(R.string.time_paused));
            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent intent1 = new Intent(CountActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
