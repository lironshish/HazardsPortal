package com.example.hazardsportal.DataManager;

import com.google.firebase.database.FirebaseDatabase;

public class MyDataManager {


    private final FirebaseDatabase realTimeDB;


    private static MyDataManager single_instance = null;

    private MyDataManager() {
        realTimeDB = FirebaseDatabase.getInstance();
    }

    public static MyDataManager initHelper() {
        if (single_instance == null) {
            single_instance = new MyDataManager();
        }
        return single_instance;
    }

    public static MyDataManager getInstance() {
        return single_instance;
    }

    public FirebaseDatabase getRealTimeDB() {
        return realTimeDB;
    }

}
