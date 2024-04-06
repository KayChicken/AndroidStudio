package ru.mirea.kainov.audioplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import ru.mirea.kainov.audioplayer.databinding.ActivityAudioBinding;

public class AudioActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ActivityAudioBinding binding;
    private ImageView playButton;
    private TextView time;
    private boolean isPlaying = false;
    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAudioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        time = binding.time;
        mediaPlayer = MediaPlayer.create(this , R.raw.music_1);
        playButton = binding.playPauseButton;
        seekbar = binding.seekbar;
        int duration = mediaPlayer.getDuration();
        seekbar.setMax(duration);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    playButton.setImageResource(R.drawable.play);
                    isPlaying = false;
                } else {
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.pause);
                    isPlaying = true;
                }
            }
        });

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int totalDuration = mediaPlayer.getDuration();

                    // Форматирование времени в формат ММ:СС
                    String currentTime = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(currentPosition),
                            TimeUnit.MILLISECONDS.toSeconds(currentPosition) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPosition))
                    );

                    // Установка текста с текущим временем
                    time.setText(currentTime);
                    seekbar.setProgress(currentPosition);
                    // Повторный вызов этого метода каждые 1000 миллисекунд (1 секунда)
                    handler.postDelayed(this, 1000);
                }
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
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
