package com.example.weatherapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private JSONObject weatherInfo;

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

        //gMap.setMinZoomPreference(10);
        gMap.addMarker(new MarkerOptions().position(austin)
                .title("Marker in Austin"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(austin));
    }

    //Jason is driving

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

            String DKKey = "35c9c0ca52bba848c40d88442d0a86f6";
            latString = "" + coordinates.latitude;
            longiString = "" + coordinates.longitude;

            gMap.addMarker(new MarkerOptions().position(coordinates)
                  .title("You are here!"));

            gMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

            // Jason driving again

            String base = "https://api.darksky.net/forecast/"; //37.8267,-122.4233
            String url = base + DKKey + "/" + latString + "," + longiString;


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("Response: " + response.toString());
                            weatherInfo = response;

                            //Mircea is driving
                            try {
                                double temperature = weatherInfo.getJSONObject("currently").getDouble("temperature");
                                double humidity = weatherInfo.getJSONObject("currently").getDouble("humidity");
                                double windSpeed = weatherInfo.getJSONObject("currently").getDouble("windSpeed");
                                double precipitation = weatherInfo.getJSONObject("currently").getDouble("precipIntensity");

                                String finalWeather =
                                        "Temperature: " + temperature + "\n" +
                                                "Humidity: " + humidity + "\n" +
                                                "Wind Speed: " + windSpeed + "\n" +
                                                "Precipitation Intensity: " + precipitation;

                                TextView weatherT = (TextView) findViewById(R.id.weatherText);
                                weatherT.setText(finalWeather);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

            Volley.newRequestQueue(this).add(jsonObjectRequest);

        }catch(IOException e){
            Toast toast = Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

}
