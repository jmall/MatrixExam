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
//        return countriesData.get(i).getId();
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
        TextView populationTv = rowView.findViewById(R.id.populationTv);
        TextView areaTv = rowView.findViewById(R.id.areaTv);

        countriesModel = countriesData.get(position);
//        String netName = countriesModel.flag.toLowerCase().replace(".svg","").replace("https://restcountries.eu/data/","");
//        int drawableResourceId = context.getResources().getIdentifier(netName, "drawable", context.getPackageName());
//
//        if (drawableResourceId !=0) {
//            Drawable drawable = ContextCompat.getDrawable(context, drawableResourceId);
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                drawable = (DrawableCompat.wrap(drawable)).mutate();
//            }
//            Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//
//
//            countryFlagIv.setImageBitmap(bmp);
//        }
        countryNameTv.setText(countriesModel.country);
        populationTv.setText("Population : " + countriesModel.population);
        areaTv.setText("Area : " + countriesModel.area);

        return rowView;
    }

}