package com.example.meowmeow.youtubekids.Model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.meowmeow.youtubekids.R;
import com.github.siyamed.shapeimageview.CircularImageView;

public class RecommendedMovie extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "";
    private static final int valuesensor = Sensor.TYPE_PROXIMITY *3;
    ImageButton img_explore,img_learning, img_shows, img_music, img_search;
    CircularImageView img_user;

    //Khai báo keyplaylist
    // khai báo keyid
    // link lấy danh sách video từ playlist id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_movie);
        //ánh xạ đến view để hiển thị
        AnhXa();
        //lấy dữ liệu từ youtube

        //sự kiện click của button
        ControlButton();
        //Cảm biến để đo khoảng cách trong android
        CamBienAndroid();
        //lấy dữ liệu từ sharepreferences
        GetPreferences();
    }

    private void AnhXa() {
        img_user = findViewById(R.id.img_avatar);
        img_explore = findViewById(R.id.img_explore);
        img_learning = findViewById(R.id.img_learning);
        img_shows = findViewById(R.id.img_shows);
        img_music = findViewById(R.id.img_music);
        img_search = findViewById(R.id.img_search);
    }

    private void ControlButton() {
        img_user.setOnClickListener(this);
        img_explore.setOnClickListener(this);
        img_music.setOnClickListener(this);
        img_shows.setOnClickListener(this);
        img_learning.setOnClickListener(this);
        img_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_avatar:
                Intent intent = new Intent(RecommendedMovie.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.img_explore:
                Intent intent2 = new Intent(RecommendedMovie.this, ExplorerMovie.class);
                startActivity(intent2);
                break;
            case R.id.img_music:
                Intent intent3 = new Intent(RecommendedMovie.this,MusicMovie.class);
                startActivity(intent3);
                break;
            case R.id.img_shows:
                Intent intent4 = new Intent(RecommendedMovie.this,ShowsMovie.class);
                startActivity(intent4);
                break;
            case R.id.img_learning:
                Intent intent5 = new Intent(RecommendedMovie.this,LearningMovie.class);
                startActivity(intent5);
                break;
            case R.id.img_search:
                break;
        }
    }

    public void CamBienAndroid() {
        final SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor =
                sensorManager.getDefaultSensor(valuesensor);


        final SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                // More code goes here
                if(proximitySensor == null) {
                    Log.e(TAG, "Proximity sensor not available.");
                    finish(); // Close app
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(sensorEventListener, proximitySensor, 2 * 1000 * 1000);
    }

    //lấy dữ liệu bằng sharepreferences
    public void GetPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_name",MODE_PRIVATE);
        String AvataValue = sharedPreferences.getString("avatar","");
        if(!AvataValue.equals("")){
            img_user.setImageBitmap(decodeBase64(AvataValue));
        }
    }

    //hàm lấy hình ảnh thành bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
