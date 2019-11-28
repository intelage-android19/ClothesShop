package com.example.clothesshop.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.clothesshop.R;
import com.example.clothesshop.Validations;
import com.example.clothesshop.databinding.ActivitySignUpBinding;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.REGISTER_CUSTOMER;
import static com.example.clothesshop.utility.SessionManager.KEY_AGENT_ID;
import static com.example.clothesshop.utility.SessionManager.KEY_NAME;
import static com.example.clothesshop.utility.SessionManager.KEY_OPERATOR_ID;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignUpBinding mBinder;
    EditText etMobile,etPassword,etName,etemail;
    Button btnSignUp;
    TextView btnLogin;
    ImageView btnBack;
    AwesomeValidation awesomeValidation;
    Validations validations=new Validations();
    ScrollView layout;
    LinearLayout noNetwork;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    String registerCustomer = "Customer-Registration";
    SessionManager sessionManager;

   // CountryCodePicker ccp;
    AppCompatEditText edtPhoneNumber;
    String countrycode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        initdata();
        addEvent();

    }

    private void registerCustomer() {

        sessionManager = new SessionManager(SignUp.this);

        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        String referrerId = userInfo.get(SessionManager.REFERRER_ID);


        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, SignUp.this);

        Map<String, String> params = new HashMap<>();

        params.put("name", etName.getText().toString());
        params.put("email", etemail.getText().toString());
        params.put("contact", etMobile.getText().toString());
        params.put("country_code","91");
        params.put("password", etPassword.getText().toString());

        if (referrerId != null) {
            params.put("referrer_id", referrerId);
        } else {
            params.put("referrer_id", "");
        }


        Log.v("Register Activity", String.valueOf(params));

        mVolleyService.postDataVolleyParameters(REGISTER_CUSTOMER, this.getResources().getString(R.string.base_url) + registerCustomer, params);

    }

    private void initVolleyCallback() {

     //   pbRegisterCustomer.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case REGISTER_CUSTOMER:
                        try {

                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            String message = jsonObj.getString("msg");

                            if (status == 0) {
                                Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {

                                Toast.makeText(SignUp.this, "Register Successfully", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {

                            Log.v("Customer Activity", e.toString());
                        }

                        break;

                }
              //  pbRegisterCustomer.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                Toast.makeText(SignUp.this, "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            //    pbRegisterCustomer.setVisibility(View.GONE);

            }
        };
    }




    private void addEvent() {
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void initdata() {
        etMobile=mBinder.etMobile;
        etPassword=mBinder.etPassword;
        etName=mBinder.etName;
        etemail=mBinder.etEmail;
        btnLogin=mBinder.tvLogin;
        btnSignUp=mBinder.btnsignUp;
        btnBack=mBinder.toolbar.btnBack;
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        layout=mBinder.layout;

/*

        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);

        countrycode = ccp.getSelectedCountryCode();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                countrycode = ccp.getSelectedCountryCode();
            }
        });
*/



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;

            case R.id.tvLogin:
                finish();
                break;

            case R.id.btnsignUp:
                performSignUp();
                break;
        }
    }

    private void performSignUp() {
        validations.checkName(awesomeValidation,R.id.etName,this);
        validations.checkEmail(awesomeValidation,R.id.etEmail,this);
        validations.checkMobile(awesomeValidation,R.id.etMobile,this);
        validations.checkPass(awesomeValidation,R.id.etPassword,this);
        if (awesomeValidation.validate()){

            registerCustomer();

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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

       // checkNetworkConnection();
    }
}
