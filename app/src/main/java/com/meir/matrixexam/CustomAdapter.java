package com.meir.matrixexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Country> countriesData;
    LayoutInflater layoutInflater;
    Country countriesModel;

    public CustomAdapter(Context context, ArrayList<Country> countriesData) {
        this.context = context;
        this.countriesData = countriesData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countriesData.size();
    }

    @Override
    public Object getItem(int i) {
        return countriesData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View rowView = view;
        if (rowView==null) {
            rowView = layoutInflater.inflate(R.layout.country_row, null, true);
        }
        //link views
        final ImageView countryFlagIv = rowView.findViewById(R.id.countryFlagIv);
        TextView countryNameTv = rowView.findViewById(R.id.countryNameTv);
        TextView nativeName = rowView.findViewById(R.id.nativeName);
        TextView populationTv = rowView.findViewById(R.id.populationTv);
        TextView areaTv = rowView.findViewById(R.id.areaTv);

        countriesModel = countriesData.get(position);

        countryNameTv.setText(countriesModel.country);
        nativeName.setText(countriesModel.nativeName);
        populationTv.setText("Population : " + countriesModel.population);
        areaTv.setText("Area : " + countriesModel.area);

        return rowView;
    }

}