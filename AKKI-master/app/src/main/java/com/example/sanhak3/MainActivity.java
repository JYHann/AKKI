package com.example.sanhak3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String mUsername;
    private String mPhotoUrl;

    private GoogleApiClient mGoogleApiClient;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentBalance fragmentBalance = new FragmentBalance();
    private FragmentCard fragmentCard = new FragmentCard();
    private FragmentCommunity fragmentCommunity = new FragmentCommunity();
    private FragmentInout fragmentInout = new FragmentInout();

    private FragmentPattern fragmentPattern = new FragmentPattern();
    OnBackPressedListener listener;

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser == null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if(mFirebaseUser.getPhotoUrl() != null){
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentInout).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        username = findViewById(R.id.username);
        username.setText( "< " + mUsername + " > 님으로 로그인");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.inout:
                    transaction.replace(R.id.frameLayout, fragmentInout).commitAllowingStateLoss();
                    break;
                case R.id.balance:
                    transaction.replace(R.id.frameLayout, fragmentBalance).commitAllowingStateLoss();
                    break;
                case R.id.pattern:
                    transaction.replace(R.id.frameLayout, fragmentPattern).commitAllowingStateLoss();
                    break;
                case R.id.card:
                    transaction.replace(R.id.frameLayout, fragmentCard).commitAllowingStateLoss();
                    break;
                case R.id.community:
                    transaction.replace(R.id.frameLayout, fragmentCommunity).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if(listener != null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = "";
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
