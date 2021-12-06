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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.csgomoney.adapters.ItemAdapter;
import com.example.csgomoney.models.Item;
import com.example.csgomoney.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "ProfileActivity";
    List<Item> items;
    User loggedInUser;
    Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseUser user = ParseUser.getCurrentUser();
        String SteamID=(String)user.get("SteamID");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        myThread.interrupt();
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        myThread.interrupt();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
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
        loggedInUser = new User();

        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams jsonParams = new RequestParams();
//        jsonParams.put("token", "619b5b289ec480409601fc66");
//        client.post("https://backpack.tf/api/inventory/"+SteamID+"/refresh", jsonParams.toString(), new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "Refresh successful");
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                Log.d(TAG, "Failed to refresh: "+response);
//            }
//        });

        client.get("https://backpack.tf/api/users/info/v1?steamids="+SteamID+"&key=619b5b289ec480409601fc66", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("Success", TAG);
                JSONObject jsonObject = json.jsonObject;
                try {
                    loggedInUser.setName(jsonObject.getJSONObject("users").getJSONObject(SteamID).getString("name"));
                    loggedInUser.setAvatar(jsonObject.getJSONObject("users").getJSONObject(SteamID).getString("avatar"));
                    loggedInUser.setValue(jsonObject.getJSONObject("users").getJSONObject(SteamID).getJSONObject("inventory").getJSONObject("730").getString("value"));
                    items.add(new Item(loggedInUser));
                    itemAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    loggedInUser.setValue("0.00");
                    items.add(new Item(loggedInUser));
                    itemAdapter.notifyDataSetChanged();
                }

                client.get("https://steamcommunity.com/inventory/"+SteamID+"/730/2?l=english&count=5000", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d("Success", TAG);
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("descriptions");
                            for (int i = 0; i < results.length(); i++) {
                                String description="";
                                if (results.getJSONObject(i).getInt("marketable") == 1) {
                                    JSONArray temp=results.getJSONObject(i).getJSONArray("descriptions");
                                    for(int j=0;j<temp.length();j++){
                                        if(temp.getJSONObject(j).getString("value").isEmpty()==false) {
                                            description += Html.fromHtml(temp.getJSONObject(j).getString("value"));
                                            description += "\n";
                                        }
                                    }
                                    Log.v(TAG, description);
                                    items.add(new Item(results.getJSONObject(i).getString("name"),
                                            results.getJSONObject(i).getString("market_hash_name"),
                                            results.getJSONObject(i).getString("icon_url"),
                                            description
                                    ));
                                    itemAdapter.notifyDataSetChanged();
                                }
                            }

                            myThread =new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        for (int i = 0; i < items.size(); i++) {
                                            Log.v(TAG, "Attempting to get prices...");
                                            getPrices(i);
                                            Thread.sleep(3000);
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

                    public void getPrices(int index) {
                        client.get("https://steamcommunity.com/market/priceoverview/?appid=730&currency=1&market_hash_name=" + items.get(index).getMarket_hash_name(), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                JSONObject jsonObject = json.jsonObject;
                                try {
                                    items.get(index).setPrice(jsonObject.getString("lowest_price"));
                                    itemAdapter.notifyDataSetChanged();
                                    Log.v("TAG", "Prices retrieved");
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

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

}