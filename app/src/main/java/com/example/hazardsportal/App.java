package com.example.hazardsportal;

import android.app.Application;

import com.example.hazardsportal.DataManager.MyDataManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Initiate FireBase Managers
        MyDataManager.initHelper();
    }
}
