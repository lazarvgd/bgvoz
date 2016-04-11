package com.example.xor.bgvoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PanMost extends AppCompatActivity {

    DatabaseHelper database;
    String [] stanice;

    Spinner stanica;
    Spinner polazak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_most);

        database = new DatabaseHelper(this);
        try {
            database.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } //pokusaj da se kopira postojeca baza umjesto nove

        stanica= (Spinner)findViewById(R.id.stanica);
        polazak= (Spinner)findViewById(R.id.polazak);

        ArrayAdapter<CharSequence> nizStanica= ArrayAdapter.createFromResource(this, R.array.panmost, android.R.layout.simple_spinner_item);

        nizStanica.setDropDownViewResource(android.R.layout.simple_spinner_item);

        stanica.setAdapter(nizStanica);

        //uzimam panmost_tabele i prosledjujem ga nizu stanica jer je zapisano u formatu u kome je i ime kolona u DB
        stanice = getResources().getStringArray(R.array.panmost_tabele);

        stanica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                List<String> vremena;

                vremena=database.getTimesPanMost(stanice[position]);

                ArrayAdapter<String> nizPolazaka=new ArrayAdapter<String>(PanMost.this,android.R.layout.simple_list_item_1,vremena);

                nizPolazaka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                polazak.setAdapter(nizPolazaka);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
