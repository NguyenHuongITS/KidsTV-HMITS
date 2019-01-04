package com.example.meowmeow.youtubekids.Model;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.meowmeow.youtubekids.R;
import com.github.siyamed.shapeimageview.CircularImageView;

public class RecommendedMovie extends AppCompatActivity {
    private static final String TAG = "";
    ImageButton img_explore,img_learning, img_shows, img_music, img_search;
    CircularImageView img_user;
    //key youtube:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_movie);
        img_user = findViewById(R.id.img_avatar);
        img_explore = findViewById(R.id.img_explore);
        img_learning = findViewById(R.id.img_learning);
        img_shows = findViewById(R.id.img_shows);
        img_music = findViewById(R.id.img_music);
        img_search = findViewById(R.id.img_search);
        img_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedMovie.this, ExplorerMovie.class);
                startActivity(intent);
            }
        });
        img_learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedMovie.this,LearningMovie.class);
                startActivity(intent);
            }
        });
        img_shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedMovie.this,ShowsMovie.class);
                startActivity(intent);
            }
        });
        img_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedMovie.this,MusicMovie.class);
                startActivity(intent);
            }
        });
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecommendedMovie.this, UserActivity.class);
                startActivity(intent);
            }
        });
        
        CamBienAndroid();
        
    }

    private void CamBienAndroid() {
        final SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        final SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                // More code goes here
                if(proximitySensor == null) {
                    Log.e(TAG, "Proximity sensor not available.");
                    finish(); // Close app
                }
//                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
//                    // Detected something nearby
//                    getWindow().getDecorView().setBackgroundColor(Color.RED);
//                } else {
//                    // Nothing is nearby
//                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
//                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorEventListener, proximitySensor, 2 * 1000 * 1000);
    }
}
