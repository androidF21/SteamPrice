package com.example.csgomoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.csgomoney.adapters.ItemAdapter;
import com.example.csgomoney.models.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG="HomeActivity";
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

        RecyclerView rvItems=findViewById(R.id.rvItems);
        items=new ArrayList<>();

        ItemAdapter itemAdapter=new ItemAdapter(this, items);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://steamcommunity.com/market/search/render/?appid=730&norender=1&count=30", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("Success", TAG);
                JSONObject jsonObject=json.jsonObject;
                try {
                    JSONArray results=jsonObject.getJSONArray("results");
                    for(int i=0;i<results.length();i++){
                        int finalI=i;
                        items.add(new Item(results.getJSONObject(i).getString("name"),
                                results.getJSONObject(i).getString("hash_name"),
                                results.getJSONObject(i).getJSONObject("asset_description").getString("icon_url")
                        ));
                        itemAdapter.notifyDataSetChanged();
                        try {
                            items.get(finalI).setPrice(results.getJSONObject(i).getString("sell_price_text"));
                            itemAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json exception", e);
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("Failure", TAG);
            }
        });

    }

}