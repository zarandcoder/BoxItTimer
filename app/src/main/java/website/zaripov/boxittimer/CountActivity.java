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

    public CountActivity() {}

    private int currRound = 1;
    private final long TIME_INTERVAL = 1000;
    private long millisLeft = 0;
    private boolean pauseActivated = false;
    private boolean resumeActivated = false;
    private boolean cancelActivated = false;

    private TextView currentState;

    private MediaPlayer countDownFiveSecs;
    private MediaPlayer bellSound;
    private TextView timeCounter;
    private TextView roundNo;

    private int fightTime;
    private int restTime;
    private int rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "CountActivity: onCreate() started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        //Boxing bell sound effect
        bellSound = MediaPlayer.create(this, R.raw.sample);
        // Countdown sound effect
        countDownFiveSecs = MediaPlayer.create(this, R.raw.countdown_sample);
        // Main time counter textview
        timeCounter = findViewById(R.id.textView6);

        roundNo = findViewById(R.id.textView10);

        final Intent intent = getIntent();
        fightTime = intent.getIntExtra("fightTime", 3);
        restTime = intent.getIntExtra("restTime", 1);
        rounds = intent.getIntExtra("rounds", 5);

        String timeCounterStr = fightTime + ":00";
        timeCounter.setText(timeCounterStr);

        //Round X of Y
        String s = getResources().getString(R.string.round);
        String roundStr = String.format(s, currRound, rounds);
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

                currentState = findViewById(R.id.textView5);
                currentState.setText(getString(R.string.fight));

                // Countdown timer starts
                startFight(fightTime);
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

        if(seconds.length() < 2) {
            seconds = "0" + seconds;
        }
        return String.format("%s:%s", minutes, seconds);
    }

    private void startFight(int fightTime) {

        // Set title to "Fight!"
        currentState.setText(getString(R.string.fight));
        bellSound.start();

        // Set current fight round title
        String s = getResources().getString(R.string.round);
        String roundStr = String.format(s, currRound, rounds);
        roundNo.setText(roundStr);

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
                        if (timeLeft.equals("0:06")) {
                            countDownFiveSecs.start();
                        }
                    }
                }

                @Override
                public void onFinish() {
                    currentState = findViewById(R.id.textView5);
                    currentState.setText(R.string.rest);
                    bellSound.start();
                    startRest(restTime);
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
                        if (timeLeft.equals("0:06")) {
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
                    startRest(restTime);
                }
            }.start();
        }
    }

    private void startRest(int restTime) {

        if(currRound == rounds) {
            currentState.setText(getString(R.string.finish));
            finish();
        } else {
            long lRestTime = restTime * 60 * 1000;
            new CountDownTimer(lRestTime, TIME_INTERVAL) {

                @Override
                public void onTick(long millisUntilFinished) {
                    millisLeft = millisUntilFinished;
                    String timeLeft = renderMinsAndSecs(millisLeft);
                    timeCounter.setText(timeLeft);
                    if (timeLeft.equals("0:06")) {
                        countDownFiveSecs.start();
                    }
                }

                @Override
                public void onFinish() {
                    bellSound.start();
                    currRound++;
                    startFight(fightTime);
                }
            }.start();
        }
    }
}
