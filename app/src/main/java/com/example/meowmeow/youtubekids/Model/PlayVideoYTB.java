package com.example.meowmeow.youtubekids.Model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.meowmeow.youtubekids.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoYTB extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    String API_KEYVIDEO = "AIzaSyA2e3uG6u3_fWeh_KNIS4UN5bZPD2FiDxM";

    int REQUEST_VIDEO = 123;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_ytb);

        youTubePlayerView = findViewById(R.id.myYTBView);
        youTubePlayerView.initialize(API_KEYVIDEO, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        String results = getIntent().getExtras().getString("videoId");
        youTubePlayer.cueVideo(results);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(PlayVideoYTB.this, REQUEST_VIDEO );
        }
        else {
            Toast.makeText(this, "Error!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_VIDEO){
            youTubePlayerView.initialize(API_KEYVIDEO, PlayVideoYTB.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
