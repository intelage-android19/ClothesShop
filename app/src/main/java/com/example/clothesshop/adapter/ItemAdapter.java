package com.example.clothesshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.ItemModel;
import com.example.clothesshop.model.ProductModel;
import com.example.clothesshop.utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    List<ProductModel> itemlist;
    Activity activity;
    AddToCartListener addToCartListener;
    SessionManager sessionManager;

    public ItemAdapter(Activity activity, List<ProductModel> productModelList) {

        this.itemlist = productModelList;
        this.activity = activity;
        this.addToCartListener= (AddToCartListener) activity;
        sessionManager=new SessionManager(activity);

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.item_view,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final ProductModel current=itemlist.get(i);

        holder.prodName.setText(current.getCategoryName());
        holder.prodPrice.setText(activity.getResources().getString(R.string.currancy) + " "+(current.getMrpprice()));
        holder.brand.setText(current.getName());

        Picasso.with(activity)
                .load(activity.getResources().getString(R.string.product_image_url)+current.getImage())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(holder.prodimg);

        holder.prodimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ProdDetailsActivity.class);
                intent.putExtra("id",current.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplicationContext().startActivity(intent);
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ProdDetailsActivity.class);
                intent.putExtra("id",current.getId());
                intent.putExtra("name",current.getName());
                intent.putExtra("image",current.getImage());
                intent.putExtra("brand",current.getName());
                intent.putExtra("price",current.getPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplicationContext().startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView prodimg,addcart,addwishlist;
        LinearLayout details;
        TextView prodName,prodPrice,brand;

        public Holder(@NonNull View itemView) {
            super(itemView);
            prodimg=itemView.findViewById(R.id.prodImg);
            details=itemView.findViewById(R.id.details);
            prodName=itemView.findViewById(R.id.prodName);
            prodPrice=itemView.findViewById(R.id.prodPrice);
            brand=itemView.findViewById(R.id.prodBrand);

            addcart=itemView.findViewById(R.id.cart);
            addwishlist=itemView.findViewById(R.id.wishlist);

            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    ProductModel model=itemlist.get(pos);

                    CartModel cartModel=new CartModel();
                    cartModel.setId(model.getId());
                    cartModel.setBrand(model.getName());
                    cartModel.setProdImg(model.getImage());
                 //   cartModel.setPrice(Float.parseFloat(model.getPrice()));
                    cartModel.setSoldBy(model.getName());
                    cartModel.setProdName(model.getCategoryName());
                    cartModel.setSize("S");
                    sessionManager.addToCart(activity,cartModel);
                    addToCartListener.addToCart();
                }
            });

            addwishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    ProductModel cartModel=itemlist.get(pos);

                    CartModel wishModel=new CartModel();

                    wishModel.setId(cartModel.getId());
                    wishModel.setBrand(cartModel.getCategoryName());
                    wishModel.setProdImg(cartModel.getImage());
                  //  wishModel.setPrice(Float.parseFloat(cartModel.getPrice()));
                    wishModel.setSoldBy(cartModel.getName());
                    wishModel.setProdName(cartModel.getName());
                    sessionManager.addToWishlist(activity,wishModel);

                }
            });


        }
    }
}
