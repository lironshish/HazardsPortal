package com.example.hazardsportal.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hazardsportal.DataManager.MyDataManager;
import com.example.hazardsportal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private TextView locationDetails;
    private OnMapReadyCallback callback = googleMap -> {
        mMap = googleMap;
    };

    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        findViews(view);
        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        // Async
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                DatabaseReference reference = MyDataManager.getInstance().getRealTimeDB().getReference("hazards");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            double latitude = snapshot.child("lat").getValue(Double.class);
                            double longitude = snapshot.child("lon").getValue(Double.class);
                            LatLng location = new LatLng(latitude, longitude);
                            // Add the location to the map or do other processing
                            if(snapshot.child("level").getValue(String.class).equals("HIGH")){
                                mMap.addMarker(new MarkerOptions().position(location).title("High Hazard").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                            } else if(snapshot.child("level").getValue(String.class).equals("MEDIUM")){
                                mMap.addMarker(new MarkerOptions().position(location).title("Medium Hazard").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                            } else if(snapshot.child("level").getValue(String.class).equals("LOW")){
                                mMap.addMarker(new MarkerOptions().position(location).title("Low Hazard").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                            }

                            //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))); // Set marker color

                            moveToCurrentLocation(location);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }

    public void locateOnMap(double x, double y) {
        if (x == 0.0 && y == 0.0) {
            locationDetails.setVisibility(View.VISIBLE);
            locationDetails.setText("No information about location");
        } else {
            locationDetails.setVisibility(View.INVISIBLE);
            LatLng point = new LatLng(x, y);
            mMap.addMarker(new MarkerOptions().position(point).title(""));
            moveToCurrentLocation(point);
        }
    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    private void findViews(View view) {
        locationDetails = view.findViewById(R.id.data);
    }
}