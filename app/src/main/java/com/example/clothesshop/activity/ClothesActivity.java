package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.adapter.ItemAdapter;
import com.example.clothesshop.adapter.TopBrandsAdapter;
import com.example.clothesshop.interfaces.AddToCartListener;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.ItemModel;
import com.example.clothesshop.model.ProductBaseClass;
import com.example.clothesshop.model.ProductModel;
import com.example.clothesshop.model.SubCategoryBaseModel;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.clothesshop.utility.ConstantVariables.GET_PRODUCT;
import static com.example.clothesshop.utility.ConstantVariables.GET_SUB_CATEGORY;
import static java.security.AccessController.getContext;

public class ClothesActivity extends AppCompatActivity implements AddToCartListener {

    RecyclerView recyclerView;
    ItemAdapter adapter;
    List<ItemModel> itemlist = new ArrayList<>();
    Toolbar toolbar;
    SessionManager sessionManager;
    TextView textCartItemCount;
    Menu mMenu;
    MenuItem cartItem;

    private String baseUrl = "https://efunhub.in/Clothing/images/bottomwear_women.jpg";
    private String baseUrl2 = "https://efunhub.in/Clothing/images/forever9.jpg";
    private String baseUrl3 = "https://efunhub.in/Clothing/images/bottomwear_women.jpg";
    private String baseUrl4 = "https://efunhub.in/Clothing/images/formal_men.jpg";
    private String baseUrl5 = "https://efunhub.in/Clothing/images/dress_women.jpg";
    private String baseUrl6 = "https://efunhub.in/Clothing/images/kazo.jpg";
    private String baseUrl7 = "https://efunhub.in/Clothing/images/tops_tunics.jpg";
    private String baseUrl8 = "https://efunhub.in/Clothing/images/hotsummer.jpg";

    CheckConnectivity checkConnectivity;


    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String getProducts = "GetProduct";

    String main_id, subCategoryId;

    ProductBaseClass productBaseClass;
    List<ProductModel> productModelList = new ArrayList<>();
ImageView imgNoProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        checkConnectivity = new CheckConnectivity();
        init();
        getData();
        setupToolbar();
        getSubCategory();
        // setupRecycler();
    }

    private void getData() {

        main_id = getIntent().getStringExtra("main_id");
        subCategoryId = getIntent().getStringExtra("sub_category");

    }

    private void init() {
        recyclerView = findViewById(R.id.itemRecycler);
        imgNoProduct = findViewById(R.id.imgNoProduct);
        sessionManager = new SessionManager(this);
    }

    /*   private void setupRecycler() {
           recyclerView = findViewById(R.id.itemRecycler);
           recyclerView.setHasFixedSize(true);
           GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
           recyclerView.setLayoutManager(gridLayoutManager);


           ItemModel itemModel1 = new ItemModel("1", "Woment's Formal Trouser", "GAP", "Varsh Traders", 25, baseUrl);//
           ItemModel itemModel2 = new ItemModel("2", "Women's Fashion Top", "Forever9", "Varsh Traders", 28, baseUrl6);//
           ItemModel itemModel3 = new ItemModel("3", "Women's Casual Dress", "BIBA", "Varsh Traders", 30, baseUrl2);//
           ItemModel itemModel4 = new ItemModel("4", "Men's Formal Trouser", "Peter England", "Varsh Traders", 22, baseUrl4);//
           ItemModel itemModel5 = new ItemModel("5", "Women's Casual Maxy", "BIBA", "Varsh Traders", 32, baseUrl5);//
           ItemModel itemModel6 = new ItemModel("6", "Women's Kurti", "PLUSS", "Varsh Traders", 20, baseUrl8);//
           ItemModel itemModel7 = new ItemModel("7", "Women's Chiffon Top", "KAZO", "Varsh Traders", 21, baseUrl7);
           ItemModel itemModel8 = new ItemModel("8", "Fashion Shorts", "Forever 9", "Varsh Traders", 25, baseUrl8);


           itemlist.add(itemModel1);
           itemlist.add(itemModel2);
           itemlist.add(itemModel3);
           itemlist.add(itemModel4);
           itemlist.add(itemModel5);
           itemlist.add(itemModel6);
           itemlist.add(itemModel7);
           itemlist.add(itemModel8);

         //  adapter = new ItemAdapter(itemlist, getApplicationContext());
           recyclerView.setAdapter(adapter);
       }
   */
    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Clothes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backwhite);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getSubCategory() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        HashMap<String, String> params = new HashMap<>();
        params.put("main_category_auto_id", main_id);
        params.put("sub_category_auto_id", subCategoryId);

        mVolleyService.postDataVolleyParameters(GET_PRODUCT,
                this.getResources().getString(R.string.base_url) + getProducts, params);
    }

    private void initVolleyCallback() {

        //   cvPgBar.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case GET_PRODUCT:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {
                                Gson gson = new Gson();

                                ProductBaseClass productBaseClass = gson.fromJson(response, ProductBaseClass.class);

                                List<ProductModel> guestRoomModels = productBaseClass.getAllProducts();


                                for (ProductModel productModel : guestRoomModels) {
                                    productModelList.add(productModel);
                                }
                                //   recyclerView.setHasFixedSize(true);
                                adapter = new ItemAdapter(ClothesActivity.this, productModelList);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(ClothesActivity.this, 2, GridLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(adapter);

                            } else if (status == 0) {
                                imgNoProduct.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            //imgNoProduct.setVisibility(View.VISIBLE);
                            Log.v("cuisines exception", e.toString());
                        }
                        break;
                } //cvPgBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                imgNoProduct.setVisibility(View.VISIBLE);
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        this.mMenu = menu;
        cartItem = mMenu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        //setupBadge();

        /*Drawable icon= cartItem.getIcon();
        LayerDrawable iconLayer=new LayerDrawable(new Drawable[]{icon});
        BadgeCount badgeCount=new BadgeCount();*/
        ArrayList<CartModel> cartModels = sessionManager.getCartList();

        if (cartModels != null) {
            //badgeCount.setBadgeCount(this,iconLayer,String.valueOf(cartModels));
            setupBadge();
        }

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });
        //  return super.onCreateOptionsMenu(menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        if (id == R.id.action_wishlist) {
            startActivity(new Intent(this, WishlistActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addToCart() {
        //addToCartItem();
        setupBadge();
    }

    private void setupBadge() {

        ArrayList<CartModel> cartModels = sessionManager.getCartList();

        if (cartModels != null) {
            //badgeCount.setBadgeCount(this, iconlayer, String.valueOf(cartModels));

            if (textCartItemCount != null) {
                if (cartModels.size() == 0) {
                    if (textCartItemCount.getVisibility() != View.GONE) {
                        textCartItemCount.setVisibility(View.GONE);
                    }
                } else {

                    textCartItemCount.setText(String.valueOf(Math.min(cartModels.size(), 99)));

                    if (textCartItemCount.getVisibility() != View.VISIBLE) {
                        textCartItemCount.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        this.invalidateOptionsMenu();
        Intent background = new Intent(getApplicationContext(), BlockStatusService.class);
        startService(background);
        //Check connectivity

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(checkConnectivity, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        Intent background = new Intent(getApplicationContext(), BlockStatusService.class);
        stopService(background);
        if (checkConnectivity != null) {
            this.unregisterReceiver(checkConnectivity);
        }
    }
}
