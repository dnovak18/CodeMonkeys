package com.example.pung.codemonkeys;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private TextView Name, Email;
    private ImageView profilePic;
    private ConstraintLayout profile_Section;
    private Button signOutButton;
    private Group profileGroup;
    private BottomNavigationView menuView;

    // TODO: Rename and change types of parameters
    private static int RC_SIGN_IN = 9001;

    public LoginActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        profile_Section = (ConstraintLayout)findViewById(R.id.profileSection);
        profileGroup = (Group)findViewById(R.id.profileGroup);
        Name = (TextView)findViewById(R.id.profileName);
        profilePic = (ImageView)findViewById(R.id.imageView3);
        Email = (TextView)findViewById(R.id.userEmail);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        signOutButton = (Button)findViewById(R.id.logoutButton);
        signOutButton.setOnClickListener(this);
        profileGroup.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        menuView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        // uncomment we don't want animation menu shifting
        //BottomNavigationViewHelper.removeShiftMode(menuView);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.scan:

                        //Intent scanClick = new Intent(LoginActivity.this, MainActivity.class);
                        //startActivity(scanClick);
                       // return true;
                       // getSupportFragmentManager().beginTransaction().replace(R.id.profileSection, new ScannerFragment()).addToBackStack(null).commit();
                        return true;

                    case R.id.search:
                      //  getSupportFragmentManager().beginTransaction().replace(R.id.profileSection, new SearchFragment()).addToBackStack(null).commit();
                        return true;

                    case R.id.map:
                      //  Intent mapClick = new Intent(LoginActivity.this, MapsActivity.class);
                       // startActivity(mapClick);
                        return true;

                    case R.id.profile:
                       // Intent profileClick = new Intent(LoginActivity.this, LoginActivity.class);
                      //  startActivity(profileClick);
                        return true;
                }
                return false;

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            updateUI(true);
            String name = account.getDisplayName();
            String email = account.getEmail();
            Uri imgURI = account.getPhotoUrl();
            if(imgURI != null){
                String imgURL = account.getPhotoUrl().toString();
                Glide.with(this).load(imgURL).into(profilePic);
            }
            Name.setText(name);
            Email.setText(email);

        } else updateUI(false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.logoutButton:
                signOut();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            Uri imgURI = account.getPhotoUrl();
            if(imgURI != null){
                String imgURL = account.getPhotoUrl().toString();
                Glide.with(this).load(imgURL).into(profilePic);
            }
            Name.setText(name);
            Email.setText(email);
            updateUI(true);


        }else {
            //Coded to determine status code errors on connection
//            int statusCode = result.getStatus().getStatusCode();
//            Toast.makeText(this, "Generic ERROR CODE" + statusCode, Toast.LENGTH_LONG).show();
            updateUI(false);
        }

    }
    private void updateUI(boolean isLogin){
        if(isLogin){
            signInButton.setVisibility(View.GONE);
            profileGroup.setVisibility(View.VISIBLE);
        }else{
            signInButton.setVisibility(View.VISIBLE);
            profileGroup.setVisibility(View.GONE);
            Glide.with(this).clear(profilePic);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void infoClick(View view) {

        Toast toast = Toast.makeText(LoginActivity.this, "Cheeeeers!", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        v.setTextSize(40);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        //Toast.makeText(MainActivity.this, "Cheeeeers!", Toast.LENGTH_LONG).show();


    }
}