package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.adapter.ItemAdapter;
import com.example.clothesshop.adapter.SliderImgAdapter;
import com.example.clothesshop.adapter.TopBrandsAdapter;
import com.example.clothesshop.adapter.TopBrandsUnderProductAdapter;
import com.example.clothesshop.interfaces.AddToCartListener;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.ProductDetailsBaseClass;
import com.example.clothesshop.model.ProductDetailsGalleryModel;
import com.example.clothesshop.model.ProductDetailsModel;
import com.example.clothesshop.model.ProductDetailsSizweModel;
import com.example.clothesshop.model.ProductDetailsSpcificationModel;
import com.example.clothesshop.model.ProductModel;
import com.example.clothesshop.model.ProductUnderTopBrandBaseModel;
import com.example.clothesshop.model.ProductUnderTopBrandModel;
import com.example.clothesshop.model.SliderModel;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.clothesshop.utility.ConstantVariables.GET_PRODUCT_DETAILS;
import static com.example.clothesshop.utility.ConstantVariables.PRODUCTUNDERTOPBRAND;

public class TopBrandsDetailsActivity extends AppCompatActivity implements View.OnClickListener, AddToCartListener {

    SessionManager sessionManager;
    Toolbar toolbar;
    MenuItem cartItem;
    Menu mMenu;
    TextView textCartItemCount;
    String id, nameString, brand, imgString, sizeString = "S";
    Float priceFloat;
    private int[] images = new int[]{R.drawable.bottoms, R.drawable.bottoms, R.drawable.bottoms, R.drawable.bottoms, R.drawable.bottoms};

    CheckConnectivity checkConnectivity;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String topBrandURL = "ProductUnderTopBrands";

    List<ProductUnderTopBrandModel> productUnderTopBrandModelList = new ArrayList<>();

    String topBrand_id;

    RecyclerView rvTopBrands;
    TopBrandsUnderProductAdapter adapter;
    ImageView imgNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_brands_product);

        init();
        getData();
        getTopBrandProduct();
        checkConnectivity = new CheckConnectivity();
    }

    private void getData() {

        topBrand_id = getIntent().getStringExtra("id");

    }

    private void getTopBrandProduct() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        HashMap<String, String> params = new HashMap<>();
        params.put("top_brand_auto_id", topBrand_id);

        mVolleyService.postDataVolleyParameters(PRODUCTUNDERTOPBRAND,
                this.getResources().getString(R.string.base_url) + topBrandURL, params);
    }

    private void initVolleyCallback() {

        //   cvPgBar.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case PRODUCTUNDERTOPBRAND:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {
                                Gson gson = new Gson();

                                ProductUnderTopBrandBaseModel productUnderTopBrandBaseModel = gson.fromJson(response, ProductUnderTopBrandBaseModel.class);

                                List<ProductUnderTopBrandModel> productUnderTopBrandModels = productUnderTopBrandBaseModel.getBrandProducts();


                                for (ProductUnderTopBrandModel productUnderTopBrandModel : productUnderTopBrandModels) {
                                    productUnderTopBrandModelList.add(productUnderTopBrandModel);
                                }
                                //   recyclerView.setHasFixedSize(true);
                                adapter = new TopBrandsUnderProductAdapter(TopBrandsDetailsActivity.this, productUnderTopBrandModelList);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(TopBrandsDetailsActivity.this, 2, GridLayoutManager.VERTICAL, false);
                                rvTopBrands.setLayoutManager(gridLayoutManager);
                                rvTopBrands.setAdapter(adapter);

                            }

                            else if (status == 0) {
                                Toast.makeText(TopBrandsDetailsActivity.this, "No data avaiable", Toast.LENGTH_SHORT).show();
                                // isCuisine = false;
                            }
                            if (productUnderTopBrandModelList.isEmpty()) {
                                imgNoData.setVisibility(View.VISIBLE);

                            }

                        } catch (Exception e) {
                            imgNoData.setVisibility(View.VISIBLE);

                            Log.v("cuisines exception", e.toString());
                        }
                        break;
                } //cvPgBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                imgNoData.setVisibility(View.VISIBLE);
                Toast.makeText(TopBrandsDetailsActivity.this, "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }



    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Brands");
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

    private void init() {
        rvTopBrands = findViewById(R.id.rvTopBrands);
        imgNoData = findViewById(R.id.imgNoData);
        sessionManager = new SessionManager(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTocart:
                addtoCart();
                break;
            case R.id.btnWishlist:
                addToWishlist();
                break;
        }
    }

    private void addtoCart() {
        //  TopServiceModel service=topService.get(getAdapterPosition());

        CartModel cartModel = new CartModel();

        cartModel.setId(id);
        cartModel.setBrand(brand);
        cartModel.setProdImg(imgString);
        cartModel.setPrice(priceFloat);
        cartModel.setSoldBy(brand);
        cartModel.setProdName(nameString);
        cartModel.setSize(sizeString);
        sessionManager.addToCart(this, cartModel);
        setupBadge();

    }

    private void addToWishlist() {
        CartModel wishModel = new CartModel();

        wishModel.setId(id);
        wishModel.setBrand(brand);
        wishModel.setProdImg(imgString);
        wishModel.setPrice(priceFloat);
        wishModel.setSoldBy(brand);
        wishModel.setProdName(nameString);
        sessionManager.addToWishlist(this, wishModel);
    }

    @Override
    public void addToCart() {
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
