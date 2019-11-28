package com.example.clothesshop.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.clothesshop.R;
import com.example.clothesshop.Validations;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.CHANGE_PASSWORD;
import static com.example.clothesshop.utility.ConstantVariables.UPDATE_PROFILE;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ChangePassFragment extends Fragment {


    View view;
    Toolbar toolbar;
    ImageView back;
    TextView title;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String updatePassword = "Customer-Update-Password";

    CheckConnectivity checkConnectivity;
    LinearLayout imgNoDataFound;
    SessionManager sessionManager;

    EditText edtOldP,edtNewP;
    Button btnUpdatePassword;
    String id;

    AwesomeValidation awesomeValidation;
    Validations validations=new Validations();

    @SuppressLint("ValidFragment")
    public ChangePassFragment(String id) {
        this.id =id;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_change_pass, container, false);

        toolbar=view.findViewById(R.id.toolbar);
        back=view.findViewById(R.id.btnBack);
        title=view.findViewById(R.id.tvTitle);

        edtOldP =  view.findViewById(R.id.etOldP);
        edtNewP = view.findViewById(R.id.edtnewPasssword);
        btnUpdatePassword = view.findViewById(R.id.btnUpdatePassword);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validations.checkPass(awesomeValidation,R.id.etOldP,getActivity());
                validations.checkLoginPass(awesomeValidation,R.id.edtnewPasssword,getActivity());
                if (awesomeValidation.validate()){
                    updatePassword();
                }

            }
        });
        title.setText("Change Password");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;

    }


    private void updatePassword() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getContext());

        HashMap<String, String> params = new HashMap<>();

        params.put("oldp", edtOldP.getText().toString());
        params.put("newp", edtNewP.getText().toString());
        params.put("customer_auto_id", id);


        mVolleyService.postDataVolleyParameters(CHANGE_PASSWORD, this.getResources().getString(R.string.base_url) + updatePassword, params);
    }

    private void initVolleyCallback() {
        // linearProgressBar.setVisibility(View.VISIBLE);
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {


                    case CHANGE_PASSWORD:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            String msg = jsonObj.getString("msg");

                            if (status == 0) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                Toast.makeText(getContext(), "Password has been changed successfully.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {

                            Log.v("Update_profile_dialog", e.toString());
                        }
                        break;


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

}
