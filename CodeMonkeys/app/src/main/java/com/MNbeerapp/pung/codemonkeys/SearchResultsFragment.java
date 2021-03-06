package com.MNbeerapp.pung.codemonkeys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchResultsFragment extends Fragment {
    ListView search;
    ListView beerDetailList;
    View view;
    SearchListAdapter adapter;
    SearchListAdapter adapterBeer;

    List<Search> mProductList;
    List<Search> deatilBeerList;
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
        deatilBeerList = new ArrayList<>();

        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        getBreweryResult();
        search = (ListView)view.findViewById(R.id.brewery_search_result_listView);
        adapter = new SearchListAdapter(getActivity().getApplicationContext(), mProductList,fragmentManager);
        search.setAdapter(adapter);


        return view;
    }


    public void getBreweryResult(){
        String breweryNameResult = null;
        String breweryAddress = null;
        String beerInfo = null;
        String beerNameResult = null;
        String phoneResult = null;
        String emailResult = null;
        String websiteResult = null;

        String find;
        int count=0;
        if(!breweryName.isEmpty()){
            count++;
        }
        if(!breweryZip.isEmpty()){
            count++;
        }
        if(!breweryCity.isEmpty()){
            count++;
        }
        if(!beerType.isEmpty()){
            count++;
        }
        if(!beerName.isEmpty()){
            count++;
        }

        if(count==0){
            find = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip, beer_type, beer_name, brewery_phone, brewery_email, brewery_website FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID";
        }else{
            find = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip, beer_type, beer_name, brewery_phone, brewery_email, brewery_website FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where ";
            if(!breweryName.isEmpty()){
                breweryName = breweryName.replace("'", "''");
                breweryName = breweryName.trim();
                find+="brewery_name LIKE '%"+breweryName+"%'";
                if(count>1){
                    find+=" and ";
                    count--;
                }
            }
            if(!breweryZip.isEmpty()){
                breweryZip = breweryZip.trim();
                find+="brewery_zip LIKE '"+breweryZip+"%'";
                if(count>1){
                    find+=" and ";
                    count--;
                }
            }
            if(!breweryCity.isEmpty()){
                breweryCity = breweryCity.trim();
                //if else city search created by Jeff. May need cleaning up but was the best way to get it to find saint paul in the database
                if(breweryCity.contentEquals("Saint") || breweryCity.contentEquals("saint")) {

                    find += "brewery_city LIKE '%St.%'";

                }else if(breweryCity.contains("Saint L")||breweryCity.contains("Saint l")||breweryCity.contains("saint L")||breweryCity.contains("saint l")) {

                    find += "brewery_city LIKE '%St. Louis Park%'";

                }else if(breweryCity.contains("Saint P")||breweryCity.contains("Saint p")||breweryCity.contains("saint P")||breweryCity.contains("saint p")){

                    find += "brewery_city LIKE '%St. Paul%'";

                }else if(breweryCity.contains("Saint J")||breweryCity.contains("Saint j")||breweryCity.contains("saint J")||breweryCity.contains("saint j")){

                    find += "brewery_city LIKE '%St. Joseph%'";

                }else{

                    find+="brewery_city LIKE '%"+breweryCity+"%'";
                }
                if(count>1){
                    find+=" and ";
                    count--;
                }
            }
            if(!beerType.isEmpty()){
                beerType = beerType.trim();
                find+="beer_type LIKE '%"+beerType+"%'";
                if(count>1){
                    find+=" and ";
                    count--;
                }
            }
            if(!beerName.isEmpty()){
                beerName = beerName.replace("'","''");
                beerName = beerName.trim();
                    find += "beer_name LIKE '%" + beerName + "%'";
                if(count>1){
                    find+=" and ";
                    count--;
                }
            }
        }

        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(find,null);
        if(cursor.moveToFirst()){

            breweryNameResult = cursor.getString(0);
            breweryAddress = cursor.getString(1);
            breweryAddress += " "+cursor.getString(2)+",";
            breweryAddress += " "+cursor.getString(3);
            breweryAddress += " "+cursor.getString(4);
            beerInfo = "Beer Type: "+cursor.getString(5);
            beerNameResult = cursor.getString(6);
            phoneResult = cursor.getString(7);
            emailResult = cursor.getString(8);
            websiteResult = cursor.getString(9);
            mProductList.add(new Search(1,breweryNameResult, breweryAddress, beerInfo, beerNameResult, phoneResult, emailResult, websiteResult));

            while(cursor.moveToNext()==true){
                breweryNameResult = cursor.getString(0);
                breweryAddress = cursor.getString(1);
                breweryAddress += " "+cursor.getString(2)+",";
                breweryAddress += " "+cursor.getString(3);
                breweryAddress += " "+cursor.getString(4);
                beerInfo = "Beer Type: "+cursor.getString(5);
                beerNameResult = cursor.getString(6);
                phoneResult = cursor.getString(7);
                emailResult = cursor.getString(8);
                websiteResult = cursor.getString(9);
                mProductList.add(new Search(1,breweryNameResult, breweryAddress, beerInfo, beerNameResult, phoneResult, emailResult, websiteResult));
            }

        }

        cursor.close();
    }





}
