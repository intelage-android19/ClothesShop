package com.example.clothesshop.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.activity.ClothesActivity;
import com.example.clothesshop.adapter.NewArrivalsAdapter;
import com.example.clothesshop.adapter.TopBrandsAdapter;
import com.example.clothesshop.databinding.FragmentMenBinding;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.NewArrivalModel;
import com.example.clothesshop.model.NewArrivalsBaseModel;
import com.example.clothesshop.model.SubCategoryBaseModel;
import com.example.clothesshop.model.SubCategoryModel;
import com.example.clothesshop.model.TopBrandBaseModel;
import com.example.clothesshop.model.TopBrandModel;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.clothesshop.utility.ConstantVariables.GET_SUB_CATEGORY;
import static com.example.clothesshop.utility.ConstantVariables.NEW_ARRIVALS;
import static com.example.clothesshop.utility.ConstantVariables.TOP_BRANDS;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    ImageView type_jacket, type_top, type_bottom, tunics, plazzos, jeans, dress, kurti, ethnicwear, hotsummer, lehengas, offer;
    private FragmentMenBinding mBinder;
    private String baseUrl = "https://efunhub.in/Clothing/images/";

    static String main_id;

    CheckConnectivity checkConnectivity;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String getSubCategory = "GetSubCategory";
    private String getTopBrands = "TopBrands";
    private String getNewArrivals = "NewArrivals";

    List<SubCategoryModel> subCategoryModelsList = new ArrayList<>();
    List<TopBrandModel> topBrandModelsList = new ArrayList<>();
    List<NewArrivalModel> newArrivalModelList = new ArrayList<>();

    TopBrandsAdapter topBrandsAdapter;
    NewArrivalsAdapter newArrivalsAdapter;
    String sub_category;
    RelativeLayout progress_gif;

    public static HomeFragment newInstance(String id) {

        HomeFragment fragment = new HomeFragment();

        main_id = id;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_men, container, false);

        init();
        getData();
        getSubCategory();
        getTopBrands();
        getNewArrivals();
        setImages();

        return mBinder.getRoot();
        //return inflater.inflate(R.layout.fragment_men, container, false);
    }

    private void getData() {

        Bundle bundle = getArguments();
        main_id = bundle.getString("main_id");


    }

    private void setImages() {


/*

        Picasso.with(getActivity())
                .load(baseUrl + "tshirt_men.jpg")
                .into(type_jacket);

        Picasso.with(getActivity())
                .load(baseUrl + "formal_men.jpg")
                .into(type_bottom);
*/

    /*    Picasso.with(getActivity())
                .load(baseUrl + "skult.jpg")
                .into(forever9);

        Picasso.with(getActivity())
                .load(baseUrl + "spykar.jpg")
                .into(pluss);

        Picasso.with(getActivity())
                .load(baseUrl + "pluss_men.jpg")
                .into(biba);

        Picasso.with(getActivity())
                .load(baseUrl + "peterengland.jpg")
                .into(kazo);*/

       /* Picasso.with(getActivity())
                .load(baseUrl+"tshitshirt.jpg")
                .into(tunics);

        Picasso.with(getActivity())
                .load(baseUrl+"formals.jpg")
                .into(plazzos);

        Picasso.with(getActivity())
                .load(baseUrl+"jeans_men.jpg")
                .into(jeans);

        Picasso.with(getActivity())
                .load(baseUrl+"jackets_men.png")
                .into(dress);
*/
       /* Picasso.with(getActivity())
                .load(baseUrl + "ethnicmen.jpg")
                .into(kurti);

        Picasso.with(getActivity())
                .load(baseUrl + "shirt1.jpg")
                .into(ethnicwear);

        Picasso.with(getActivity())
                .load(baseUrl + "tshirt_men1.png")
                .into(hotsummer);

        Picasso.with(getActivity())
                .load(baseUrl + "hotsummer_men.jpg")
                .into(
                );*/

        Picasso.with(getActivity())
                .load(baseUrl + "off_men.jpg")
                .into(offer);


//        Picasso.get()
//                .load(url)
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
//                .into(imageView);

    }

    private void init() {
        type_bottom = mBinder.imgBottomWear;
        type_top = mBinder.imgTopWear;
        type_jacket = mBinder.imgJacket;
        // kurti = mBinder.kurti;
   /*     tunics=mBinder.tunics;
        jeans=mBinder.jeans;
        dress=mBinder.dress;
        plazzos=mBinder.plazzos;
*/
      /*  ethnicwear = mBinder.ethnicwear;
        hotsummer = mBinder.hotsummer;
        lehengas = mBinder.lehengas;*/
        offer = mBinder.off;

        type_jacket.setOnClickListener(this);
        type_bottom.setOnClickListener(this);
        type_top.setOnClickListener(this);
        // kurti.setOnClickListener(this);
      /*  plazzos.setOnClickListener(this);
        tunics.setOnClickListener(this);
        jeans.setOnClickListener(this);
        dress.setOnClickListener(this);*/
        // ethnicwear.setOnClickListener(this);
        // hotsummer.setOnClickListener(this);
        // lehengas.setOnClickListener(this);
        offer.setOnClickListener(this);
    }

    private void getSubCategory() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getContext());

        HashMap<String, String> params = new HashMap<>();
        params.put("main_category_auto_id", main_id);

        mVolleyService.postDataVolleyParameters(GET_SUB_CATEGORY,
                this.getResources().getString(R.string.base_url) + getSubCategory, params);
    }

    private void getTopBrands() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getContext());

        HashMap<String, String> params = new HashMap<>();
        params.put("main_category_auto_id", main_id);

        mVolleyService.postDataVolleyParameters(TOP_BRANDS,
                this.getResources().getString(R.string.base_url) + getTopBrands, params);
    }

    private void getNewArrivals() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getContext());

        HashMap<String, String> params = new HashMap<>();
        params.put("main_category_auto_id", main_id);

        mVolleyService.postDataVolleyParameters(NEW_ARRIVALS,
                this.getResources().getString(R.string.base_url) + getNewArrivals, params);
    }

    private void initVolleyCallback() {

        mBinder.progressGif.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case GET_SUB_CATEGORY:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {

                                Gson gson = new Gson();
                                SubCategoryBaseModel subCategoryBaseModel = gson.fromJson(response, SubCategoryBaseModel.class);
                                subCategoryModelsList = subCategoryBaseModel.getAllSubcategories();

                                setSubCategoryImages();
                                mBinder.progressGif.setVisibility(View.GONE);

                            } else {
                                mBinder.progressGif.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            mBinder.progressGif.setVisibility(View.GONE);
                            Log.v("cuisines exception", e.toString());
                        }
                        break;

                    case TOP_BRANDS:
                        try {
                            topBrandModelsList.clear();
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {

                                Gson gson = new Gson();

                                TopBrandBaseModel topBrandBaseModel = gson.fromJson(response, TopBrandBaseModel.class);

                                List<TopBrandModel> topBrandModels = topBrandBaseModel.getTopBrands();


                                for (TopBrandModel topBrandModel : topBrandModels) {
                                    topBrandModelsList.add(topBrandModel);

                                    System.out.println(topBrandModel.getId());
                                }


                                setTopBrand();
                                mBinder.progressGif.setVisibility(View.GONE);


                            }
                            if (topBrandModelsList.isEmpty()) {

                                mBinder.txtBrands.setVisibility(View.GONE);

                                mBinder.progressGif.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            mBinder.progressGif.setVisibility(View.GONE);

                            Log.v("cuisines exception", e.toString());
                        }
                        break;

                    case NEW_ARRIVALS:
                        try {
                            newArrivalModelList.clear();
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");

                            if (status == 1) {

                                Gson gson = new Gson();

                                NewArrivalsBaseModel newArrivalsBaseModel = gson.fromJson(response, NewArrivalsBaseModel.class);

                                List<NewArrivalModel> newArrivalModels = newArrivalsBaseModel.getNewarrivals();


                                for (NewArrivalModel newArrivalModel : newArrivalModels) {
                                    newArrivalModelList.add(newArrivalModel);
                                }


                                setNewArrivals();
                                mBinder.progressGif.setVisibility(View.GONE);

                            }
                            if (newArrivalModelList.isEmpty()) {

                                mBinder.txtNewArrival.setVisibility(View.GONE);

                                mBinder.progressGif.setVisibility(View.GONE);

                            } else {
                                // isCuisine = false;
                            }
                        } catch (Exception e) {
                            mBinder.progressGif.setVisibility(View.GONE);

                            Log.v("cuisines exception", e.toString());
                        }
                        break;

                } //cvPgBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                mBinder.progressGif.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }


    private void setSubCategoryImages() {


        String typetop = (subCategoryModelsList.get(0).getName());
        mBinder.txtTopWear.setVisibility(View.VISIBLE);
        mBinder.txtTopWear.setText(typetop);
        Picasso.with(getActivity())
                .load(getResources().getString(R.string.sub_category_image_url) + subCategoryModelsList.get(0).getSubcategoryImage())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(type_top);


        String typeBottom = (subCategoryModelsList.get(1).getName());
        mBinder.txtBottom.setVisibility(View.VISIBLE);
        mBinder.txtBottom.setText(typeBottom);
        Picasso.with(getActivity())
                .load(getResources().getString(R.string.sub_category_image_url) + subCategoryModelsList.get(1).getSubcategoryImage())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(type_bottom);


        String typeJacket = (subCategoryModelsList.get(2).getName());
        mBinder.txtMenJacket.setVisibility(View.VISIBLE);
        mBinder.txtMenJacket.setText(typeJacket);
        Picasso.with(getActivity())
                .load(getResources().getString(R.string.sub_category_image_url) + subCategoryModelsList.get(2).getSubcategoryImage())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(type_jacket);

    }

    private void setTopBrand() {

        //   recyclerView.setHasFixedSize(true);
        topBrandsAdapter = new TopBrandsAdapter(getActivity(), topBrandModelsList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        mBinder.rvTopBrands.setLayoutManager(gridLayoutManager);
        mBinder.rvTopBrands.setAdapter(topBrandsAdapter);


    }

    private void setNewArrivals() {

        //   recyclerView.setHasFixedSize(true);
        newArrivalsAdapter = new NewArrivalsAdapter(getActivity(), newArrivalModelList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        mBinder.rvNewArrivals.setLayoutManager(gridLayoutManager);
        mBinder.rvNewArrivals.setAdapter(newArrivalsAdapter);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imgBottomWear:
                Intent dressintent = new Intent(getActivity(), ClothesActivity.class);
                if (!subCategoryModelsList.isEmpty()){
                    dressintent.putExtra("main_id", subCategoryModelsList.get(1).getMainCategoryAutoId());
                    dressintent.putExtra("sub_category", subCategoryModelsList.get(1).getId());
                    startActivity(dressintent);

                }else
                {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
                 break;

            case R.id.imgJacket:
                if (!subCategoryModelsList.isEmpty()) {

                    Intent i = new Intent(getActivity(), ClothesActivity.class);
                    i.putExtra("main_id", main_id);
                    i.putExtra("sub_category", subCategoryModelsList.get(2).getId());
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.imgTopWear:
                if (!subCategoryModelsList.isEmpty()) {

                    Intent intent = new Intent(getActivity(), ClothesActivity.class);
                    intent.putExtra("main_id", subCategoryModelsList.get(0).getMainCategoryAutoId());
                    intent.putExtra("sub_category", subCategoryModelsList.get(0).getId());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.forever9:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;

            case R.id.kurti:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;

            case R.id.pluss:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.kazo:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.biba:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;
/*
            case R.id.jeans:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.dress:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.tunics:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.plazzos:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;*/

          /*  case R.id.ethnicwear:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.hotsummer:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.lehengas:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.off:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;*/
        }
    }


}
