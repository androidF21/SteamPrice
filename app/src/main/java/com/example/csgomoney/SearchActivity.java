package com.example.csgomoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.csgomoney.adapters.ItemAdapter;
import com.example.csgomoney.models.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Headers;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "SearchActivity";
    List<Item> items;
    EditText userQueryInput;
    ProgressBar loadingSearch;
    ImageButton searchButton;
    Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        userQueryInput = findViewById(R.id.etQuery);
        searchButton = findViewById(R.id.btnSearch);
        loadingSearch = findViewById(R.id.loadingSearch);
        loadingSearch.setVisibility(View.GONE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        myThread.interrupt();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        myThread.interrupt();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                }
                return false;
            }
        });

        RecyclerView rvItems = findViewById(R.id.rvItems);
        items = new ArrayList<>();

        ItemAdapter itemAdapter = new ItemAdapter(this, items);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myThread != null) {
                    if (myThread.isAlive()) {
                        myThread.interrupt();
                    }
                }
                loadingSearch.setVisibility(View.VISIBLE);
                items.clear();
                String userQuery = userQueryInput.getText().toString();
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("https://steamcommunity.com/market/search/render/?query="+userQuery+"&category_730_ItemSet%5B%5D=any&category_730_ProPlayer%5B%5D=any&category_730_StickerCapsule%5B%5D=any&category_730_TournamentTeam%5B%5D=any&category_730_Weapon%5B%5D=any&appid=730&norender=1", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d("Success", TAG);
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                int finalI = i;
                                String nameColor=results.getJSONObject(i).getJSONObject("asset_description").getString("name_color");
                                String finalColor="";
                                if (nameColor.equals("D2D2D2")){
                                    finalColor="ffffff";
                                }
                                else {
                                    finalColor=nameColor;
                                }
                                String nameWcolor = "<font color='#" + finalColor + "'>" + results.getJSONObject(i).getString("name") + "</font>";
                                items.add(new Item(nameWcolor,
                                        results.getJSONObject(i).getString("hash_name"),
                                        results.getJSONObject(i).getJSONObject("asset_description").getString("icon_url")
                                ));
                                itemAdapter.notifyDataSetChanged();
                                try {
                                    items.get(finalI).setPrice(results.getJSONObject(i).getString("sale_price_text"));
                                    itemAdapter.notifyDataSetChanged();
                                    loadingSearch.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            myThread =new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        for (int i = 0; i < items.size(); i++) {
                                            Log.v(TAG, "Attempting to get description...");
                                            getDescriptions(i);
                                            Thread.sleep(3100);
                                        }
                                    } catch (Exception e) {

                                    }
                                }
                            });
                            myThread.start();

                        } catch (JSONException e) {
                            Log.e(TAG, "json exception", e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d("Failure", TAG);
                    }

                    public void getDescriptions(int index) {
                        String hashName= items.get(index).getMarket_hash_name();
                        client.get("https://steamcommunity.com/market/listings/730/"+hashName+"/render?start=0&count=1&currency=1&format=json&norender=1", new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                JSONObject jsonObject = json.jsonObject;
                                try {
                                    JSONObject results = jsonObject.getJSONObject("assets");
                                    results = results.getJSONObject("730");
                                    results = results.getJSONObject("2");
                                    Iterator<String> test = results.keys();
                                    String test2 = "";
                                    JSONObject value = null;
                                    while (test.hasNext()) {
                                        String key = test.next();
                                        try {
                                            value = results.getJSONObject(key);
                                            test2 = value.toString();
                                        } catch (JSONException e){

                                        }
                                    }
                                    JSONArray results2 = value.getJSONArray("descriptions");

                                    String description = "";
                                    for(int j=0;j<results2.length();j++){
                                        if(results2.getJSONObject(j).getString("value").isEmpty()==false) {
                                            if ( j != 6 ) {
                                                if(results2.getJSONObject(j).has("color")) {
                                                    String colorHTML = "";
                                                    colorHTML += Html.fromHtml(results2.getJSONObject(j).getString("color"));
                                                    description+= "<font color='#"+colorHTML+"'>"+results2.getJSONObject(j).getString("value")+"</font>";
                                                    description+="<br>";
                                                }
                                                else {
                                                    description += Html.fromHtml(results2.getJSONObject(j).getString("value"));
                                                    description += "<br>";
                                                }
                                            }
                                            if (j==6 && (results2.getJSONObject(j).has("color"))){
                                                String colorHTML = "";
                                                colorHTML += Html.fromHtml(results2.getJSONObject(j).getString("color"));
                                                description+= "<font color='#"+colorHTML+"'>"+results2.getJSONObject(j).getString("value")+"</font>";
                                                description+="<br>";
                                            }
                                        }
                                    }
                                    items.get(index).setDescription(description);
                                    itemAdapter.notifyDataSetChanged();
                                    Log.v("TAG", "Description Retrieved");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                            }
                        });
                    }
                });
            }
        });


    }
}

