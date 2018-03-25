package com.example.pung.codemonkeys;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
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
public class SearchListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Search> mProductList;
    private FragmentManager mFragmentManager;

    //Constructor

    public SearchListAdapter(Context mContext, List<Search> mProductList,FragmentManager mFragmentManager) {
        this.mContext = mContext;
        this.mProductList = mProductList;
        this.mFragmentManager = mFragmentManager;
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

        breweryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    DetailViewFragment detailViewFragment = new DetailViewFragment();
                    fragmentTransaction.replace(R.id.activity_main, detailViewFragment);
                    fragmentTransaction.commit();

                } catch (ClassCastException e) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                }
                
            }
        });

        //Save product id to tag
        v.setTag(mProductList.get(position));

        return v;
    }
}
