package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.WishlistAdapter;
import com.example.clothesshop.interfaces.RemoveWishlist;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity implements RemoveWishlist {

    Toolbar toolbar;
    RecyclerView recyclerView;
    WishlistAdapter adapter;
    List<CartModel> wishList = new ArrayList<>();
    SessionManager sessionManager;
    LinearLayout emptyWishlist;
    ScrollView wishView;
    Button shonow;
    CheckConnectivity checkConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        checkConnectivity = new CheckConnectivity();
        init();
        setupToolbar();
        setupRecycler();
    }

    private void setupRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        wishList = sessionManager.getWishlist();

        if (wishList == null || wishList.size() == 0) {
            wishView.setVisibility(View.GONE);
            emptyWishlist.setVisibility(View.VISIBLE);
        } else {
            adapter = new WishlistAdapter(wishList, this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            wishView.setVisibility(View.VISIBLE);
            emptyWishlist.setVisibility(View.GONE);
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        sessionManager = new SessionManager(this);
        recyclerView = findViewById(R.id.wishlistRecycler);
        wishView = findViewById(R.id.wishView);
        emptyWishlist = findViewById(R.id.emptyWishlist);
        shonow = findViewById(R.id.btnshopnow);
        shonow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WishlistActivity.this, ClothesActivity.class));
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wishlist");
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

    @Override
    public void removeItem() {
        setupRecycler();
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