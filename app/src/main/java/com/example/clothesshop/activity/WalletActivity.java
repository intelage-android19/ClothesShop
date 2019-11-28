package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.adapter.WalletHistoryAdapter;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.WalletHistoryBaseModel;
import com.example.clothesshop.model.WalletHistorymodel;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.example.clothesshop.utility.ConstantVariables.WALLET;
import static com.example.clothesshop.utility.ConstantVariables.WALLET_HISTORY;
import static com.example.clothesshop.utility.ConstantVariables.WALLET_TRANSFER_AMOUNT;
import static com.example.clothesshop.utility.SessionManager.KEY_CONTACT;
import static com.example.clothesshop.utility.SessionManager.KEY_ID;
import static com.example.clothesshop.utility.SessionManager.KEY_NAME;


public class WalletActivity extends AppCompatActivity {

    Toolbar toolbar;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    //all urls
    private String walletUrl = "GetWalletAmount";
    private String walletHistoryUrl = "GetWalletHistory";
    private String walletTransferAmount = "WalletToWallet";

    WalletHistoryBaseModel transactionHistoryBaseModel;
    List<WalletHistorymodel> walletHistorymodelList = new ArrayList<>();
    WalletHistoryAdapter walletHistoryAdapter;
    private SessionManager sessionManager;
    private String  name, contact;
    private String wallet_amount,cid;
    TextView tvWalletBalance;
    RecyclerView rvTransaction;
    Button btnSend, btnTransfer;
    EditText edtTransMobile, edtTransAmount;
    AlertDialog alertDialog;
//    CountryCodePicker ccp;
    String countrycode = "";

    String user_data, logo;
    String[] playlists;

    CheckConnectivity checkConnectivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        sessionManager = new SessionManager(this);
        checkConnectivity = new CheckConnectivity();
        initData();
        setupToolbar();
        //getUserData();


        getWalletBalance();
        getWalleHistory();

    }

    private void initData() {
        rvTransaction = findViewById(R.id.rvTransaction);
        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTransferDialog();
            }
        });

    }

/*
    private void getUserData() {

      */
/*  user_data = sessionManager.getData();
        playlists = user_data.split(",");
*//*

        if (Arrays.asList(playlists).contains(getResources().getString(R.string.wallet)) &&
                Arrays.asList(playlists).contains(getResources().getString(R.string.wallet_to_wallet)) &&
                Arrays.asList(playlists).contains(getResources().getString(R.string.trasactionHistory))) {
            btnTransfer.setVisibility(View.VISIBLE);
            rvTransaction.setVisibility(View.VISIBLE);

        } else {
            btnTransfer.setVisibility(View.GONE);
            rvTransaction.setVisibility(View.GONE);
        }
        if (Arrays.asList(playlists).contains(getResources().getString(R.string.wallet)) &&
                Arrays.asList(playlists).contains(getResources().getString(R.string.wallet_to_wallet))) {
            btnTransfer.setVisibility(View.VISIBLE);
            rvTransaction.setVisibility(View.GONE);

        } else {
            btnTransfer.setVisibility(View.GONE);
            rvTransaction.setVisibility(View.GONE);
        }

        if (Arrays.asList(playlists).contains(getResources().getString(R.string.wallet)) &&
                Arrays.asList(playlists).contains(getResources().getString(R.string.trasactionHistory))) {
            btnTransfer.setVisibility(View.GONE);
            rvTransaction.setVisibility(View.VISIBLE);

        } else {
            btnTransfer.setVisibility(View.GONE);
            rvTransaction.setVisibility(View.GONE);
        }
    }
*/

    private void getWalletBalance() {

        initVolleyCallback();

        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        cid = userInfo.get(KEY_ID);


        HashMap<String, String> param = new HashMap<>();

        param.put("customer_auto_id", cid);//"5d2577d462c29"

        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyParameters(WALLET,
                this.getResources().getString(R.string.base_url) + walletUrl, param);

    }

    private void getWalleHistory() {

        initVolleyCallback();


        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        cid = userInfo.get(KEY_ID);


        HashMap<String, String> param = new HashMap<>();

        param.put("customer_auto_id", cid);//"5d2577d462c29"

        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyParameters(WALLET_HISTORY,
                this.getResources().getString(R.string.base_url) + walletHistoryUrl, param);

    }

    private void transferAmount() {

        initVolleyCallback();


        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        cid = userInfo.get(KEY_ID);
        name = userInfo.get(KEY_NAME);
        contact = userInfo.get(KEY_CONTACT);


        HashMap<String, String> param = new HashMap<>();

        param.put("login_customer_auto_id", cid);//"5d2577d462c29"
        param.put("transfer_amount", edtTransAmount.getText().toString());//"5d2577d462c29"
        param.put("receivers_contact", edtTransMobile.getText().toString());//"5d2577d462c29"
        param.put("name", name);//"5d2577d462c29"
        param.put("country_code", countrycode);//"5d2577d462c29"
        param.put("contact", contact);//"5d2577d462c29"


        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyParameters(WALLET_TRANSFER_AMOUNT,
                this.getResources().getString(R.string.base_url) + walletTransferAmount, param);

    }


    private void showTransferDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.transfer_dialog, null);
        dialogBuilder.setView(dialogView);

        edtTransMobile = dialogView.findViewById(R.id.edtTransMobile);
        edtTransAmount = dialogView.findViewById(R.id.edtTransAmount);
        btnSend = dialogView.findViewById(R.id.btnSend);
