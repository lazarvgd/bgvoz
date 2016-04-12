package com.example.xor.bgvoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Batajnica extends AppCompatActivity {

    DatabaseHelper database;
    String [] stanice;

    Spinner polaznaStanica;
    Spinner vrijemePolaska;

    TextView batajaPolasci;

    List<String> vremena = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_most);

        database = new DatabaseHelper(this);

        // TODO : MENJANO
        try {
            database.createDataBase();
            database.openDataBase();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        batajaPolasci = (TextView) findViewById(R.id.bataja_polasci);

        polaznaStanica = (Spinner)findViewById(R.id.stanica_spinner);
        vrijemePolaska = (Spinner)findViewById(R.id.polazak_spinner);

        ArrayAdapter<CharSequence> nizStanica= ArrayAdapter.createFromResource(this, R.array.batajnica, android.R.layout.simple_spinner_item);

        nizStanica.setDropDownViewResource(android.R.layout.simple_spinner_item);

        polaznaStanica.setAdapter(nizStanica);

        stanice = getResources().getStringArray(R.array.batajnica_tabele);

        polaznaStanica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vremena = database.getTimesPanMost(stanice[position]);

                ArrayAdapter<String> nizPolazaka = new ArrayAdapter<String>(Batajnica.this, android.R.layout.simple_list_item_1, vremena);

                nizPolazaka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                vrijemePolaska.setAdapter(nizPolazaka);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vrijemePolaska.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Select 2" , "INSIDE");

                List<String> filtriraniPolasci = filterPolaska(vremena, vremena.get(position));

                Log.d("Select 2" , "SIZE " + filtriraniPolasci.size());

                ispisiPolaske(filtriraniPolasci);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ispisiPolaske(List<String> lista){

        String text = "";

        for(String polazak : lista)
            text+= polazak + "\n\n";

        batajaPolasci.setText(text);
    }

    private List<String> filterPolaska(List<String> lista, String polazak){
        List<String> polasci = new ArrayList<>();

        Log.d("Filter polazaka" , "INSIDE");

        for(String current : lista)
            if(polazakJeKasnije(polazak,current))
                polasci.add(current);

        return polasci;
    }

    private boolean polazakJeKasnije(String A, String B){

        Log.d("Polazak je kasnije", "INSIDE");

        int satA = Integer.parseInt(A.substring(0, A.indexOf(":")));
        int minA = Integer.parseInt(A.substring(A.indexOf(":") + 1));

        int satB = Integer.parseInt(B.substring(0, B.indexOf(":")));
        int minB = Integer.parseInt(B.substring(B.indexOf(":") + 1));


        Log.d("Polazak je kasnije", "A " + A + "  B " + B);
        Log.d("Polazak je kasnije" , satA + " " + minA + "\n" + satB + " " + minB);

        if(satA<satB)
            return true;
        else
        if(satA==satB && minA<minB)
            return true;

        return false;
    }
}
