package com.meir.matrixexam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String countriesStr = "";
    static List<Country> countries;
    static Context context;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        new TaskGetData().execute("https://restcountries.eu/rest/v2/all");

        ImageButton imageButton = findViewById(R.id.ztoa);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customAdapter != null){

                    Collections.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country country1, Country country2) {
                            return country2.country.compareTo(country1.country);
                        }
                    });
                    customAdapter.notifyDataSetChanged();
                }
            }
        });
        ImageButton imageButton1 = findViewById(R.id.atoz);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customAdapter != null){

                    Collections.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country country1, Country country2) {
                            return country1.country.compareTo(country2.country);
                        }
                    });
                }
                customAdapter.notifyDataSetChanged();
            }
        });
        ImageButton area = findViewById(R.id.area);
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customAdapter != null){

                    Collections.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country country1, Country country2) {
                            if (country1.population < country2.population) return -1;
                            if (country1.population > country2.population) return 1;
                            return 0;
                        }
                    });
                }
                customAdapter.notifyDataSetChanged();
            }
        });
        ImageButton population = findViewById(R.id.population);
        population.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customAdapter != null){

                    Collections.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country country1, Country country2) {
                            if (country1.area < country2.area) return -1;
                            if (country1.area > country2.area) return 1;
                            return 0;
                        }
                    });
                }
                customAdapter.notifyDataSetChanged();
            }
        });
        ListView countryListView = findViewById(R.id.countryList);
        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(MainActivity.this, Neighbors.class);
                myIntent.putExtra("index", i); //Optional parameters
                startActivity(myIntent);
            }
        });
    }

    class TaskGetData extends AsyncTask<String , String , String> {


        @Override
        protected void onPreExecute() {
//        textView.append("Get data ...\n\n");
        }

        @Override
        protected String doInBackground(String... params) {
            return getDataHttpUriConnection(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            countries = new ArrayList<Country>();
            readData();
            ListView countryListView = findViewById(R.id.countryList);

            customAdapter = new CustomAdapter(context, (ArrayList<Country>) countries);
            countryListView.setAdapter(customAdapter);

        }
        public String getDataHttpUriConnection(String uri){
            try {
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String result = inputStreamToString(con.getInputStream());
                countriesStr = result;
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        public String inputStreamToString(InputStream stream)  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb = new StringBuilder();
            String line = "";
            try
            {
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    sb.append("\n");
                }
                return sb.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }


    public void readData(){
        try {
            JSONArray jsonArray = new JSONArray(countriesStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String country = jsonObject.getString("name");
                String flag = jsonObject.getString("flag");
                String area = jsonObject.getString("area");
                String subregion = jsonObject.getString("subregion");
                String population = jsonObject.getString("population");
                String nativeName = jsonObject.getString("nativeName");
                Country countryRecord = new Country();
                countryRecord.country = country;
                countryRecord.flag = flag;
                countryRecord.subregion = subregion;
                countryRecord.nativeName = nativeName;
                countryRecord.area = 0;
//                if (area != null && !area.isEmpty()) {
                if (area != null && area.compareTo("null") < 0) {
                    countryRecord.area = Double.valueOf(area);
                }
                countryRecord.population = Integer.parseInt(population);
                countries.add(countryRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

class Country{
    String country;
    String flag;
    double area;
    int population;
    String subregion;
    String nativeName;
}