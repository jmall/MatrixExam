package com.meir.matrixexam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Neighbors extends AppCompatActivity {
    String[] coutryArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbors);
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);
        TextView title = findViewById(R.id.title);
        TextView region = findViewById(R.id.region);
        String sTitle = MainActivity.countries.get(index).country;
        title.setText(sTitle);
        String subregion = MainActivity.countries.get(index).subregion;
        region.setText(subregion);
        int count = getRegionCountries(subregion);
        sTitle = MainActivity.countries.get(index).country +" ("+ count + ')';
        title.setText(sTitle);
    }

    public int getRegionCountries(String subregion){
        if (subregion.isEmpty()){
            return 0;
        }
        int count = 0;
        for (int i=0;i<MainActivity.countries.size();i++){
            if (MainActivity.countries.get(i).subregion.contains(subregion)){
                count ++;
            }
        }

        coutryArray = new String[count];
        int cc = 0;
        for (int i=0;i<MainActivity.countries.size();i++){
            if (MainActivity.countries.get(i).subregion.contains(subregion)){
                coutryArray[cc] = new String();
                coutryArray[cc] = MainActivity.countries.get(i).country + " ("+MainActivity.countries.get(i).nativeName+")";
                cc ++;
            }
        }
        ListView countryList = findViewById(R.id.countryList);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, R.id.label, coutryArray);
        countryList.setAdapter(adapter);

        return count;
    }
}
