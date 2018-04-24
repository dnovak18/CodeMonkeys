package com.MNbeerapp.pung.codemonkeys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by prath on 2/20/2018.
 */
public class SearchDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<Search> deatilBeerList;
    private FragmentManager mFragmentManager;

    //Constructor

    public SearchDetailAdapter(Context mContext, List<Search> deatilBeerList,FragmentManager mFragmentManager) {
        this.mContext = mContext;
        this.deatilBeerList = deatilBeerList;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public int getCount() {
        return deatilBeerList.size();
    }

    @Override
    public Object getItem(int position) {
        return deatilBeerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.details_beer_layout, null);
        TextView beerTypeDetail = (TextView)v.findViewById(R.id.beerType_textView);
        TextView beerNameDetail = (TextView)v.findViewById(R.id.beerName_textView);
        TextView beerAbvDetail = (TextView)v.findViewById(R.id.beerABV_textView);
    //Set text for TextView
        beerTypeDetail.setText(deatilBeerList.get(position).getBeerType());
        beerNameDetail.setText(deatilBeerList.get(position).getBeerName());
        beerAbvDetail.setText(deatilBeerList.get(position).getABV());




        //Save product id to tag
        v.setTag(deatilBeerList.get(position));

        return v;
    }
}
