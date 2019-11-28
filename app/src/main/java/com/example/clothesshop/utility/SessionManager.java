package com.example.clothesshop.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.clothesshop.activity.HomeActivity;
import com.example.clothesshop.activity.Login;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.ItemModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context mContext;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "hairSpaPref";

    //Add to cart
    private static final String ADD_TO_CART = "AddToCart";

    //Add to wishlist
    private static final String ADD_TO_WISHLIST="AddToWishlist";


    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //FCM Token
    public static final String KEY_FCM_TOKEN = "token";

    // ID (make variable public to access from outside)
    public static final String KEY_ID = "agid";

    public static final String KEY_NAME = "name";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_CONTACT = "contact";
    public static final String KEY_OPERATOR_ID = "operator";
    public static final String KEY_AGENT_ID = "agent";

    //Referrer ID
    public static final String REFERRER_ID = "referrer_id";


    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //    public float getSubtotalCount(Context context){
//        float subtotal=0;
//        ArrayList<ItemModel> cartList=getCartList();
//
//        if (cartList!=null){
//            for (int i=0;i<cartList.size();i++){
//                ItemModel cartItem=cartList.get(i);
//                if (cartItem.isPack()){
//                    subtotal=subtotal+Float.valueOf(cartItem.getServiceName());
//                }
//                else {
//                    subtotal +=cartItem.getServicePrice();
//                }
//            }
//        }
//        return subtotal;
//    }



    private void saveToCart(Context context,List<CartModel> cartModelList){
        Gson gson=new Gson();
        String jsonCart=gson.toJson(cartModelList);
        editor.putString(ADD_TO_CART,jsonCart);
        editor.commit();
        Toast.makeText(context,"Added in cart",Toast.LENGTH_LONG).show();
    }

    public ArrayList<CartModel> getCartList(){
        List<CartModel> cartList=new ArrayList<>();
        if (pref.contains(ADD_TO_CART)){
            String jsonCartList=pref.getString(ADD_TO_CART,null);
            Gson gson=new Gson();
            CartModel[] cartModels=gson.fromJson(jsonCartList,CartModel[].class);

            cartList= Arrays.asList(cartModels);
            cartList=new ArrayList<CartModel>(cartList);
        }
        else
            return null;
        return (ArrayList<CartModel>) cartList;
    }

    public void addToCart(Context context,CartModel cartModelList){
        boolean isAdded=false;
        ArrayList<CartModel> cartModelArrayList=getCartList();
        if (cartModelArrayList==null)
            cartModelArrayList=new ArrayList<CartModel>();

        for (int i=0;i<cartModelArrayList.size();i++){
            CartModel cartModel=cartModelArrayList.get(i);
            if (cartModel.getId()!=null && cartModel.getId().equals(cartModelList.getId())){
                Toast.makeText(context,"Already added in cart",Toast.LENGTH_LONG).show();
                isAdded=true;
            }
        }

        if (!isAdded){
            cartModelArrayList.add(cartModelList);
            saveToCart(context,cartModelArrayList);
        }
    }

    public void removeCartItem(Context context,int position){
        ArrayList<CartModel> cartList=getCartList();
        if (cartList!=null){
            cartList.remove(position);
            saveToCart(context,cartList);
        }

    }


    //wishlist

    public ArrayList<CartModel> getWishlist(){
        List<CartModel> wishList=new ArrayList<>();
        if (pref.contains(ADD_TO_WISHLIST)){
            String jsonWishlist=pref.getString(ADD_TO_WISHLIST,null);
            Gson gson=new Gson();
            CartModel[] wishModel=gson.fromJson(jsonWishlist,CartModel[].class);

            wishList=Arrays.asList(wishModel);
            wishList=new ArrayList<CartModel>(wishList);
        }
        else
            return null;

        return (ArrayList<CartModel>) wishList;
    }

    private void saveToWishlist(Context context,List<CartModel> wishList){
        Gson gson=new Gson();
        String jsonWishlist=gson.toJson(wishList);
        editor.putString(ADD_TO_WISHLIST,jsonWishlist);
        editor.commit();
       // Toast.makeText(context,"Added in Wishlist",Toast.LENGTH_LONG).show();
    }

    public void addToWishlist(Context context,CartModel wishList) {
        boolean isAdded=false;
        ArrayList<CartModel> wishModelArrayList=getWishlist();
        if (wishModelArrayList==null)
            wishModelArrayList=new ArrayList<CartModel>();

        for (int i=0;i<wishModelArrayList.size();i++){
            CartModel wishModel=wishModelArrayList.get(i);
            if (wishModel.getId()!=null && wishModel.getId().equals(wishList.getId())){
                Toast.makeText(context,"Already added in wishlist",Toast.LENGTH_LONG).show();
                isAdded=true;
            }
        }

        if (!isAdded){
            wishModelArrayList.add(wishList);
            saveToWishlist(context,wishModelArrayList);
            Toast.makeText(context,"Added in wishlist",Toast.LENGTH_LONG).show();
        }
    }

    public void removeWishlistItem(Context context,int position){
        ArrayList<CartModel> wishList=getWishlist();
        if (wishList!=null){
            wishList.remove(position);
            saveToWishlist(context,wishList);
            Toast.makeText(context,"Removed From wishlist",Toast.LENGTH_LONG).show();

        }

    }


    public void createLoginSession(String id, String name, String contact ,String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing ID in pref
        editor.putString(KEY_ID, id);
        editor.putString(KEY_CONTACT, contact);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
    /*    editor.putString(KEY_OPERATOR_ID, operatorId);
        editor.putString(KEY_AGENT_ID, agentid);
*/
        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();

        // user info
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

    /*    user.put(KEY_OPERATOR_ID, pref.getString(KEY_OPERATOR_ID, null));

        user.put(KEY_AGENT_ID, pref.getString(KEY_AGENT_ID, null));
*/

        return user;
    }
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public boolean checkLogin() {
        // Check login status
        if (this.isLoggedIn()) {

            // user is logged in redirect him to Main Activity
            Intent i = new Intent(mContext, HomeActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);

        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }

        return false;
    }

    public void setfeaturename(String[] filterlis) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterlis.length; i++) {
            sb.append(filterlis[i]).append(",");
        }

        editor.putString("data", sb.toString());

        editor.commit();


    }

    public String getData() {


        return pref.getString("data", "LOGIN");


    }
    public void setAppName(String appName) {
        editor.putString("appName", appName);
        editor.commit();

    }

    public String getNAME() {


        return pref.getString("appName", "name");


    }

    public void setAppLogo(String appLogo) {
        editor.putString("appLogo", appLogo);
        editor.commit();
    }

    public String getLOGO() {

        return pref.getString("appLogo", "logo");


    }
    public void removeLogin() {

        editor.putBoolean(IS_LOGIN, false);

        editor.remove(KEY_ID);

        editor.commit();


    }

    public void logoutUser() {
        removeLogin();
        // Clearing all data from Shared Preferences
        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);


    }




}
