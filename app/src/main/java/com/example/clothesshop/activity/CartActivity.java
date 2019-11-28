package com.example.clothesshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.CartAdapter;
import com.example.clothesshop.interfaces.RemoveFromCartListener;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements RemoveFromCartListener {

    Toolbar toolbar;
    RecyclerView cartRecycler;
    CartAdapter adapter;
    List<CartModel> cartlist=new ArrayList<>();
    SessionManager sessionManager;
    RelativeLayout cartView;
    LinearLayout emptycart;
    TextView btnBuy;
    Button shonow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        setupToolbar();
        setupRecycler();
    }

    private void setupRecycler() {
        cartlist=sessionManager.getCartList();
        if (cartlist==null || cartlist.size()==0){
            emptycart.setVisibility(View.VISIBLE);
            cartView.setVisibility(View.GONE);
        }
        else {
            adapter = new CartAdapter(cartlist, this);
            adapter.notifyDataSetChanged();
            cartRecycler.setAdapter(adapter);
            emptycart.setVisibility(View.GONE);
            cartView.setVisibility(View.VISIBLE);
        }

    }

    private void init() {
        toolbar=findViewById(R.id.toolbar);
        btnBuy=findViewById(R.id.btnBuyNow);

        cartRecycler=findViewById(R.id.cartRecycler);
        cartRecycler.setHasFixedSize(true);
        cartRecycler.setLayoutManager(new LinearLayoutManager(CartActivity.this,LinearLayoutManager.VERTICAL,false));
        cartRecycler.setItemAnimator(new DefaultItemAnimator());

        sessionManager=new SessionManager(this);
        cartView=findViewById(R.id.cartView);
        emptycart=findViewById(R.id.emptyCart);

        shonow=findViewById(R.id.btnshopnow);
        shonow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,ClothesActivity.class));
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
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
//        ArrayList<CartModel> cart=sessionManager.getCartList();
//
//        if (cart==null || cart.size()==0){
//            emptycart.setVisibility(View.VISIBLE);
//            cartView.setVisibility(View.GONE);
//        }
//        else {
//            emptycart.setVisibility(View.GONE);
//            cartView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
