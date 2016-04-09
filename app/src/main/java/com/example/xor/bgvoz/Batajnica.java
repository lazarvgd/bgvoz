package com.example.xor.bgvoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class Batajnica extends AppCompatActivity {

    Spinner stanica= (Spinner)findViewById(R.id.stanica);
    Spinner polazak= (Spinner)findViewById(R.id.polazak);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batajnica);
    }

}
