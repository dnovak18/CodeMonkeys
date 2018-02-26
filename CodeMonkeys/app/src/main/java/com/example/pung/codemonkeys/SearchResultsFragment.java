package com.example.pung.codemonkeys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchResultsFragment extends Fragment {
    ListView search;
    View view;
    SearchListAdapter adapter;
    List<Search> mProductList;
    DatabaseHelper myDbHelper;
    String breweryName;
    String breweryZip;
    String breweryCity;
    String beerType;
    String beerName;

    public SearchResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        Bundle bundle = getArguments();

        breweryName = bundle.getString("breweryName");
        breweryZip = bundle.getString("breweryZip");
        breweryCity = bundle.getString("breweryCity");
        beerType = bundle.getString("beerType");
        beerName = bundle.getString("beerName");



        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();
        mProductList = new ArrayList<>();


        getBreweryResult();
        search = (ListView)view.findViewById(R.id.brewery_search_result_listView);
        adapter = new SearchListAdapter(getActivity().getApplicationContext(), mProductList);
        search.setAdapter(adapter);

        ListView clickTextView = (ListView) view.findViewById(R.id.brewery_search_result_listView);
        //Toast.makeText(getActivity().getApplicationContext(), "get string =" +clickTextView , Toast.LENGTH_SHORT).show();


        return view;
    }

    public void getBreweryResult(){
        String breweryNameResult = null;
        String breweryAddress = null;
        String find;

        if(breweryName.isEmpty()&&breweryZip.isEmpty()&&breweryCity.isEmpty()&&beerType.isEmpty()&&beerName.isEmpty()){
            find = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID";
        }else{
            find = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where ";
            if(!breweryName.isEmpty()){
                find+="brewery_name = '"+breweryName+"'";
            }
            if(!breweryZip.isEmpty()){
                find+="brewery_zip = '"+breweryZip+"'";
            }
            if(!breweryCity.isEmpty()){
                find+="brewery_city = '"+breweryCity+"'";
            }
            if(!beerType.isEmpty()){
                find+="beer_type = '"+beerType+"'";
            }
            if(!beerName.isEmpty()){
                find+="beer_name = '"+beerName+"'";
            }
        }

        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(find,null);
        if(cursor.moveToFirst()){

            breweryNameResult = cursor.getString(0);
            breweryAddress = cursor.getString(1);
            breweryAddress += " "+cursor.getString(2);
            breweryAddress += " "+cursor.getString(3);
            breweryAddress += " "+cursor.getString(4);
            mProductList.add(new Search(1,breweryNameResult, breweryAddress));

            while(cursor.moveToNext()==true){
                breweryNameResult = cursor.getString(0);
                breweryAddress = cursor.getString(1);
                breweryAddress += " "+cursor.getString(2);
                breweryAddress += " "+cursor.getString(3);
                breweryAddress += " "+cursor.getString(4);
                mProductList.add(new Search(1,breweryNameResult, breweryAddress));

            }

        }

        cursor.close();
    }



}
