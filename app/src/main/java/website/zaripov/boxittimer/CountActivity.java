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
    private long millisLeftFighting = 0;
    private long millisLeftResting = 0;

    private boolean isPauseActivated = false;
    private boolean isCancelActivated = false;
    private boolean isRestTime = false;

    private TextView currentState;

    private MediaPlayer countDownFiveSecs;
    private MediaPlayer bellSound;
    private TextView timeCounter;
    private TextView roundNo;
    private CountDownTimer timerFight;
    private CountDownTimer timerRest;

    private float fightTimeSec;
    private float restTimeSec;
    private int rounds;


    @Override
    public void onBackPressed() {
        CountActivity.super.onBackPressed();
        if(timerFight != null) {
            timerFight.cancel();
        }
        if(timerRest != null) {
            timerRest.cancel();
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "CountActivity: onCreate() started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        final Intent intent = getIntent();

        final ImageButton playBtn = findViewById(R.id.id_image_play_button);
        final ImageButton pauseBtn = findViewById(R.id.id_image_pause_button);
        pauseBtn.setEnabled(false);
        final ImageButton stopBtn = findViewById(R.id.id_image_stop_button);

        //Boxing bell sound effect
        bellSound = MediaPlayer.create(this, R.raw.sample);
        // Countdown sound effect
        countDownFiveSecs = MediaPlayer.create(this, R.raw.countdown_sample);
        // Main time counter textview
        timeCounter = findViewById(R.id.id_current_round_time_text);
        // Current state - Fight, Rest, Finished...
        currentState = findViewById(R.id.id_current_status_text);

        roundNo = findViewById(R.id.id_current_round);

        fightTimeSec = intent.getFloatExtra("fightTime", 0);
        restTimeSec = intent.getFloatExtra("restTime", 0);
        rounds = intent.getIntExtra("rounds", 0);

        millisLeftFighting = (long) (fightTimeSec * 60 * 1000);
        millisLeftResting = (long) (restTimeSec * 60 * 1000);

        String timeCounterStr = intent.getStringExtra("sFightTime");
        timeCounter.setText(timeCounterStr);

        //Round X of Y
        String s = getResources().getString(R.string.round);
        String roundStr = String.format(s, currRound, rounds);
        roundNo.setText(roundStr);

        /*********************Event listeners for 3 Buttons***************************************/
        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "CountActivity: playBtn clicked");
                bellSound.start();

                isPauseActivated = false;

                playBtn.setEnabled(false);
                pauseBtn.setEnabled(true);

                // Countdown timer starts
                if(!isRestTime) {
                    startFight();
                } else {
                    startRest();
                }
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "CountActivity: pauseBtn clicked");
                bellSound.pause();
                if(millisLeftFighting/1000 < 6 || millisLeftResting/1000 < 6) {
                    countDownFiveSecs.pause();
                }

                isPauseActivated = true;

                currentState.setText(getString(R.string.time_paused));

                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "CountActivity: stopBtn clicked");
                isCancelActivated = true;
                isPauseActivated = false;

                playBtn.setEnabled(false);
                pauseBtn.setEnabled(false);
                stopBtn.setEnabled(false);

                final Intent intent1 = new Intent(CountActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        /*********************End Event listeners for 3 Buttons***************************************/
    }


    private void startFight() {
        // Set title to "Fight!"
        currentState.setText(getString(R.string.fight));

        // Set current fight round title
        String s = getResources().getString(R.string.round);
        String roundStr = String.format(s, currRound, rounds);
        roundNo.setText(roundStr);

        //If user press Pause Button we have to save the time
        timerFight = new CountDownTimer(millisLeftFighting, TIME_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isPauseActivated || isCancelActivated) {
                    cancel();
                } else {
                    millisLeftFighting = millisUntilFinished;
                    String timeLeft = renderMinsAndSecs(millisLeftFighting);
                    timeCounter.setText(timeLeft);
                    if (millisLeftFighting/1000 == 6) {
                        countDownFiveSecs.start();
                    }
                }
            }

            @Override
            public void onFinish() {
                millisLeftFighting = (long) (fightTimeSec * 60 * 1000);
                isRestTime = true;
                startRest();
            }
        }.start();
    }

    private void startRest() {
        // Set title to "Rest!"
        currentState.setText(R.string.rest);

        if(currRound == rounds) {
            currentState.setText(getString(R.string.finish));
            finish();
        } else {
            timerRest = new CountDownTimer(millisLeftResting, TIME_INTERVAL) {

                @Override
                public void onTick(long millisUntilFinished) {
                    if (isPauseActivated || isCancelActivated) {
                        cancel();
                    } else {
                        millisLeftResting = millisUntilFinished;
                        String timeLeft = renderMinsAndSecs(millisLeftResting);
                        timeCounter.setText(timeLeft);
                        if (millisLeftResting/1000 == 6) {
                            countDownFiveSecs.start();
                        }
                    }
                }

                @Override
                public void onFinish() {
                    millisLeftResting = (long) (restTimeSec * 60 * 1000);
                    bellSound.start();
                    currRound++;
                    isRestTime = false;
                    startFight();
                }
            }.start();
        }
    }

    /**
     * Helper function to pretify milisecons to minutes and seconds
     * and return them formated to render on the screen.
     */
    private String renderMinsAndSecs(long millis) {
        String minutes = String.valueOf(((millis / 1000) % 3600) / 60);
        String seconds = String.valueOf((millis / 1000) % 60);

        if(seconds.length() < 2) {
            seconds = "0" + seconds;
        }
        return String.format("%s:%s", minutes, seconds);
    }
}
