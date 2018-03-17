package com.example.pung.codemonkeys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultScreenFragment extends Fragment {
    ListView search;
    View view;
    SearchListAdapter adapter;
    List<Search> mProductList;
    DatabaseHelper myDbHelper;

    public ResultScreenFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_screen, container, false);

        Bundle breweryInfoBundle = new Bundle();

        String detailBrewery = breweryInfoBundle.getString("brewery");
        String detailAddress = breweryInfoBundle.getString("address");
        String detailCity = breweryInfoBundle.getString("city");
        String detailState = breweryInfoBundle.getString("state");
        String detailZip = breweryInfoBundle.getString("zip");
        String detailBeerName = breweryInfoBundle.getString("beerName");
        String detailBeerType = breweryInfoBundle.getString("beerType");

        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();
        mProductList = new ArrayList<>();


        getBreweryResult();
        search = view.findViewById(R.id.brewery_result_infoListView);
        //details list adapter?
       // adapter = new InfoListAdapter(getActivity().getApplicationContext(), mProductList);
        search.setAdapter(adapter);

        return view;
    }

    public void getBreweryResult(){

    }

}
