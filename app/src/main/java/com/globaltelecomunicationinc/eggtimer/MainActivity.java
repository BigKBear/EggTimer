package com.globaltelecomunicationinc.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        controlButton.setText("Go!");
        counterIsActive=false;
    }

    public void updateTimer(int secondsLeft){
        //converting from munites to seconds in the progress bar
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondsString = Integer.toString(seconds);

        if (seconds <= 9){
            secondsString = "0" + secondsString;
        }

        //update the text view
        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    public void controlTimer(View view){
        if (counterIsActive == false){
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controlButton.setText("Stop");

        //Log.i("Button Pressed","Pressed");
        //Start the timer
        //the aditional + 100 is to give the app time to process
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100,1000){

            @Override
            public void onTick(long milisecondsUntilFinish) {
                //get the number of seconds
                updateTimer((int)milisecondsUntilFinish / 1000);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0:00");
                //If we try to play sound in a function then we can not use the this key word you need to use getApplication context
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                mplayer.start();
                //Log.i("FINISH","timer done");
                resetTimer();
            }
        }.start();

        }else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controlButton = (Button)findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        //initial setup 30 seconds
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
