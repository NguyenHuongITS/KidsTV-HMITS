package com.example.meowmeow.youtubekids.Model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowmeow.youtubekids.Adapter.LearningVideoAdapter;
import com.example.meowmeow.youtubekids.Adapter.MusicVideoApdater;
import com.example.meowmeow.youtubekids.Interface.LearningVideo;
import com.example.meowmeow.youtubekids.Interface.MusicVideo;
import com.example.meowmeow.youtubekids.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LearningMovie extends AppCompatActivity {

    ImageButton img_recommend,img_explore, img_shows, img_music, img_search;
    CircularImageView img_user;

    private String API_KEYPLAYLIST = "AIzaSyAI6YiDW8IaP6bVYSLTPyih2uNX0PWNyn0";
    private String ID_PLAYLIST = "PLNI3rMEOpVxmWIeT7XEuA0xIQXAyECmuV";

    RecyclerView recyclerView;
    ArrayList<LearningVideo> learningMovieArrayList = new ArrayList<>();
    LearningVideoAdapter learningVideoAdapter;

    // link lấy danh sách video từ playlist id
    public String urlYTB = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId="+ID_PLAYLIST+"&key="+API_KEYPLAYLIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_movie);

        img_user = findViewById(R.id.img_avatar);
        img_recommend = findViewById(R.id.img_recommend);
        img_explore = findViewById(R.id.img_explore);
        img_shows = findViewById(R.id.img_shows);
        img_music = findViewById(R.id.img_music);
        img_search = findViewById(R.id.img_search);

        GetYTBJson(urlYTB);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_learning);

        img_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningMovie.this, RecommendedMovie.class);
                startActivity(intent);
            }
        });
        img_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningMovie.this,ExplorerMovie.class);
                startActivity(intent);
            }
        });
        img_shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningMovie.this,ShowsMovie.class);
                startActivity(intent);
            }
        });
        img_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningMovie.this,MusicMovie.class);
                startActivity(intent);
            }
        });
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LearningMovie.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
    private void GetYTBJson(final String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonItems = response.getJSONArray("items");
                            String title ="";
                            String urlvideo ="";
                            String idvideo = "";
                            for(int i = 0; i < jsonItems.length();i++)
                            {
                                JSONObject jsonObject = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonObject.getJSONObject("snippet");
                                title = jsonSnippet.getString("title");
                                JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                                urlvideo = jsonMedium.getString("url");
                                JSONObject jsonResource = jsonSnippet.getJSONObject("resourceId");
                                idvideo = jsonResource.getString("videoId");

                                learningMovieArrayList.add(new LearningVideo(title,urlvideo,idvideo));
                            }
                            learningVideoAdapter = new LearningVideoAdapter(getApplicationContext(),R.layout.item_custom_video,learningMovieArrayList);

                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(learningVideoAdapter);
                            learningVideoAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MusicMovie.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LearningMovie.this, "Error!!!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
