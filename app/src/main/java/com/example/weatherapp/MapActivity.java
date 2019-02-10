package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    //Mircea is driving
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initMap();


        //
    }

    private LatLng sydney = new LatLng(-33.852, 151.211);
    private LatLng austin = new LatLng(30.2672, -97.7431);

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMinZoomPreference(10);
        gMap.addMarker(new MarkerOptions().position(austin)
                .title("Marker in Austin"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(austin));
    }

    // Jason is driving

    public void submitAddress(View view){
        //gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        TextView addressBar = (TextView) findViewById(R.id.addressText);
        String address = addressBar.getText().toString();

        String latString;
        String longiString;

        Geocoder geocoder = new Geocoder(this);
        gMap.clear();


        try {
            List<Address> location = geocoder.getFromLocationName(address, 1);

            for(int i=0; i< location.size(); i++){
            System.out.println(location.get(0));
            }

            LatLng coordinates = new LatLng(location.get(0).getLatitude(),location.get(0).getLongitude());
            latString = ""+coordinates.latitude;
            longiString = ""+coordinates.longitude;

            gMap.addMarker(new MarkerOptions().position(coordinates)
                  .title("You are here!"));

            gMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));


            // Jason driving again

            String base = "https://api.darksky.net/forecast/API_KEY/"; //37.8267,-122.4233
            String url = base + latString + "," + longiString;
            final String weatherInfo = "";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("Response: " + response.toString());
                            weatherInfo = response.toString();
                        }
                    }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            Volley.newRequestQueue(this).add(jsonObjectRequest);



        }


        //Mircea drive
        catch(IOException e){
            Toast toast = Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT);
            toast.show();
        }



    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

}
