package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Map;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    //Mircea is driving
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

}
