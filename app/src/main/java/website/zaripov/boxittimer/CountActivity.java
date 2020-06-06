package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    private static String TAG = "xx Count Activity";
    private final long TIME_INTERVAL = 1000;
    private long millisLeft = 0;
    private boolean pauseActivated = false;
    private boolean resumeActivated = false;
    private boolean cancelActivated = false;
    private TextView currentState;

    public CountActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "CountActivity: onCreate() started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        //Boxing bell sound
        final MediaPlayer bellSound = MediaPlayer.create(this, R.raw.sample);
        final MediaPlayer countDownFiveSecs = MediaPlayer.create(this, R.raw.countdown_sample);
        final TextView timeCounter = findViewById(R.id.textView6);
        final TextView roundNo = findViewById(R.id.textView10);

        final Intent intent = getIntent();
        final int fightTime = intent.getIntExtra("fightTime", 3);
        final int restTime = intent.getIntExtra("restTime", 1);
        final int round = intent.getIntExtra("rounds", 5);

        String timeCounterStr = fightTime + ":00";
        timeCounter.setText(timeCounterStr);

        String roundStr = roundNo.getText().toString() + "1";
        roundNo.setText(roundStr);


        final ImageButton playBtn = findViewById(R.id.imageButton2);
        final ImageButton pauseBtn = findViewById(R.id.imageButton);
        pauseBtn.setEnabled(false);
        final ImageButton stopBtn = findViewById(R.id.imageButton3);

        //Event listeners for 3 Buttons
        playBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                Log.d(TAG, "CountActivity: playBtn clicked");

                pauseActivated = false;

                playBtn.setEnabled(false);
                pauseBtn.setEnabled(true);

                bellSound.start();
                currentState = findViewById(R.id.textView5);
                currentState.setText(getString(R.string.fight));

                if (!resumeActivated) {
                    //If user press Pause Button we have to save the time
                    long lFightTime = fightTime * 60 * 1000; //From min to milisec
                    new CountDownTimer(lFightTime, TIME_INTERVAL) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (pauseActivated || cancelActivated) {
                                cancel();
                            } else {
                                millisLeft = millisUntilFinished;
                                String timeLeft = renderMinsAndSecs(millisLeft);
                                timeCounter.setText(timeLeft);
                                if (timeLeft.equals("00:06")) {
                                    countDownFiveSecs.start();
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            timeCounter.setText("00:00");
                            currentState = findViewById(R.id.textView5);
                            currentState.setText(R.string.rest);
                            bellSound.start();
                        }
                    }.start();
                } else {
                    new CountDownTimer(millisLeft, TIME_INTERVAL) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (pauseActivated || cancelActivated) {
                                cancel();
                            } else {
                                millisLeft = millisUntilFinished;
                                String timeLeft = renderMinsAndSecs(millisLeft);
                                timeCounter.setText(timeLeft);
                                if (timeLeft.equals("00:06")) {
                                    countDownFiveSecs.start();
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            String restTimeStrPretty = restTime + ":00";
                            timeCounter.setText(restTimeStrPretty);
                            currentState = findViewById(R.id.textView5);
                            currentState.setText(R.string.rest);
                            bellSound.start();
                        }
                    }.start();
                }
            }
        });


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CountActivity: pauseBtn clicked");

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
                Log.d(TAG, "CountActivity: stopBtn clicked");
                cancelActivated = true;
                final Intent intent1 = new Intent(CountActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private String renderMinsAndSecs(long millis) {
        String minutes = String.valueOf(((millis / 1000) % 3600) / 60);
        String seconds = String.valueOf((millis / 1000) % 60);

        if(minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        if(seconds.length() < 2) {
            seconds = "0" + seconds;
        }
        return String.format("%s:%s", minutes, seconds);
    }
}
