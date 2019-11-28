package com.example.clothesshop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clothesshop.R;
import com.example.clothesshop.activity.ProdDetailsActivity;
import com.example.clothesshop.interfaces.AddToCartListener;
import com.example.clothesshop.model.NewArrivalModel;
import com.example.clothesshop.model.TopBrandModel;
import com.example.clothesshop.utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewArrivalsAdapter extends RecyclerView.Adapter<NewArrivalsAdapter.Holder> {

    List<NewArrivalModel> itemlist;
    Activity activity;
    AddToCartListener addToCartListener;
    SessionManager sessionManager;


    public NewArrivalsAdapter(FragmentActivity activity, List<NewArrivalModel> newArrivalModelList) {

        this.itemlist = newArrivalModelList;
        this.activity = activity;
        this.addToCartListener= (AddToCartListener) activity;
        sessionManager=new SessionManager(activity);
    }


    @NonNull
    @Override
    public NewArrivalsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.top_brands,null);
        return new NewArrivalsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewArrivalsAdapter.Holder holder, int i) {
        final NewArrivalModel current=itemlist.get(i);

        holder.txtTopBrands.setText(current.getCategoryName());

        Picasso.with(activity)
                .load(activity.getResources().getString(R.string.product_image_url)+current.getImage())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(holder.imgTopBrands);

        holder.imgTopBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ProdDetailsActivity.class);
                intent.putExtra("id",current.getId());
                intent.putExtra("name",current.getName());
                intent.putExtra("image",current.getImage());
                intent.putExtra("brand",current.getCategoryName());
                // intent.putExtra("price",current.getPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplicationContext().startActivity(intent);
            }
        });

    /*    holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ProdDetailsActivity.class);
                intent.putExtra("id",current.getId());
                intent.putExtra("name",current.getBrandName());
                intent.putExtra("image",current.getBrandImage());
                intent.putExtra("brand",current.getBrandName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplicationContext().startActivity(intent);
            }

        });*/

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView imgTopBrands,addcart,addwishlist;
        LinearLayout details;
        TextView txtTopBrands,prodPrice,brand;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgTopBrands=itemView.findViewById(R.id.imgTopBrands);
            txtTopBrands=itemView.findViewById(R.id.txtTopBrands);

            details=itemView.findViewById(R.id.details);
            prodPrice=itemView.findViewById(R.id.prodPrice);
            brand=itemView.findViewById(R.id.prodBrand);

           /* addcart=itemView.findViewById(R.id.cart);
            addwishlist=itemView.findViewById(R.id.wishlist);

            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    TopBrandModel model=itemlist.get(pos);

                    CartModel cartModel=new CartModel();
                    cartModel.setId(model.getId());
                    cartModel.setBrand(model.getBrandName());
                    cartModel.setProdImg(model.getBrandImage());
                    //   cartModel.setPrice(Float.parseFloat(model.getPrice()));
                    //cartModel.setSoldBy(model.getName());
                  //  cartModel.setProdName(model.getCategoryName());
                    cartModel.setSize("S");
                    sessionManager.addToCart(activity,cartModel);
                    addToCartListener.addToCart();
                }
            });

            addwishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    TopBrandModel cartModel=itemlist.get(pos);

                    CartModel wishModel=new CartModel();

                    wishModel.setId(cartModel.getId());
                    wishModel.setBrand(cartModel.getBrandName());
                    wishModel.setProdImg(cartModel.getBrandImage());
                    //  wishModel.setPrice(Float.parseFloat(cartModel.getPrice()));
                  *//*  wishModel.setSoldBy(cartModel.getName());
                    wishModel.setProdName(cartModel.getName());*//*
                    sessionManager.addToWishlist(activity,wishModel);

                }
            });

*/
        }
    }
}
