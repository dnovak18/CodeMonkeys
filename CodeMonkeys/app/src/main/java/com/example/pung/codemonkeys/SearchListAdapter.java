package com.example.pung.codemonkeys;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prath on 2/20/2018.
 */
public class SearchListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Search> mProductList;

    //Constructor

    public SearchListAdapter(Context mContext, List<Search> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.search_result_layout, null);
        TextView breweryName = (TextView)v.findViewById(R.id.brewery_name_result_textView);
        TextView breweryAddress = (TextView)v.findViewById(R.id.brewery_address_result_textView);
        TextView beerType = (TextView)v.findViewById(R.id.beer_type);
        TextView beerName = (TextView)v.findViewById(R.id.beer_name);
        //Set text for TextView
        breweryName.setText(mProductList.get(position).getName());
        breweryAddress.setText(String.valueOf(mProductList.get(position).getAddress()));
        beerType.setText(String.valueOf(mProductList.get(position).getBeerType()));
        beerName.setText(String.valueOf(mProductList.get(position).getBeerName()));

        //Save product id to tag
        v.setTag(mProductList.get(position));

        return v;
    }
}
