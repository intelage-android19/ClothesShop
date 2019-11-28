package com.example.clothesshop.utility;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.interfaces.IResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.clothesshop.utility.ConstantVariables.BLOCK_STATUS;
import static com.example.clothesshop.utility.SessionManager.KEY_ID;


public class BlockStatusService extends Service {
    private boolean isRunning;
    private Context mContext;
    private Handler handler;
    private Timer timer;
    private TimerTask doAsynchronousTask;
    private SessionManager sessionManager;
    private String sid;

    private VolleyService mVolleyService;
    private IResult mResultCallBack = null;

    private String BlockStatusURL = "CustomerStatus";
    AlertDialog alertDialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.mContext = this;
        this.isRunning = false;
        handler = new Handler();
        timer = new Timer();
        sessionManager = new SessionManager(this);
    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
        doAsynchronousTask.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;

            // Timer task makes your service will repeat after every 20 Sec.
            doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            getBlockStatus();
                        }

                    });
                }
            };
            //Starts after 20 sec and will repeat on every 20 sec of time interval.
            timer.schedule(doAsynchronousTask, 20000, 20000);  // 20 sec timer
        }
        return START_STICKY;
    }

    private void getBlockStatus() {

        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        sid = userInfo.get(KEY_ID);

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallBack, mContext);

        Map<String, String> params = new HashMap<>();
        params.put("customer_auto_id", sid);

        mVolleyService.postDataVolleyParameters(BLOCK_STATUS, getApplicationContext().getResources().getString(R.string.base_url) + BlockStatusURL, params);
    }

    private void initVolleyCallback() {

        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response);

                    JSONArray allProducts = jsonObject.getJSONArray("customer_status");

                    for (int j = 0; j < allProducts.length(); j++) {

                        JSONObject jsonObjectMenu = allProducts.getJSONObject(j);

                        String status = jsonObjectMenu.getString(("status"));

                        if (status.equalsIgnoreCase("Block")) {

                            Intent intent = new Intent();
                            intent.setAction("com.efunhub.hotelbooking");
                            sendBroadcast(intent);
                            Toast.makeText(mContext, "Your account is blocked", Toast.LENGTH_SHORT).show();
                            sessionManager.logoutUser();
                            doAsynchronousTask.cancel();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                Log.v("Volley RequestId", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }


}
