package com.example.pung.codemonkeys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SearchFragment extends Fragment {
    EditText breweryName;
    EditText breweryZip;
    EditText breweryCity;
    EditText beerType;
    EditText beerName;
    String breweryNameText;
    String breweryZipText;
    String breweryCityText;
    String beerTypeText;
    String beerNameText;
    public interface ChangeFragment{
        public void setFrag();
    }

    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //final SearchResultsFragment fragment = new SearchResultsFragment();
        breweryName = (EditText) view.findViewById(R.id.breweryNameEditText);
        breweryZip = (EditText) view.findViewById(R.id.breweryZipEditText);
        breweryCity = (EditText) view.findViewById(R.id.breweryCityEditText);
        beerType = (EditText) view.findViewById(R.id.beerTypeEditText);
        beerName = (EditText) view.findViewById(R.id.beerNameEditText);

          Button searchButton = (Button) view.findViewById(R.id.searchButton);
         searchButton.setOnClickListener(new View.OnClickListener() {
          @Override
    public void onClick(View view) {


              breweryNameText = breweryName.getText().toString();
              breweryZipText = breweryZip.getText().toString();
              breweryCityText = breweryCity.getText().toString();
              beerTypeText = beerType.getText().toString();
              beerNameText = beerName.getText().toString();
              //SearchResultsFragment fragment = new SearchResultsFragment();
              Bundle bundle = new Bundle();

              bundle.putString("breweryName", breweryNameText);
              bundle.putString("breweryZip", breweryZipText);
              bundle.putString("breweryCity", breweryCityText);
              bundle.putString("beerType", beerTypeText);
              bundle.putString("beerName", beerNameText);

             // fragment.setArguments(bundle);
              FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

              SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
              searchResultsFragment.setArguments(bundle);

              fragmentTransaction.replace(R.id.activity_main, searchResultsFragment);
              fragmentTransaction.commit();


          }
         });


        return view;
    }


}
