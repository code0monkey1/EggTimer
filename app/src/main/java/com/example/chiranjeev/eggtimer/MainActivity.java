package com.example.chiranjeev.eggtimer;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.chiranjeev.eggtimer.R.drawable.egg;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private SeekBar seek;
    private TextView time;
    private MediaPlayer mediaPlayer;
    private ImageView eggPicture;

    private long currentTime;
    private boolean buttonPressed;

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
        eggPicture = findViewById(R.id.eggImage);
    }

    public void onStartClick(View view) {

        if (buttonPressed == false) {
            time = findViewById(R.id.time);

            final long countDownTime = getProcessedTime();

            startCountdownTimer(countDownTime);

            buttonPressed = true;

            start.setText("Stop");
        } else {
            start.setText("Start");
            buttonPressed = false;
        }
    }

    private long getProcessedTime() {
        return currentTime * 1000 * 60;
    }

    private void startCountdownTimer(final long countDownTime) {
        new CountDownTimer(countDownTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                displayTime(millisUntilFinished);
                eggPicture.animate().rotationBy(360);
                terminateIfStopButtonPressed();
            }

            private void terminateIfStopButtonPressed() {
                if (buttonPressed == false) {
                    seek.setEnabled(true);
                    cancel();
                }
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
