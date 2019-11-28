package com.example.clothesshop.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.activity.ClothesActivity;
import com.example.clothesshop.databinding.FragmentHomeBinding;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.MainCategoryBasemodel;
import com.example.clothesshop.model.MainCategoryModel;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.clothesshop.utility.ConstantVariables.MAIN_CATEGORIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class WomanFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding mBinder;
    private View view;
    private String baseUrl = "https://efunhub.in/Clothing/images/";
    ImageView type_top, type_dress, type_bottom, forever9, pluss, biba, kazo, tunics, plazzos, jeans, dress, kurti, ethnicwear, hotsummer, lehengas, offer;

    RelativeLayout pgGif;

    static String main_id;

    //Volley Service
    VolleyService mVolleyService;
    IResult mIResultCallback;
    final String get_categories_url = "ShowMainCategory";

    MainCategoryBasemodel mainCategoryBasemodel;
    List<MainCategoryModel> mainCategoryModelList = new ArrayList<>();

    public static WomanFragment newInstance(String id) {

        WomanFragment womanFragment = new WomanFragment();

        main_id = id;

        return womanFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        setImages();
        getCategories();
        return mBinder.getRoot();
    }

    private void init() {
        type_bottom = mBinder.typeBottom;
        type_top = mBinder.typeTops;
        type_dress = mBinder.typeDress;
        forever9 = mBinder.forever9;
       // plazzos = mBinder.plazzos;
        pluss = mBinder.pluss;
        biba = mBinder.biba;
        kazo = mBinder.kazo;
        kurti = mBinder.kurti;
      //  tunics = mBinder.tunics;
     //   jeans = mBinder.jeans;
      //  dress = mBinder.dress;
        ethnicwear = mBinder.ethnicwear;
        hotsummer = mBinder.hotsummer;
        lehengas = mBinder.lehengas;
        offer = mBinder.off;
        pgGif = (RelativeLayout) mBinder.pgGif;

        type_dress.setOnClickListener(this);
        type_bottom.setOnClickListener(this);
        type_top.setOnClickListener(this);
        forever9.setOnClickListener(this);
        pluss.setOnClickListener(this);
        biba.setOnClickListener(this);
        kurti.setOnClickListener(this);
        kazo.setOnClickListener(this);
      //  plazzos.setOnClickListener(this);
      //  tunics.setOnClickListener(this);
       // jeans.setOnClickListener(this);
      //  dress.setOnClickListener(this);
        ethnicwear.setOnClickListener(this);
        hotsummer.setOnClickListener(this);
        lehengas.setOnClickListener(this);
        offer.setOnClickListener(this);
    }

    private void setImages() {

        Picasso.with(getActivity())
                .load(baseUrl + "topwear_women.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(type_top);

        Picasso.with(getActivity())
                .load(baseUrl + "dress_women.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(type_dress);



        Picasso.with(getActivity())
                .load(baseUrl + "forever9.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(forever9);

        Picasso.with(getActivity())
                .load(baseUrl + "pluss.jpeg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(pluss);

        Picasso.with(getActivity())
                .load(baseUrl + "biba.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(biba);

        Picasso.with(getActivity())
                .load(baseUrl + "kazo.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(kazo);

      /*  Picasso.with(getActivity())
                .load(baseUrl + "tops_tunics.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(tunics);

        Picasso.with(getActivity())
                .load(baseUrl + "plazzo.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(plazzos);

        Picasso.with(getActivity())
                .load(baseUrl + "jeans_jeggings.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(jeans);

        Picasso.with(getActivity())
                .load(baseUrl + "dresses.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(dress);*/

        Picasso.with(getActivity())
                .load(baseUrl + "kurti.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(kurti);

        Picasso.with(getActivity())
                .load(baseUrl + "ethnic_women.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(ethnicwear);

        Picasso.with(getActivity())
                .load(baseUrl + "hotsummer.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(hotsummer);

        Picasso.with(getActivity())
                .load(baseUrl + "lehenga.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(lehengas);

        Picasso.with(getActivity())
                .load(baseUrl + "off_women.jpg")
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(offer);

//        Picasso.get()
//                .load(url)
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
//                .into(imageView);

    }

    private void getCategories() {
        initVolleyCallback();

        mVolleyService = new VolleyService(mIResultCallback, getActivity());

        String url = getActivity().getResources().getString(R.string.base_url) + get_categories_url;

        mVolleyService.getDataVolley(MAIN_CATEGORIES, url);
    }

    private void initVolleyCallback() {
        //   pgGif.setVisibility(View.VISIBLE);
        mIResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                pgGif.setVisibility(View.GONE);
                JSONObject jsonObj = null;
                switch (requestId) {
                    case MAIN_CATEGORIES:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {
                                Gson gson = new Gson();
                                MainCategoryBasemodel mainCategoryBasemodel = gson.fromJson(response, MainCategoryBasemodel.class);
                                mainCategoryModelList = mainCategoryBasemodel.getAllmaincategories();

                            } else {
                                // isCuisine = false;
                            }
                        } catch (Exception e) {
                            pgGif.setVisibility(View.GONE);

                            Log.v("cuisines exception", e.toString());
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                pgGif.setVisibility(View.GONE);
                Log.v("cuisines error", error.toString());
            }
        };
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.type_bottom:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;

            case R.id.type_dress:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;

            case R.id.type_tops:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.forever9:
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

         /*   case R.id.jeans:
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

            case R.id.ethnicwear:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.hotsummer:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.kurti:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;

            case R.id.lehengas:
                startActivity(new Intent(getActivity(), ClothesActivity.class));

                break;

            case R.id.off:
                startActivity(new Intent(getActivity(), ClothesActivity.class));
                break;
        }
    }
}
