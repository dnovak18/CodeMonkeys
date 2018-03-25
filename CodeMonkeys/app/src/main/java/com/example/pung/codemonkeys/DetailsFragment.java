package com.example.pung.codemonkeys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {
    ListView search;
    View view;
    DetailsListAdapter adapter;
    List<Details> detailProductList;
    DatabaseHelper myDbHelper;
    String brewery;
    String address;
    String zip;
    String city;
    String state;
    String beerType;
    String beerName;
    String breweryNameResult;
    String breweryAddress;
    String breweryCity;
    String breweryState;
    String breweryZip;
    String type;


    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);
        Bundle detailsBundle = getArguments();

      breweryNameResult =  detailsBundle.getString("brewery");
      breweryAddress = detailsBundle.getString("address");
      breweryCity = detailsBundle.getString("city");
      breweryState = detailsBundle.getString("state");
      breweryZip = detailsBundle.getString("zip");
      beerName = detailsBundle.getString("beerName");
      type = detailsBundle.getString("beerType");



        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();
        detailProductList = new ArrayList<Details>();


        getBreweryResult();
        search = (ListView)view.findViewById(R.id.brewery_search_result_listView);
        adapter = new DetailsListAdapter(getActivity().getApplicationContext(), detailProductList);
        search.setAdapter(adapter);


        return view;
    }

    public void getBreweryResult(){
       String breweryNameResult = null;
       String breweryAddress = null;
       String breweryCity = null;
       String breweryState = null;
       String breweryZip = null;
       String type = null;
       String beerNameResult = null;
       String beerABVResult = null;
       String breweryPhone = null;
       String breweryEmail = null;
       String breweryWebsite = null;
       String findDetails;

        int count=0;
        if(!breweryNameResult.isEmpty()){
            count++;
        }
        if(!breweryAddress.isEmpty()){
            count++;
        }
        if(!breweryZip.isEmpty()){
            count++;
        }
        if(!breweryCity.isEmpty()){
            count++;
        }
        if(!type.isEmpty()){
            count++;
        }
        if(!beerNameResult.isEmpty()){
            count++;
        }
        if(!breweryState.isEmpty()){
            count++;
        }
        if(!beerABVResult.isEmpty()){
            count++;
        }
        if(!breweryPhone.isEmpty()){
            count++;
        }
        if(!breweryWebsite.isEmpty()){
            count++;
        }
        if(!breweryEmail.isEmpty()){
            count++;
        }

        if(count==0){
            findDetails = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip, beer_type, beer_name FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID";
        }else{
            findDetails = "SELECT brewery_name, brewery_address, brewery_city,brewery_state,brewery_zip, beer_type, beer_name FROM brewery_table inner join beer_table ON brewery_table.brewery_ID = beer_table.brewery_ID where ";
            if(!breweryNameResult.isEmpty()){
                findDetails+="brewery_name LIKE '%"+breweryNameResult+"%'";
                if(count>1){
                    findDetails+=" and ";
                    count--;
                }
            }
            if(!breweryZip.isEmpty()){
                findDetails+="brewery_zip LIKE '"+breweryZip+"%'";
                if(count>1){
                    findDetails+=" and ";
                    count--;
                }
            }
            if(!breweryCity.isEmpty()){
                breweryCity = breweryCity.trim();
                //if else city search created by Jeff. May need cleaning up but was the best way to get it to find saint paul in the database
                if(breweryCity.contentEquals("Saint") || breweryCity.contentEquals("saint")) {

                    findDetails += "brewery_city LIKE '%St.%'";

                }else if(breweryCity.contains("Saint L")||breweryCity.contains("Saint l")||breweryCity.contains("saint L")||breweryCity.contains("saint l")) {

                    findDetails += "brewery_city LIKE '%St. Louis Park%'";

                }else if(breweryCity.contains("Saint P")||breweryCity.contains("Saint p")||breweryCity.contains("Saint P")||breweryCity.contains("Saint p")){

                    findDetails += "brewery_city LIKE '%St. Paul%'";

                }else{

                    findDetails+="brewery_city LIKE '%"+breweryCity+"%'";
                }
                if(count>1){
                    findDetails+=" and ";
                    count--;
                }
            }
            if(!type.isEmpty()){
                findDetails+="beer_type LIKE '%"+type+"%'";
                if(count>1){
                    findDetails+=" and ";
                    count--;
                }
            }
            if(!beerName.isEmpty()){
                findDetails+="beer_name LIKE '%"+beerName+"%'";
                if(count>1){
                    findDetails+=" and ";
                    count--;
                }
            }

        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(findDetails,null);
        if(cursor.moveToFirst()){

            breweryNameResult = cursor.getString(0);
            breweryAddress = cursor.getString(1);
            breweryCity += " "+cursor.getString(2)+",";
            breweryState += " "+cursor.getString(3);
            breweryZip += " "+cursor.getString(4);
            type = "Type: "+cursor.getString(5);
            beerNameResult = "Beer Name: "+cursor.getString(6);
            beerABVResult = "ABV: "+cursor.getString(7);
            breweryPhone = "Phone: "+cursor.getString(0);
            breweryEmail = "Email: "+cursor.getString(0);
            breweryWebsite = "Website: "+cursor.getString(0);
            detailProductList.add(new Details(1,breweryNameResult, breweryAddress,type,beerNameResult, beerABVResult, breweryPhone, breweryEmail,breweryWebsite));

            while(cursor.moveToNext()==true){
                breweryNameResult = cursor.getString(0);
                breweryAddress = cursor.getString(1);
                breweryCity += " "+cursor.getString(2)+",";
                breweryState += " "+cursor.getString(3);
                breweryZip += " "+cursor.getString(4);
                type = "Type: "+cursor.getString(5);
                beerNameResult = "Beer Name: "+cursor.getString(6);
                beerABVResult = "ABV: "+cursor.getString(7);
                breweryPhone = "Phone: "+cursor.getString(0);
                breweryEmail = "Email: "+cursor.getString(0);
                breweryWebsite = "Website: "+cursor.getString(0);
                detailProductList.add(new Details(1,breweryNameResult, breweryAddress,type,beerNameResult, beerABVResult, breweryPhone, breweryEmail,breweryWebsite));

            }

        }

        cursor.close();
    }
}}
