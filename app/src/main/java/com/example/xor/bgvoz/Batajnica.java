package com.example.xor.bgvoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class Batajnica extends AppCompatActivity {

    DatabaseHelper database;
    String [] stanice;

    Spinner polaznaStanica;
    Spinner vrijemePolaska;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batajnica);

        database = new DatabaseHelper(this);

        polaznaStanica = (Spinner)findViewById(R.id.stanica_spinner);
        vrijemePolaska = (Spinner)findViewById(R.id.polazak_spinner);

        ArrayAdapter<CharSequence> nizStanica= ArrayAdapter.createFromResource(this, R.array.batajnica, android.R.layout.simple_spinner_item);

        nizStanica.setDropDownViewResource(android.R.layout.simple_spinner_item);

        polaznaStanica.setAdapter(nizStanica);

        stanice = getResources().getStringArray(R.array.batajnica_tabele);

        polaznaStanica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Stanica", stanice[position]);

                List<String> vremena;
                vremena = database.getTimesBataja(stanice[position]);

                ArrayAdapter<String> nizPolazaka = new ArrayAdapter<String>(
                        Batajnica.this,
                        android.R.layout.simple_list_item_1,
                        vremena);

                nizPolazaka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                vrijemePolaska.setAdapter(nizPolazaka);
                database.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
