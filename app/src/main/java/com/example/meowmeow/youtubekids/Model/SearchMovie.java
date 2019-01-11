package com.example.meowmeow.youtubekids.Model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.example.meowmeow.youtubekids.Adapter.DataAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowmeow.youtubekids.Adapter.SearchVideoAdapter;
//import com.example.meowmeow.youtubekids.Interface.RequestInterface;
import com.example.meowmeow.youtubekids.Interface.SearchVideo;
//import com.example.meowmeow.youtubekids.JSONResponse;
import com.example.meowmeow.youtubekids.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchMovie extends AppCompatActivity {

    ImageButton imageBack;

    public Toolbar toolbar;
    private String API_KEYPLAYLIST = "AIzaSyAI6YiDW8IaP6bVYSLTPyih2uNX0PWNyn0";
    //private String ID_PLAYLIST = "PLVOZ_45NZhu58nzy9VyRjsuEVlrIzgnqW";
    //private String Base_URL = "https://www.googleapis.com/youtube/v3/";
    public String searches = "";
   // public String urlYTB = Base_URL + "search?part=snippet&q=soccer&type=playlist"+"&key=" + API_KEYPLAYLIST;
    private RecyclerView mRecyclerView;
    private ArrayList<SearchVideo> searchVideoList = new ArrayList<>();
    private SearchVideoAdapter searchVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        imageBack = findViewById(R.id.img_back);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SearchMovie.this,RecommendedMovie.class);
                startActivity(intent);
                finish();
            }
        });

       // searches = toolbar.getTitle().toString();
//        GetYTBJson(query);

    }

    private void GetYTBJson(String keyword) {

        String url ="https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + keyword + "&maxResults=10&type=video&key="+API_KEYPLAYLIST;
        Log.d("BBB",url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonItems = response.getJSONArray("items");
                            String title = "";
                            String urlvideo = "";
                            String idvideo = "";
                            for (int i = 0; i < jsonItems.length(); i++) {
                                JSONObject jsonObject = jsonItems.getJSONObject(i);
                                JSONObject jsonId = jsonObject.getJSONObject("id");
                                JSONObject jsonSnippet = jsonObject.getJSONObject("snippet");
                                title = jsonSnippet.getString("title");
                                JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                                urlvideo = jsonMedium.getString("url");
//                                JSONObject jsonResource = jsonId.getJSONObject("id");
                                idvideo = jsonId.getString("videoId");

                                searchVideoList.add(new SearchVideo(title, urlvideo, idvideo));
                            }

                            searchVideoAdapter = new SearchVideoAdapter(getApplicationContext(), R.layout.item_custom_video, searchVideoList);
                            mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setHasFixedSize(true);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(searchVideoAdapter);

                            searchVideoAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(SearchMovie.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchMovie.this, "Error!!!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    //color text search
    @SuppressLint("ResourceAsColor")


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        getMenuInflater().inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.search_menu,menu);

        final MenuItem search = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        SearchView.SearchAutoComplete searchAutoComplete =
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(R.color.colorPrimary);
        searchAutoComplete.setTextColor(R.color.colorPrimary);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searches = query;
                Log.d("BBB",searches);
                GetYTBJson(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                try{
                    searchVideoAdapter.getFilter().filter(newText);
                    return false;
                }
                catch (Exception ex){
                    return  true;
                }
            }
        });
        return true;
    }
}
