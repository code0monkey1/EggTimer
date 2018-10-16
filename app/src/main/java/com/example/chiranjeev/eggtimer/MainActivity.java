package com.example.chiranjeev.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private SeekBar seek;
    private TextView time;
    private MediaPlayer mediaPlayer;
    private long currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        setSeekBarVars();

        setSeekBarProgressChangedAction();

    }

    private void setSeekBarProgressChangedAction() {
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) progress = 1;
                currentTime = progress;
                time.setText("" + currentTime + " : 00");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSeekBarVars() {
        seek.setMax(60);
        seek.setProgress(1);
    }

    private void init() {
        start = findViewById(R.id.start);
        mediaPlayer = MediaPlayer.create(this, R.raw.alert);
        seek = findViewById(R.id.seek);
        time = findViewById(R.id.time);
    }

    public void onStartClick(View view) {

        time = findViewById(R.id.time);

        final long countDownTime = getProcessedTime();

        startCountdownTimer(countDownTime);
    }

    private long getProcessedTime() {
        return currentTime * 1000 * 60;
    }

    private void startCountdownTimer(final long countDownTime) {
        new CountDownTimer(countDownTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                displayTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                countdownEndAction();

            }
        }.start();
    }

    private void countdownEndAction() {

        mediaPlayer.start();

        enableControls(true);
    }

    private void enableControls(boolean b) {
        seek.setEnabled(b);
        start.setEnabled(b);
    }

    private void displayTime(long millisUntilFinished) {

        enableControls(false);

        long minutes = getSeconds(millisUntilFinished, 1000) / 60;
        long seconds = getSeconds(millisUntilFinished, 1000) % 60;


        setCountDownTime(minutes, seconds);
    }

    private void setCountDownTime(long minutes, long seconds) {
        time.setText(minutes + " : " + (seconds > 9 ? seconds : "0" + seconds));
    }

    private long getSeconds(long millisUntilFinished, int i) {
        return millisUntilFinished / i;
    }
}
