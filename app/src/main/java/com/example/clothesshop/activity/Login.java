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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.clothesshop.R;
import com.example.clothesshop.Validations;
import com.example.clothesshop.databinding.ActivityLoginBinding;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.LOGIN;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding mBinder;
    EditText etMobile,etPassword;
    Button btnLogin;
    TextView btnForgetPass,btnSignUp;
    AwesomeValidation awesomeValidation;
    Validations validations=new Validations();
    LinearLayout layout,noNetwork;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    SessionManager sessionManager;

    //all urls
    private String LOGIN_URL = "Customer-Login";


    CheckConnectivity checkConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initdata();
        addEvent();
    }

    private void addEvent() {
        btnLogin.setOnClickListener(this);
        btnForgetPass.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        checkConnectivity = new CheckConnectivity();


    }

    private void initdata() {
        etMobile=mBinder.etMobile;
        etPassword=mBinder.etPassword;
        btnLogin=mBinder.btnLogIn;
        btnForgetPass=mBinder.tvForgotPass;
        btnSignUp=mBinder.tvNewUser;
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        layout=mBinder.layout;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogIn:
                performLogin();
                break;

            case R.id.tvForgotPass:
                Intent intent1=new Intent(Login.this,ForgetPass.class);
                startActivity(intent1);
                break;
            case R.id.tvNewUser:
                Intent intent2=new Intent(Login.this,SignUp.class);
                startActivity(intent2);
                break;

        }

    }

    private void performLogin() {
        validations.checkMobile(awesomeValidation,R.id.etMobile,this);
        validations.checkLoginPass(awesomeValidation,R.id.etPassword,this);
        if (awesomeValidation.validate()){
          loginCustomer();
        }
    }

    private void loginCustomer() {

        sessionManager = new SessionManager(this);

        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        Map<String, String> params = new HashMap<>();

        params.put("contact", etMobile.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("country_code", "91");

        Log.v("Register Activity", String.valueOf(params));

        mVolleyService.postDataVolleyParameters(LOGIN, this.getResources().getString(R.string.base_url) + LOGIN_URL, params);

    }

    private void initVolleyCallback() {

       // linearProgressBar.setVisibility(View.VISIBLE);


        mResultCallback = new IResult() {

            @Override
            public void notifySuccess(int requestId, String response) {

                JSONObject jsonObj = null;

                switch (requestId) {

                    case LOGIN:
                        try {

                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            String message = jsonObj.getString("msg");

                            if (status == 0) {
                                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                String Id = jsonObj.getString("customer_auto_id");
                                String name = jsonObj.getString("name");
                                String contact = jsonObj.getString("contact");
                                String email = jsonObj.getString("email");

                                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                               sessionManager.createLoginSession(Id,name,contact,email);
                                startActivity(new Intent(Login.this, HomeActivity.class));
                            }
                        } catch (Exception e) {

                            Log.v("Login Activity", e.toString());
                        }

                        break;

                }
             //   linearProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                Toast.makeText(Login.this, "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            //    linearProgressBar.setVisibility(View.GONE);

            }
        };
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
