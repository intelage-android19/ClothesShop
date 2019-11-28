package com.example.clothesshop.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.clothesshop.R;
import com.example.clothesshop.activity.ClothesActivity;
import com.example.clothesshop.databinding.FragmentKidBinding;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class KidFragment extends Fragment implements View.OnClickListener {


    ImageView type_top,type_dress,type_bottom,forever9,pluss,biba,kazo,tunics,plazzos,jeans,dress,kurti,ethnicwear,hotsummer,lehengas,offer;
    private String baseUrl="https://efunhub.in/Clothing/images/";
    private FragmentKidBinding mBinder;

    static String main_id;

    public KidFragment() {
        // Required empty public constructor
    }

    public static KidFragment newInstance(String id){

        KidFragment fragment = new KidFragment();

        main_id = id;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_kid, container, false);

        init();
        setImages();
        return mBinder.getRoot();
       // return inflater.inflate(R.layout.fragment_kid, container, false);
    }

    private void init() {
        type_bottom=mBinder.typeBottom;
        type_top=mBinder.typeTops;
        type_dress=mBinder.typeDress;
        forever9=mBinder.forever9;

        pluss=mBinder.pluss;
        biba=mBinder.biba;
        kazo=mBinder.kazo;
        kurti=mBinder.kurti;
   /*     tunics=mBinder.tunics;
        jeans=mBinder.jeans;
        dress=mBinder.dress;
        plazzos=mBinder.plazzos;*/
        ethnicwear=mBinder.ethnicwear;
        hotsummer=mBinder.hotsummer;
        lehengas=mBinder.lehengas;
        offer=mBinder.off;

        type_dress.setOnClickListener(this);
        type_bottom.setOnClickListener(this);
        type_top.setOnClickListener(this);
        forever9.setOnClickListener(this);
        pluss.setOnClickListener(this);
        biba.setOnClickListener(this);
        kurti.setOnClickListener(this);
        kazo.setOnClickListener(this);
     /*   plazzos.setOnClickListener(this);
        tunics.setOnClickListener(this);
        jeans.setOnClickListener(this);
        dress.setOnClickListener(this);*/
        ethnicwear.setOnClickListener(this);
        hotsummer.setOnClickListener(this);
        lehengas.setOnClickListener(this);
        offer.setOnClickListener(this);
    }

    private void setImages() {

        Picasso.with(getActivity())
                .load(baseUrl+"top_kids.jpg")
                .into(type_top);

        Picasso.with(getActivity())
                .load(baseUrl+"bottomwear_kids.jpg")
                .into(type_dress);

        Picasso.with(getActivity())
                .load(baseUrl+"dresses_kids.jpg")
                .into(type_bottom);

        Picasso.with(getActivity())
                .load(baseUrl+"mangikids.jpg")
                .into(forever9);

        Picasso.with(getActivity())
                .load(baseUrl+"gap.jpg")
                .into(pluss);

        Picasso.with(getActivity())
                .load(baseUrl+"gini_jony.jpg")
                .into(biba);

        Picasso.with(getActivity())
                .load(baseUrl+"612league.jpg")
                .into(kazo);
/*
        Picasso.with(getActivity())
                .load(baseUrl+"tshirtshirt_kids.jpg")
                .into(tunics);

        Picasso.with(getActivity())
                .load(baseUrl+"dess_jump.jpg")
                .into(plazzos);

        Picasso.with(getActivity())
                .load(baseUrl+"jeans_kids.jpg")
                .into(jeans);

        Picasso.with(getActivity())
                .load(baseUrl+"jackets_kids.jpg")
                .into(dress);*/

        Picasso.with(getActivity())
                .load(baseUrl+"tshirtshirt_kids.jpg")
                .into(kurti);

        Picasso.with(getActivity())
                .load(baseUrl+"ethnickids.jpg")
                .into(ethnicwear);

        Picasso.with(getActivity())
                .load(baseUrl+"shirts_kids.jpg")
                .into(hotsummer);

        Picasso.with(getActivity())
                .load(baseUrl+"hotsummer_kids.jpg")
                .into(lehengas);

        Picasso.with(getActivity())
                .load(baseUrl+"kidsoff.jpg")
                .into(offer);

//        Picasso.get()
//                .load(url)
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
//                .into(imageView);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id){
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

          /*  case R.id.jeans:
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

                break;
*/
            case R.id.ethnicwear:
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
                break;
        }
    }


}
