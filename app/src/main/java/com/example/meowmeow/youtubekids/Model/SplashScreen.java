package com.example.meowmeow.youtubekids.Model;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.meowmeow.youtubekids.R;

public class SplashScreen extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        videoView = findViewById(R.id.videoView);
        String uriPath = "android.resource://" + getPackageName() + "/" + "raw/logo"; //
        Uri video = Uri.parse(uriPath);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startNextActivivy();
            }
        });
        videoView.start();
    }

    private void startNextActivivy() {
        if(isFinishing())
            return;
        startActivity(new Intent(SplashScreen.this, RecommendedMovie.class));
        finish();

    }
}
