package com.example.csgomoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.csgomoney.adapters.ItemAdapter;
import com.example.csgomoney.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG="ProfileActivity";
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        RecyclerView rvItems=findViewById(R.id.rvItems);
        items=new ArrayList<>();

        ItemAdapter itemAdapter=new ItemAdapter(this, items);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://steamcommunity.com/inventory/76561198174398625/730/2", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("Success", TAG);
                JSONObject jsonObject=json.jsonObject;
                try {
                    JSONArray results=jsonObject.getJSONArray("descriptions");
                    for(int i=0;i<results.length();i++){
                        if(results.getJSONObject(i).getInt("marketable")==1) {
                            items.add(new Item(results.getJSONObject(i).getString("name"),
                                    results.getJSONObject(i).getString("market_hash_name"),
                                    results.getJSONObject(i).getString("icon_url")
                            ));
                            itemAdapter.notifyDataSetChanged();
                        }
                    }
                    for(int i=0;i<items.size();i++){
                        int finalI = i;
                        client.get("https://steamcommunity.com/market/priceoverview/?appid=730&currency=1&market_hash_name=" + items.get(i).getMarket_hash_name(), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                JSONObject jsonObject=json.jsonObject;
                                try {
                                    items.get(finalI).setPrice(jsonObject.getString("median_price"));
                                    itemAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                            }
                        });
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