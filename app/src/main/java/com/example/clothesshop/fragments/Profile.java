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
import com.example.clothesshop.R;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.CHANGE_PASSWORD;
import static com.example.clothesshop.utility.ConstantVariables.PROFILE_DATA;
import static com.example.clothesshop.utility.ConstantVariables.UPDATE_PROFILE;
import static com.example.clothesshop.utility.SessionManager.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Profile extends Fragment {


    View view;
    Toolbar toolbar;
    ImageView back;
    TextView title;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String updateProfile = "Customer-Update-Profile";
    private String updatePassword = "Customer-Update-Password";

    CheckConnectivity checkConnectivity;
    LinearLayout imgNoDataFound;
    SessionManager sessionManager;

    String id,name,conact,email,address;

    EditText edtName, edtMobile, edtEmail;

    Button btnUpdateProfile;


    @SuppressLint("ValidFragment")
    public Profile(String name, String contact, String email) {

        this.name = name;
        this.conact = contact;
        this.email = email;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        back = view.findViewById(R.id.btnBack);
        title = view.findViewById(R.id.tvTitle);
        edtName = view.findViewById(R.id.editName);
        edtMobile = view.findViewById(R.id.editEmail);
        edtEmail = view.findViewById(R.id.editMb);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);

        title.setText("Update Profile");

        edtName.setText(name);
        edtMobile.setText(conact);
        edtEmail.setText(email);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });


        return view;
    }


    private void updateProfile() {

        sessionManager = new SessionManager(getContext());

        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        Map<String, String> params = new HashMap<>();

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getContext());

        id = userInfo.get(KEY_ID);

        params.put("customer_auto_id", id);
        params.put("name", edtName.getText().toString());
        params.put("email", edtEmail.getText().toString());
        params.put("contact", edtMobile.getText().toString());
        params.put("country_code", "91");



        mVolleyService.postDataVolleyParameters(UPDATE_PROFILE, this.getResources().getString(R.string.base_url) + updateProfile, params);

    }

/*
    private void updatePassword() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getContext());

        Map<String, String> params = new HashMap<>();

        params.put("oldp", .getText().toString());
        params.put("newp", edtNewp.getText().toString());
        params.put("customer_auto_id", id);

        mVolleyService.postDataVolleyParameters(CHANGE_PASSWORD, this.getResources().getString(R.string.base_url) + updatePassword, params);
    }
*/

    private void initVolleyCallback() {
        // linearProgressBar.setVisibility(View.VISIBLE);
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case UPDATE_PROFILE:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            String  message = jsonObj.getString("msg");

                            if (status == 1) {
                                Toast.makeText(getContext(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().popBackStack();
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
