package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "xx Main Activity";

    private EditText mfightTime;
    private EditText mrestTime;
    private EditText mnumRounds;

    private ImageButton mPlay;
    private ImageButton mPause;
    private ImageButton mStop;



    private int fightTime;
    private int restTime;
    private int numRounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfightTime = this.findViewById(R.id.editText);
        mrestTime = this.findViewById(R.id.editText2);
        mnumRounds = this.findViewById(R.id.editText3);

        mPlay = (ImageButton) this.findViewById(R.id.imageButton2);
        final MediaPlayer bing = MediaPlayer.create(this, R.raw.sample);

        mPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bing.start();
            }
        });

        /*
        fightTime = Integer.parseInt(mfightTime.getText().toString());
        restTime = Integer.parseInt(mrestTime.getText().toString());
        numRounds = Integer.parseInt(mnumRounds.getText().toString());
        */

        Log.d(TAG, mfightTime.getText().toString());
        Log.d(TAG, mrestTime.getText().toString());
        Log.d(TAG, mnumRounds.getText().toString());



    }
}
