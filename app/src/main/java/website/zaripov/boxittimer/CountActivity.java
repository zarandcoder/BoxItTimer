package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    private static String TAG = "xx Count Activity";
    private final long TIME_INTERVAL = 1000;
    private TextView currentState;
    private boolean pauseActivated = false;
    private boolean resumeActivated = false;
    private boolean cancelActivated = false;
    private long millisLeft = 0;

    public CountActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        //Boxing bell sound
        final MediaPlayer bellSound = MediaPlayer.create(this, R.raw.sample);
        final MediaPlayer countDownFiveSecs = MediaPlayer.create(this, R.raw.countdown_sample);
        final TextView timeCounter = findViewById(R.id.textView6);

        Intent intent = getIntent();

        final String fightTimeStr = intent.getStringExtra("fightTime");
        final String fightTimeStrPretty = intent.getStringExtra("fightTimePretty");

        final String restTimeStr = intent.getStringExtra("restTime");
        final String restTimeStrPretty = intent.getStringExtra("restTimePretty");

        final String roundStr = intent.getStringExtra("rounds");

        timeCounter.setText(fightTimeStrPretty);

        final ImageButton playBtn = findViewById(R.id.imageButton2);
        final ImageButton pauseBtn = findViewById(R.id.imageButton);
        pauseBtn.setEnabled(false);
        final ImageButton stopBtn = findViewById(R.id.imageButton3);

        //Event listeners for 3 Buttons
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseActivated = false;
                playBtn.setEnabled(false);
                pauseBtn.setEnabled(true);

                if(fightTimeStr != null && !resumeActivated) {
                    //If user press Pause Button we have to save the time
                    long fightTime = Integer.parseInt(fightTimeStr) * 1000;
                    new CountDownTimer(fightTime, TIME_INTERVAL) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if(pauseActivated || cancelActivated) {
                                cancel();
                            } else {
                                //TODO Parse time as String
                                millisLeft = millisUntilFinished;
                                timeCounter.setText(String.valueOf(millisLeft / 1000));
                                if(timeCounter.getText().toString().equals("6")) {
                                    countDownFiveSecs.start();
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            bellSound.start();
                            TextView currentState = findViewById(R.id.textView5);
                            currentState.setText(R.string.rest);
                        }
                    }.start();
                } else {
                    new CountDownTimer(millisLeft, TIME_INTERVAL) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if(pauseActivated || cancelActivated) {
                                cancel();
                            } else {
                                //TODO Parse time as String
                                millisLeft = millisUntilFinished;
                                timeCounter.setText(String.valueOf(millisLeft / 1000));
                                if(timeCounter.getText().toString().equals("6")) {
                                    countDownFiveSecs.start();
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            bellSound.start();
                            TextView currentState = findViewById(R.id.textView5);
                            currentState.setText(R.string.rest);
                        }
                    }.start();
                }

                bellSound.start();
                currentState = findViewById(R.id.textView5);
                currentState.setText(getString(R.string.fight));
            }
        });


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseActivated = true;
                resumeActivated = true;

                currentState = findViewById(R.id.textView5);
                currentState.setText(getString(R.string.time_paused));

                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelActivated = true;
                Intent intent1 = new Intent(CountActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
