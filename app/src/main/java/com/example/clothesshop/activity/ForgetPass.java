package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.clothesshop.R;
import com.example.clothesshop.Validations;
import com.example.clothesshop.databinding.ActivityForgetPassBinding;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.VolleyService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.FORGOT_PASS;

public class ForgetPass extends AppCompatActivity implements View.OnClickListener {

    private ActivityForgetPassBinding mBinder;
    EditText etEmail;
    Button btnSend;
    ImageView btnBack;
    Validations validations=new Validations();
    AwesomeValidation awesomeValidation;
    LinearLayout layout,noNetwork;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String forgotPasswordUrl = "Customer-Forgot-Password";


CheckConnectivity checkConnectivity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_forget_pass);
checkConnectivity = new CheckConnectivity();
        initdata();
        addEvent();
    }

    private void addEvent() {
        btnSend.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void initdata() {
        etEmail=mBinder.etEmail;
        btnBack=mBinder.toolbar.btnBack;
        btnSend=mBinder.btnSendPass;
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        layout=mBinder.layout;
        //noNetwork=findViewById(R.id.network);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;

            case R.id.btnSendPass:
                sendPass();
                break;
        }
    }



//    public void checkNetworkConnection() {
//        CheckConnectivity checkConnectivity = new CheckConnectivity(this);
//
//        boolean connected = checkConnectivity.checkNetwork();
//        if (connected) {
//            noNetwork.setVisibility(View.GONE);
//            layout.setVisibility(View.VISIBLE);
//        } else {
//            noNetwork.setVisibility(View.VISIBLE);
//            layout.setVisibility(View.GONE);
//        }
//    }




    private void forgotPassword() {

        initVolleyCallback();

        // pbForgotPass.setVisibility(View.VISIBLE);
        //btnSend.setVisibility(View.GONE);

        mVolleyService = new VolleyService(mResultCallback, this);

        Map<String, String> params = new HashMap<>();
        params.put("email", etEmail.getText().toString());

        mVolleyService.postDataVolleyParameters(FORGOT_PASS,
                getApplicationContext().getResources().getString(R.string.base_url) + forgotPasswordUrl,
                params);

    }


    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case FORGOT_PASS:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");

                            if (status == 0) {
                                Toast.makeText(ForgetPass.this, "Sorry, an account not exist with this email", Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {

                                Toast.makeText(ForgetPass.this, "Success, Please check your email", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (Exception e) {

                            Log.v("ForgotPasswordActivity", e.toString());
                        }
                        //  pbForgotPass.setVisibility(View.GONE);
                        //   btnSend.setVisibility(View.VISIBLE);
                        break;


                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {

              //  imgNoDataFound.setVisibility(View.VISIBLE);
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                //   pbForgotPass.setVisibility(View.GONE);
                //  btnSend.setVisibility(View.VISIBLE);
            }
        };
    }



    private void sendPass() {
        validations.checkEmail(awesomeValidation,R.id.etEmail,this);
        if (awesomeValidation.validate()){
           forgotPassword();
        }
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
