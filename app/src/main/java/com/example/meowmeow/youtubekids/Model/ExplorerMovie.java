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
import com.example.meowmeow.youtubekids.Adapter.ExploreVideoAdapter;
import com.example.meowmeow.youtubekids.Adapter.MusicVideoApdater;
import com.example.meowmeow.youtubekids.Interface.ExploreVideo;
import com.example.meowmeow.youtubekids.Interface.MusicVideo;
import com.example.meowmeow.youtubekids.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExplorerMovie extends AppCompatActivity {

    ImageButton img_recommend,img_learning, img_shows, img_music, img_search;
    CircularImageView img_user;

    private String API_KEYPLAYLIST = "AIzaSyAI6YiDW8IaP6bVYSLTPyih2uNX0PWNyn0";
    private String ID_PLAYLIST = "PLO21AvsmQDO0JZBwsp2Xe4XvFjqfeyB-R";

    RecyclerView recyclerView;
    ArrayList<ExploreVideo> exploreVideoArrayList = new ArrayList<>();
    ExploreVideoAdapter exploreVideoAdapter;

    // link lấy danh sách video từ playlist id
    public String urlYTB = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId="+ID_PLAYLIST+"&key="+API_KEYPLAYLIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_movie);
        img_user = findViewById(R.id.img_avatar);
        img_recommend = findViewById(R.id.img_recommend);
        img_learning = findViewById(R.id.img_learning);
        img_shows = findViewById(R.id.img_shows);
        img_music = findViewById(R.id.img_music);
        img_search = findViewById(R.id.img_search);

        GetYTBJson(urlYTB);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_explore);

        img_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplorerMovie.this, RecommendedMovie.class);
                startActivity(intent);
            }
        });
        img_learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplorerMovie.this,LearningMovie.class);
                startActivity(intent);
            }
        });
        img_shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplorerMovie.this,ShowsMovie.class);
                startActivity(intent);
            }
        });
        img_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplorerMovie.this,MusicMovie.class);
                startActivity(intent);
            }
        });
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplorerMovie.this, UserActivity.class);
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

                                exploreVideoArrayList.add(new ExploreVideo(title,urlvideo,idvideo));
                            }
                            exploreVideoAdapter = new ExploreVideoAdapter(getApplicationContext(),R.layout.item_custom_video,exploreVideoArrayList);

                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(exploreVideoAdapter);
                            exploreVideoAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MusicMovie.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ExplorerMovie.this, "Error!!!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
