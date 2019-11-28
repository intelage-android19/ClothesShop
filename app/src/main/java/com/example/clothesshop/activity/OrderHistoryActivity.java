package com.example.clothesshop.activity;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.activity.MainActivity;
import com.example.clothesshop.adapter.BookHistoryAdapter;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.BookingHistoryBaseClass;
import com.example.clothesshop.model.BookingHistoryModel;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.ORDERHISTORY;
import static com.example.clothesshop.utility.SessionManager.KEY_ID;

public class OrderHistoryActivity extends AppCompatActivity {

    String id;

    RecyclerView rvOrderHistory;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    SessionManager sessionManager;

    String getBookHistory = "OrderHistory";

    CheckConnectivity checkConnectivity;

    BookingHistoryBaseClass bookingHistoryBaseClass;
    List<BookingHistoryModel> bookingHistoryModelList = new ArrayList<>();
    BookHistoryAdapter bookeHistoryAdapter;

    Toolbar toolbar;
    ImageView imgNoOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_history);
        rvOrderHistory = findViewById(R.id.rvOrderHistory);
        imgNoOrder = findViewById(R.id.imgNoOrder);
        toolbar = findViewById(R.id.toolbar);

        setupToolbar();

        getHistory();

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order History");
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


    private void getHistory() {

        sessionManager = new SessionManager(getApplicationContext());


        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        id = userInfo.get(KEY_ID);

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        Map<String, String> params = new HashMap<>();
        params.put("customer_auto_id", id);


        mVolleyService.postDataVolleyParameters(ORDERHISTORY,
                getResources().getString(R.string.base_url) + getBookHistory,
                params);


    }

    private void initVolleyCallback() {

        // cvPgBar.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case ORDERHISTORY:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 1) {

                                Gson gson = new Gson();

                                bookingHistoryBaseClass = gson.fromJson(
                                        response, BookingHistoryBaseClass.class);


                                List<BookingHistoryModel> bookingHistoryModels = bookingHistoryBaseClass.getBookingHistory();

                                for (BookingHistoryModel bookingHistoryModel : bookingHistoryModels) {
                                    bookingHistoryModelList.add(bookingHistoryModel);

                                }

                                bookeHistoryAdapter = new BookHistoryAdapter(getApplicationContext(), bookingHistoryModelList);
                                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                rvOrderHistory.setLayoutManager(horizontalLayoutManager);
                                rvOrderHistory.setAdapter(bookeHistoryAdapter);

                            }
                            if (status == 1) {
                                imgNoOrder.setVisibility(View.VISIBLE);

                            }
                            if (bookingHistoryModelList.isEmpty()) {
                                imgNoOrder.setVisibility(View.VISIBLE);
                            }
                            // Toast.makeText(getContext(), "sdfdff", Toast.LENGTH_SHORT).show();                            }
                        } catch (Exception e) {

                            Log.v("Customer Activity", e.toString());
                        }

                        break;

                }
                // cvPgBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                //   imgNoDataFound.setVisibility(View.VISIBLE);
                //Toast.makeText(CustomerListAtivity.this, "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                //       linearProgressBar.setVisibility(View.GONE);

            }
        };
    }

/*
    private void setupToolbar() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Clothes");
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
*/


}
