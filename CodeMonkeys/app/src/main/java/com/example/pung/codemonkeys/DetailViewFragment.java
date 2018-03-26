package com.example.pung.codemonkeys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by prath on 3/24/2018.
 */

public class DetailViewFragment extends Fragment {
    String breweryName;
    String breweryAddress;
    String breweryPhone;
    String breweryEmail;
    String breweryWebsite;

    public DetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);

        Bundle bundle = getArguments();

        breweryName = bundle.getString("breweryName");
        breweryAddress = bundle.getString("breweryAddress");
        breweryPhone = bundle.getString("breweryPhone");
        breweryEmail = bundle.getString("breweryEmail");
        breweryWebsite = bundle.getString("breweryWebsite");

        TextView breweryNameChange = (TextView)view.findViewById(R.id.brewery_name_detail_textView);
        breweryNameChange.setText(breweryName);

        TextView breweryAddressChange = (TextView)view.findViewById(R.id.brewery_address_detail_textView);
        breweryAddressChange.setText(breweryAddress);

        TextView breweryPhoneChange = (TextView)view.findViewById(R.id.brewery_phone_detail_textView_change);
        breweryPhoneChange.setText(breweryPhone);

        TextView breweryEmailChange = (TextView)view.findViewById(R.id.brewery_email_detail_textView_change);
        breweryEmailChange.setText(breweryEmail);

        TextView breweryWebsiteChange = (TextView)view.findViewById(R.id.brewery_website_detail_textView_change);
        breweryWebsiteChange.setText(breweryWebsite);


        return view;

    }
}
