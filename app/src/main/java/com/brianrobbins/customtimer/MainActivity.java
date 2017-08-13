package com.brianrobbins.customtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String StringHRS;
    String StringMINS;
    String StringSECS;
    private EditText entryHrs;
    private EditText entryMins;
    private EditText entrySecs;
    private TextView time;
    private int HRS;
    private int MINS;
    private int SECS;
    private CountDownTimer countDownTimer;
    private Boolean counterIsActive = false;
    private Boolean isPaused = false;
    private MediaPlayer mplayer;


    public void Click_Start(View view) {
        if (mplayer.isPlaying()) {
            mplayer.stop();
        }

        if (counterIsActive == false) {
            if (isPaused != true) {
                entryHrs = (EditText) findViewById(R.id.editTextHrs);
                entryMins = (EditText) findViewById(R.id.editTextMins);
                entrySecs = (EditText) findViewById(R.id.editTextSecs);

                StringHRS = entryHrs.getText().toString();
                StringMINS = entryMins.getText().toString();
                StringSECS = entrySecs.getText().toString();
            } else {
                isPaused = false;
                StringHRS = String.valueOf(HRS);
                StringMINS = String.valueOf(MINS);
                StringSECS = String.valueOf(SECS);
            }
            if (StringHRS.matches("")) {
                HRS = 0;
            } else {
                HRS = Integer.parseInt(StringHRS);
            }


            if (StringMINS.matches("")) {
                MINS = 0;
            } else {
                MINS = Integer.parseInt(StringMINS);
            }
            if (StringSECS.matches("")) {
                SECS = 0;
            } else {
                SECS = Integer.parseInt(StringSECS);
            }

            Update_Timer_Values(StringHRS, StringMINS, StringSECS);
            if (HRS == 0 && MINS == 0 && SECS == 0) {
                // this is to keep buzzer going off in zero zero zero condition
            } else {
                Start_Timer_Countdown();
            }

        }
    }

    public void Update_Timer_Values(String StringHRS, String StringMINS, String StringSECS) {

        String totaltime = "";
        if (HRS < 10) {
            StringHRS = "0" + String.valueOf(HRS);
        } else {
            StringHRS = String.valueOf(HRS);
        }
        if (MINS < 10) {
            StringMINS = "0" + String.valueOf(MINS);
        } else {
            StringMINS = String.valueOf(MINS);
        }
        if (SECS < 10) {
            StringSECS = "0" + String.valueOf(SECS);
        } else {
            StringSECS = String.valueOf(SECS);
        }
        totaltime = StringHRS + ":" + StringMINS + ":" + StringSECS;
        Update_Text_View(totaltime);


    }

    public void Update_Text_View(String totaltime) {
        time = (TextView) findViewById(R.id.time);
        time.setText(totaltime);

    }

    public void updateTimer(int secondsLeft) {
        HRS = secondsLeft / 60 / 60;
        if (HRS < 1) {
            HRS = 0;
        }
        MINS = (secondsLeft - (HRS * 60 * 60)) / 60;
        if (MINS < 1) {
            MINS = 0;
        }
        SECS = secondsLeft - (HRS * 60 * 60) - (MINS * 60);
        Update_Timer_Values(String.valueOf(HRS), String.valueOf(MINS), String.valueOf(SECS));
    }

    public void Start_Timer_Countdown() {
        if (counterIsActive == false) {
            counterIsActive = true;


            countDownTimer = new CountDownTimer((HRS * 60 * 60 * 1000) + (MINS * 60 * 1000) + (SECS * 1000) + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    time.setText("00:00:00");
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();
                    countDownTimer.cancel();
                    counterIsActive = false;
                }
            }.start();
        }

    }

    public void resetTimer() {
        countDownTimer.cancel();
        time.setText("00:00:00");
        counterIsActive = false;
    }

    public void Click_Stop(View view) {
        if (mplayer.isPlaying()) {
            mplayer.stop();
        }
        if (isPaused == true) {
            isPaused = false;
        }
        resetTimer();

    }

    public void Click_Pause(View view) {
        if (isPaused == false) {
            isPaused = true;
            countDownTimer.cancel();
            counterIsActive = false;
        } else {
            Click_Start(view);
            isPaused = false;
        }


    }

    public void Click_Clear(View view) {

        Click_Stop(view);
        entryHrs.setText("");
        entryMins.setText("");
        entrySecs.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
    }
}
