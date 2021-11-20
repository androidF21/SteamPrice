package com.example.csgomoney;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IDBe3Veffbnc7g7NNbT2851SbvQuvWPE2H7OFVIC")
                .clientKey("DmUN9YrxqAxX6oue6MhoBvm7ThZeNgO0CGLdhFtW")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
