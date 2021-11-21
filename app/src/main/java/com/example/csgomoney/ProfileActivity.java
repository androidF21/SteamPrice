package com.example.csgomoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG="ProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://steamcommunity.com/inventory/76561198174398625/730/2", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("Success", TAG);
                JSONObject jsonObject=json.jsonObject;
                try {
                    JSONArray results=jsonObject.getJSONArray("descriptions");
                    Log.i(TAG, "Results: "+results.toString());
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