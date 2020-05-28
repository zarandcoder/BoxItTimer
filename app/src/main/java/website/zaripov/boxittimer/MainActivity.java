package website.zaripov.boxittimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "xx Main Activity";

    private EditText fightTimeText;
    private EditText restTimeText;
    private EditText roundsText;

    private String fightTimePretty;
    private String fightTime;

    private String restTime;
    private String restTimePretty;

    private String rounds;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fightTimeText = findViewById(R.id.editText);
        restTimeText = findViewById(R.id.editText);
        roundsText = findViewById(R.id.editText);



        Button startBtn = findViewById(R.id.button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check values first
                if(!valuesAreEmpty()) {

                    fightTimePretty = parseMinsAndSecs(fightTimeText.getText().toString());
                    restTimePretty = parseMinsAndSecs(fightTimeText.getText().toString());

                    fightTime = fightTimeText.getText().toString();
                    restTime = restTimeText.getText().toString();
                    rounds = roundsText.getText().toString();

                    Intent intent = new Intent(MainActivity.this, CountActivity.class);
                    intent.putExtra("fightTime", fightTime);
                    intent.putExtra("fightTimePretty", fightTimePretty);
                    intent.putExtra("restTime", restTime);
                    intent.putExtra("restTimePretty", restTimePretty);
                    intent.putExtra("rounds", rounds);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                              "Please set time correctrly",
                                   Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String parseMinsAndSecs(String sec) {
        int secInt = Integer.parseInt(sec);
        String mins = String.valueOf((secInt % 3600) / 60);
        String secs = String.valueOf(secInt % 60);
        if(mins.length() < 2) {
            mins = "0" + mins;
        }

        if(secs.length() < 2) {
            secs = "0" + secs;
        }
        return String.format("%s : %s", mins, secs);
    }

    private boolean valuesAreEmpty() {
        return (fightTimeText.getText().toString().equalsIgnoreCase("") ||
                restTimeText.getText().toString().equalsIgnoreCase("") ||
                roundsText.getText().toString().equalsIgnoreCase(""));
    }
}
