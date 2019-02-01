package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private GoogleMap gMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initMap();
    }

    private LatLng sydney = new LatLng(-33.852, 151.211);
    private LatLng austin = new LatLng(30.2672, -97.7431);

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.addMarker(new MarkerOptions().position(austin)
                .title("Marker in Austin"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(austin));
    }

    public void submitAddress(){
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

}
