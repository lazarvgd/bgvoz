package com.example.xor.bgvoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PanMost extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_most);

        Spinner stanica= (Spinner)findViewById(R.id.stanica);
        Spinner polazak= (Spinner)findViewById(R.id.polazak);

        ArrayAdapter<CharSequence> nizStanica= ArrayAdapter.createFromResource(this, R.array.panmost, android.R.layout.simple_spinner_item);

        nizStanica.setDropDownViewResource(android.R.layout.simple_spinner_item);

        stanica.setAdapter(nizStanica);

    }
}
