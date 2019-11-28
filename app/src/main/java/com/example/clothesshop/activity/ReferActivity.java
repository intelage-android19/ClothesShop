package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.clothesshop.R;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;

public class ReferActivity extends AppCompatActivity {

    Toolbar toolbar;
    CheckConnectivity checkConnectivity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
checkConnectivity = new CheckConnectivity();
        setupToolbar();
    }
    private void setupToolbar() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Refer & Earn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backwhite);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void onResume() {
        super.onResume();
        this.invalidateOptionsMenu();
        Intent background = new Intent(getApplicationContext(), BlockStatusService.class);
        startService(background);
        //Check connectivity

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(checkConnectivity, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        Intent background = new Intent(getApplicationContext(), BlockStatusService.class);
        stopService(background);
        if (checkConnectivity != null) {
            this.unregisterReceiver(checkConnectivity);
        }
    }
}




