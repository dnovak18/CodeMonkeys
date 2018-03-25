package com.example.pung.codemonkeys;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeffrey on 3/24/2018.
 */

public class DetailsListAdapter extends BaseAdapter{
    private Context mContext;
    private List<Details> detailProductList;

    //Constructor

    public DetailsListAdapter(Context mContext, List<Details> mProductList) {
        this.mContext = mContext;
        this.detailProductList = detailProductList;
    }

    @Override
    public int getCount() {
        return detailProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.details_beer_layout, null);
        TextView beerName = (TextView)v.findViewById(R.id.beerName_textView);
        TextView type = (TextView)v.findViewById(R.id.beerType_textView);
        TextView abv = (TextView)v.findViewById(R.id.beerABV_textView);

        //Set text for TextView
        beerName.setText(detailProductList.get(position).getName());
        type.setText(String.valueOf(detailProductList.get(position).getAddress()));
        abv.setText(String.valueOf(detailProductList.get(position).getBeerType()));

        //Save product id to tag
        v.setTag(detailProductList.get(position));

        return v;
    }
}
