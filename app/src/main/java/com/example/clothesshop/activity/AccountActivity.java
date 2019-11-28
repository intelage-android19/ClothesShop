package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.adapter.WishlistAdapter;
import com.example.clothesshop.fragments.ChangePassFragment;
import com.example.clothesshop.fragments.Profile;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.CHANGE_PASSWORD;
import static com.example.clothesshop.utility.ConstantVariables.PROFILE_DATA;
import static com.example.clothesshop.utility.ConstantVariables.UPDATE_PROFILE;
import static com.example.clothesshop.utility.SessionManager.KEY_ID;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Menu mMenu;
    MenuItem cartItem;
    TextView textCartItemCount;
    SessionManager sessionManager;
    LinearLayout orders, wishlist, refer, changepass;
    ImageView btnEdit;
    Fragment fragment;
    FrameLayout frame;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String Profile_Data = "Customer-Profile";

    String id, name, contact, email, address, country_code, dialogName, dialogContact, dialogEmail, oldP, newP;
    TextView txtPName, txtPPhone, txtEmail;

    CheckConnectivity checkConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sessionManager = new SessionManager(this);
        checkConnectivity = new CheckConnectivity();
        init();
        frame.setVisibility(View.GONE);
        setupToolbar();

        getProfileData();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        orders = findViewById(R.id.orders);
        wishlist = findViewById(R.id.wishlist);
        refer = findViewById(R.id.referandearn);
        btnEdit = findViewById(R.id.btnEdit);
        frame = findViewById(R.id.fragmentFrame);
        changepass = findViewById(R.id.changepass);
        sessionManager = new SessionManager(this);

        txtPName = findViewById(R.id.txtUserName);
        txtPPhone = findViewById(R.id.txtUserContact);
        txtEmail = findViewById(R.id.txtUserEmail);

        orders.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        refer.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        changepass.setOnClickListener(this);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        this.mMenu = menu;
        this.cartItem = mMenu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        ArrayList<CartModel> cartModels = sessionManager.getCartList();

        if (cartModels != null) {
            //badgeCount.setBadgeCount(this,iconLayer,String.valueOf(cartModels));
            setupBadge();
        }

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });
        //  return super.onCreateOptionsMenu(menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        return true;
    }

    private void getProfileData() {

        sessionManager = new SessionManager(this);

        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        id = userInfo.get(KEY_ID);

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("customer_auto_id", id);

        mVolleyService.postDataVolleyParameters(PROFILE_DATA, this.getResources().getString(R.string.base_url) + Profile_Data, params);

    }

    private void initVolleyCallback() {
        // linearProgressBar.setVisibility(View.VISIBLE);
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {


                    case PROFILE_DATA:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 1) {
                                //   Toast.makeText(ProfileActivtiy.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                //    alertDialogUpdateProfile.dismiss();

                                JSONObject jsonObject1 = jsonObj.getJSONObject("profile");


                                name = jsonObject1.getString("name");
                                contact = jsonObject1.getString("contact");
                                email = jsonObject1.getString("email");

                                country_code = jsonObject1.getString("country_code");

                                txtPName.setText(name);
                                txtPPhone.setText(contact);
                                txtEmail.setText(email);
                            }
                        } catch (Exception e) {

                            Log.v("Update_profile_dialog", e.toString());
                        }

                        break;
                    /*case UPDATE_PROFILE:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            int message = jsonObj.getInt("msg");

                            if (status == 1) {
                                Toast.makeText(getContext(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                //updateProfileDialog.dismiss();
                            } else if (status == 0) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                //    alertDialogUpdateProfile.dismiss()
                            }
                        } catch (Exception e) {

                            Log.v("Update_profile_dialog", e.toString());
                        }

                        break;

                    case CHANGE_PASSWORD:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            String msg = jsonObj.getString("msg");

                            if (status == 0) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                Toast.makeText(getContext(), "Password has been changed successfully.", Toast.LENGTH_SHORT).show();
                                //  updatePasswordDialog.dismiss();

                            }

                        } catch (Exception e) {

                            Log.v("Update_profile_dialog", e.toString());
                        }
                        break;*/


                }
                // linearProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                //  imgNoDataFound.setVisibility(View.VISIBLE);
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                // linearProgressBar.setVisibility(View.GONE);

            }
        };
    }


    private void setupBadge() {

        ArrayList<CartModel> cartModels = sessionManager.getCartList();

        if (cartModels != null) {
            //badgeCount.setBadgeCount(this, iconlayer, String.valueOf(cartModels));

            if (textCartItemCount != null) {
                if (cartModels.size() == 0) {
                    if (textCartItemCount.getVisibility() != View.GONE) {
                        textCartItemCount.setVisibility(View.GONE);
                    }
                } else {

                    textCartItemCount.setText(String.valueOf(Math.min(cartModels.size(), 99)));

                    if (textCartItemCount.getVisibility() != View.VISIBLE) {
                        textCartItemCount.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
        }

        if (id == R.id.action_wishlist) {
            startActivity(new Intent(this, WishlistActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wishlist:
                startActivity(new Intent(this, WishlistActivity.class));
                break;

            case R.id.orders:
                startActivity(new Intent(this, OrdersActivity.class));
                break;

            case R.id.referandearn:
                startActivity(new Intent(this, ReferActivity.class));
                break;

            case R.id.btnEdit:
                fragment = new Profile(name,contact,email);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentFrame, fragment)
                        .addToBackStack(String.valueOf(fm))
                        .commit();
                frame.setVisibility(View.VISIBLE);
                break;
            case R.id.changepass:
                fragment = new ChangePassFragment(id);
                FragmentManager fm1 = getSupportFragmentManager();
                fm1.beginTransaction()
                        .replace(R.id.fragmentFrame, fragment)
                        .addToBackStack(String.valueOf(fm1))
                        .commit();
                frame.setVisibility(View.VISIBLE);
                break;
        }
    }
}
