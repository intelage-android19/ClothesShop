package com.example.clothesshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothesshop.R;
import com.example.clothesshop.interfaces.RemoveFromCartListener;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.ItemModel;
import com.example.clothesshop.utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {

    List<CartModel> cartList;
    Activity activity;
    SessionManager sessionManager;
    RemoveFromCartListener removeFromCartListener;
    public CartAdapter(List<CartModel> cartList, Activity activity) {
        this.cartList = cartList;
        this.activity = activity;
        this.sessionManager=new SessionManager(activity);
        this.removeFromCartListener= (RemoveFromCartListener) activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.cart_item,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final CartModel current=cartList.get(i);
        holder.brand.setText(current.getBrand());
        holder.price.setText(activity.getResources().getString(R.string.currancy)+String.valueOf(current.getPrice()));
        holder.name.setText(current.getProdName());
        holder.size.setText(current.getSize());

        Picasso.with(activity)
                .load(current.getProdImg())
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView removeBtn,addWishlist;
        ImageView image;
        TextView name,price,brand,size;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.prodImage);
            name=itemView.findViewById(R.id.itemName);
            price=itemView.findViewById(R.id.price);
            brand=itemView.findViewById(R.id.soldBy);
            size=itemView.findViewById(R.id.size);
            addWishlist=itemView.findViewById(R.id.btnWishlist);

            removeBtn=itemView.findViewById(R.id.btnRemove);

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Clothes Shopping")
                            .setMessage("Do you want to remove item from cart")
                            .setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos=getAdapterPosition();
                                    sessionManager.removeCartItem(activity,pos);
                                    cartList.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos,cartList.size());
                                    notifyDataSetChanged();
                                    removeFromCartListener.removeItem();
                                }
                            }).create().show();

                }
            });

            addWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Clothes Shopping")
                            .setMessage("Do you want to move this item to wishlist")
                            .setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos=getAdapterPosition();
                                    CartModel cartModel=cartList.get(pos);

                                    CartModel wishModel=new CartModel();

                                    wishModel.setId(cartModel.getId());
                                    wishModel.setBrand(cartModel.getBrand());
                                    wishModel.setProdImg(cartModel.getProdImg());
                                    wishModel.setPrice(cartModel.getPrice());
                                    wishModel.setSoldBy(cartModel.getBrand());
                                    wishModel.setProdName(cartModel.getProdName());
                                    sessionManager.addToWishlist(activity,wishModel);

                                    sessionManager.removeCartItem(activity,pos);
                                    cartList.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos,cartList.size());
                                    notifyDataSetChanged();
                                    removeFromCartListener.removeItem();
                                }
                            }).create().show();

                }
            });

        }
    }

}
