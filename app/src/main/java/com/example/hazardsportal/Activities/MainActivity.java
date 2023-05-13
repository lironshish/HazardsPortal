package com.example.hazardsportal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hazardsportal.Fragments.MapsFragment;
import com.example.hazardsportal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    MapsFragment mapsFragment;
    private MaterialTextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMapsFragment();
    }


    private void initViews(){
        title = findViewById(R.id.title);
    }
    private void initMapsFragment(){
        mapsFragment = new MapsFragment();
        //Open Fragments
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, mapsFragment)
                .commit();
    }


}