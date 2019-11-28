package com.example.clothesshop.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.clothesshop.utility.ConstantVariables.USER_DATA;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    // Session Manager
    SessionManager sessionManager;

    private IResult mResultCallback;
    private VolleyService mVollyService;
    private String userData = "GetCustomerAllData";
    String android_id;

    ProgressDialog progressDialog;
    Dialog alertDialog;
    String msg, appName, appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(MainActivity.this);
        splashScreen();
        requestReadPhoneStatePermission();
    }

    private void splashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sessionManager.checkLogin();


            }
        }, SPLASH_TIME_OUT);
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void requestReadPhoneStatePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_PHONE_STATE)
                .withListener(new PermissionListener
                        () {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted


                        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext()
                                .getSystemService(Context.TELEPHONY_SERVICE);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        android_id = telephonyManager.getDeviceId()
                                .toString();
                        // Toast.makeText(SplashActivity.this, "Device Id "+android_id, Toast.LENGTH_SHORT).show();
                        Log.e("device id-", android_id);

                        if (!android_id.isEmpty()) {
                            getUserData();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        requestReadPhoneStatePermission();
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                    }

                }).check();
    }


    private void getUserData() {

        initVolleyCallback();

        mVollyService = new VolleyService(mResultCallback, this);

        HashMap<String, String> params = new HashMap<>();
        params.put("customer_unique_id", android_id);
        params.put("main_category_id", "MCAT101");

        Log.v("id", android_id);

        mVollyService.postDataVolleyParameters(USER_DATA,
                this.getResources().getString(R.string.base_url) + userData, params);
    }


    private void initVolleyCallback() {
        //    progressDialog.show();
        try {

            mResultCallback = new IResult() {
                @Override
                public void notifySuccess(int requestId, String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        int status = jsonObject.getInt("status");

                        if (status == 0) {
                            msg = jsonObject.getString("msg");
                            //  progressDialog.dismiss();
                            // dialogNoData();
                        }
                        if (status == 1) {

                            //   Toast.makeText(getApplicationContext(), "Getting Data", Toast.LENGTH_SHORT).show();

                            JSONArray allProducts = jsonObject.getJSONArray("order_details");

                            for (int j = 0; j < allProducts.length(); j++) {

                                JSONObject jsonObjectMenu = allProducts.getJSONObject(j);


                                String prodnames = jsonObjectMenu.getString("feature_name");
                                appName = jsonObjectMenu.getString("business_name");
                                appLogo = jsonObjectMenu.getString("logo_image");

                                //   tvAppname.setText(appName);

                                String[] namearray = prodnames.split("\\|");

                                sessionManager.setfeaturename(namearray);
                                sessionManager.setAppName(appName);
                                sessionManager.setAppLogo(appLogo);
//progressDialog.dismiss();
                                sessionManager.checkLogin();
                                finish();


                            }
                        }

                    } catch (JSONException e) {

//progressDialog.dismiss();
                        e.printStackTrace();
                    }

                    //   progressDialog.dismiss();
                }

                @Override
                public void notifyError(int requestId, VolleyError error) {
                    Log.v("Volley requestid ", String.valueOf(requestId));
                    Log.v("Volley Error", String.valueOf(error));
//progressDialog.dismiss();
                }
            };
        } catch (Exception ex) {
//progressDialog.dismiss();
            Log.d("RegisterationActivity", "initVolleyCallback: " + ex);
        }

    }


    private void dialogNoData() {


        alertDialog = new AlertDialog.Builder(this)
                .setTitle(this.getResources().getString(R.string.app_name))
                .setMessage(msg)
                .show();

        alertDialog.setCancelable(false);


    }


}