/*
        ccp = (CountryCodePicker) dialogView.findViewById(R.id.ccpTranssfer);

        countrycode = ccp.getSelectedCountryCode();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                countrycode = ccp.getSelectedCountryCode();
            }
        });
*/

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnSend.setVisibility(View.GONE);

                if (checkValidations()) {
                    transferAmount();

                }
                alertDialog.dismiss();

            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case WALLET:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 0) {
                                String msg = jsonObj.getString("msg");
                                Toast.makeText(WalletActivity.this, msg, Toast.LENGTH_SHORT).show();


                            } else if (status == 1) {


                                wallet_amount = jsonObj.getString("wallat_balance");

                                tvWalletBalance.setText("₹" + " " + wallet_amount);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case WALLET_HISTORY:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 0) {
                                //  toastClass.makeToast(getApplicationContext(), "Error");

                            } else if (status == 1) {

                                Gson gson = new Gson();
                                transactionHistoryBaseModel = gson.fromJson(
                                        response, WalletHistoryBaseModel.class);

                                walletHistorymodelList = transactionHistoryBaseModel.getWallatHistory();

                                walletHistoryAdapter = new WalletHistoryAdapter(WalletActivity.this, walletHistorymodelList);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WalletActivity.this, LinearLayoutManager.VERTICAL, false);
                                rvTransaction.setLayoutManager(linearLayoutManager);
                                rvTransaction.setAdapter(walletHistoryAdapter);


                            }

                            if (walletHistorymodelList.isEmpty()) {
                            }


                        } catch (Exception e) {

                            Log.v("Wallet Activity", e.toString());
                        }
                        break;


                    case WALLET_TRANSFER_AMOUNT:

                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            String msg = jsonObj.getString("msg");

                            if (status == 0) {
                                Toast.makeText(WalletActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                Toast.makeText(WalletActivity.this, msg, Toast.LENGTH_SHORT).show();
                                getWalletBalance();
                                getWalleHistory();
                            }

                        } catch (Exception e) {
                            Log.v("Wallet Activity", e.toString());
                        }

                        //  mBinder.btnLogin.setVisibility(View.VISIBLE);
                        //   mBinder.cvPgBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }


    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wallet");
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

    private boolean checkValidations() {


        if (TextUtils.isEmpty(edtTransMobile.getText().toString())) {

            Toast.makeText(WalletActivity.this, "Please enter mobile", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtTransAmount.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter amount ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
