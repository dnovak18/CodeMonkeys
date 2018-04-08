package com.example.pung.codemonkeys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    DatabaseHelper myDbHelper;
    String breweryNameResult;
   // String[] arrayBreweryName;
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
    //autocomplete method, takes colum name and table name, return string array
    public String[] getArrayBreweryName(String search, String table){

        String searchDatabase = "SELECT DISTINCT "+search+" FROM  "+table;
        myDbHelper = new DatabaseHelper(getContext());
        myDbHelper.openDataBase();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(searchDatabase,null);
        String[] databaseResult = new String[cursor.getCount()];
        if(cursor.moveToFirst()){
            databaseResult[0] = cursor.getString(0);
        }
        for(int i = 1; i<databaseResult.length;i++){
            if(cursor.moveToNext()==true){
                databaseResult[i] = cursor.getString(0);
            }
        }
        return databaseResult;
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

        //autocomplete
        AutoCompleteTextView autoCompleteTextViewBreweryName = (AutoCompleteTextView)view.findViewById(R.id.breweryNameEditText);
        ArrayAdapter<String> adapterBreweryName = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,getArrayBreweryName("brewery_name", "brewery_table"));
        autoCompleteTextViewBreweryName.setAdapter(adapterBreweryName);

        AutoCompleteTextView autoCompleteTextViewBreweryZip = (AutoCompleteTextView)view.findViewById(R.id.breweryZipEditText);
        ArrayAdapter<String> adapterBreweryZip = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,getArrayBreweryName("brewery_zip", "brewery_table"));
        autoCompleteTextViewBreweryZip.setAdapter(adapterBreweryZip);

        AutoCompleteTextView autoCompleteTextViewBreweryCity = (AutoCompleteTextView)view.findViewById(R.id.breweryCityEditText);
        ArrayAdapter<String> adapterBreweryCity = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,getArrayBreweryName("brewery_city", "brewery_table"));
        autoCompleteTextViewBreweryCity.setAdapter(adapterBreweryCity);

        AutoCompleteTextView autoCompleteTextViewBeerType = (AutoCompleteTextView)view.findViewById(R.id.beerTypeEditText);
        ArrayAdapter<String> adapterBeerType = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,getArrayBreweryName("beer_type", "beer_table"));
        autoCompleteTextViewBeerType.setAdapter(adapterBeerType);

        AutoCompleteTextView autoCompleteTextViewBeerName = (AutoCompleteTextView)view.findViewById(R.id.beerNameEditText);
        ArrayAdapter<String> adapterBeerName = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,getArrayBreweryName("beer_name", "beer_table"));
        autoCompleteTextViewBeerName.setAdapter(adapterBeerName);



        //search
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
