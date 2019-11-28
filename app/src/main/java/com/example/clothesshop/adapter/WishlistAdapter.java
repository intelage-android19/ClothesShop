package com.example.clothesshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clothesshop.R;
import com.example.clothesshop.interfaces.RemoveWishlist;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.Holder> {

    List<CartModel> wishList;
    Activity activity;
    SessionManager sessionManager;
    LinearLayout detailsLayout;
    ImageView closebtn,img1,img2,img3,img4,img5;
    TextView name,price;
    RemoveWishlist removeWishlist;

    public WishlistAdapter(List<CartModel> wishList, Activity activity) {
        this.wishList = wishList;
        this.activity = activity;
        this.sessionManager=new SessionManager(activity);
        this.detailsLayout=activity.findViewById(R.id.detailsLayout);
        this.img1=activity.findViewById(R.id.img1);
        this.img2=activity.findViewById(R.id.img2);
        this.img3=activity.findViewById(R.id.img3);
        this.img4=activity.findViewById(R.id.img4);
        this.img5=activity.findViewById(R.id.img5);
        this.name=activity.findViewById(R.id.prodName1);
        this.price=activity.findViewById(R.id.prodPrice1);

        this.removeWishlist= (RemoveWishlist) activity;
        this.closebtn=activity.findViewById(R.id.btnclose);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsLayout.setVisibility(View.GONE);
            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.wishlist_item,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        CartModel current=wishList.get(i);

        Picasso.with(activity)
                .load(current.getProdImg())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(holder.img);

        holder.name.setText(current.getProdName());
        holder.price.setText("$"+String.valueOf(current.getPrice()));
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        LinearLayout details;
        ImageView img;
        TextView name,price,movetocart,remove;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.prodImg);
            name=itemView.findViewById(R.id.prodName);
            price=itemView.findViewById(R.id.prodPrice);
            details=itemView.findViewById(R.id.details);
            remove=itemView.findViewById(R.id.removeBtn);
            movetocart=itemView.findViewById(R.id.addToCart);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    CartModel model=wishList.get(pos);

                    detailsLayout.setVisibility(View.VISIBLE);

                    name.setText(model.getProdName());
                    price.setText("$"+String.valueOf(model.getPrice()));

                    Picasso.with(activity)
                            .load(model.getProdImg())
                            .placeholder(R.drawable.coming)
                            .error(R.drawable.coming)
                            .into(img1);

                    Picasso.with(activity)
                            .load(model.getProdImg())
                            .placeholder(R.drawable.coming)
                            .error(R.drawable.coming)
                            .into(img2);

                    Picasso.with(activity)
                            .load(model.getProdImg())
                            .placeholder(R.drawable.coming)
                            .error(R.drawable.coming)
                            .into(img3);

                    Picasso.with(activity)
                            .load(model.getProdImg())
                            .placeholder(R.drawable.coming)
                            .error(R.drawable.coming)
                            .into(img4);

                    Picasso.with(activity)
                            .load(model.getProdImg())
                            .placeholder(R.drawable.coming)
                            .error(R.drawable.coming)
                            .into(img5);



                }
            });

            movetocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    CartModel model=wishList.get(pos);

                    CartModel cartModel=new CartModel();
                    cartModel.setId(model.getId());
                    cartModel.setBrand(model.getBrand());
                    cartModel.setProdImg(model.getProdImg());
                    cartModel.setPrice(model.getPrice());
                    cartModel.setSoldBy(model.getBrand());
                    cartModel.setProdName(model.getProdName());
                    cartModel.setSize("S");
                    sessionManager.addToCart(activity,cartModel);

                    sessionManager.removeWishlistItem(activity,pos);
                    wishList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos,wishList.size());
                    notifyDataSetChanged();

                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Clothes Shopping")
                            .setMessage("Do you want to remove item from wishlist")
                            .setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos=getAdapterPosition();
                                    sessionManager.removeWishlistItem(activity,pos);
                                    wishList.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos,wishList.size());
                                    notifyDataSetChanged();
                                    removeWishlist.removeItem();
                                }
                            }).create().show();

                }
            });


        }
    }
}
