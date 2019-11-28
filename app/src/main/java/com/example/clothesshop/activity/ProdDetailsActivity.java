package com.example.clothesshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.clothesshop.adapter.SliderImgAdapter;
import com.example.clothesshop.interfaces.AddToCartListener;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.ProductDetailsBaseClass;
import com.example.clothesshop.model.ProductDetailsGalleryModel;
import com.example.clothesshop.model.ProductDetailsModel;
import com.example.clothesshop.model.ProductDetailsSizweModel;
import com.example.clothesshop.model.ProductDetailsSpcificationModel;
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

public class ProdDetailsActivity extends AppCompatActivity implements View.OnClickListener, AddToCartListener {

    String[] size = new String[]{"XS", "S", "M", "L", "XL", "XXL"};
    LinearLayout sizelayout;
    RadioButton radiobtnSize;
    RadioGroup radioGroupSize;
    SessionManager sessionManager;
    Toolbar toolbar;
    MenuItem cartItem;
    ImageView image1;
    TextView name, soldby, price;
    Menu mMenu;
    TextView textCartItemCount;
    String id, nameString, brand, imgString, sizeString = "S";
    Float priceFloat;
    private int[] images = new int[]{R.drawable.bottoms, R.drawable.bottoms, R.drawable.bottoms, R.drawable.bottoms, R.drawable.bottoms};

    Button addtocart, wishlistbtn;

    private static ViewPager mPager;
    private static int currentPage = 0;
    CirclePageIndicator indicator;
    private static int NUM_PAGES = 0;
    ArrayList<SliderModel> productImgList = new ArrayList<>();

    CheckConnectivity checkConnectivity;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String productDetails = "ProductDetails";

    ProductDetailsBaseClass productDetailsBaseClass;
    List<ProductDetailsModel> productDetailsModelList = new ArrayList<>();
    List<ProductDetailsGalleryModel> productDetailsGalleryModelList = new ArrayList<>();
    List<ProductDetailsSizweModel> productDetailsSizweModelList = new ArrayList<>();
    List<ProductDetailsSpcificationModel> productDetailsSpcificationModelList = new ArrayList<>();

    String product_id;

    TextView txtProductName, txtProductPrice, txtProductDescription, txtCategoryName,txtNoSizes,txtProductSpecification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);

        init();
        getData();
        getProductdetails();
        setupToolbar();
        checkConnectivity = new CheckConnectivity();
        addtocart.setOnClickListener(this);
        wishlistbtn.setOnClickListener(this);
    }

    private void getData() {

        product_id = getIntent().getStringExtra("id");

    }


    private void getProductdetails() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        HashMap<String, String> params = new HashMap<>();
        params.put("product_auto_id", product_id);

        mVolleyService.postDataVolleyParameters(GET_PRODUCT_DETAILS,
                this.getResources().getString(R.string.base_url) + productDetails, params);
    }

    private void initVolleyCallback() {

        //   cvPgBar.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case GET_PRODUCT_DETAILS:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {
                                Gson gson = new Gson();

                                ProductDetailsBaseClass productDetailsBaseClass = gson.fromJson(response, ProductDetailsBaseClass.class);

                                productDetailsGalleryModelList = productDetailsBaseClass.getProductGallery();
                                productDetailsSizweModelList = productDetailsBaseClass.getProductSizes();
                                productDetailsSpcificationModelList = productDetailsBaseClass.getProductSpecifications();
                                productDetailsModelList = productDetailsBaseClass.getGetProduct();
                                setUpImageSlider();
                                setupSize();
                                setData();
                            }

                           else if (status == 0) {
                                Toast.makeText(ProdDetailsActivity.this, "No data avaiable", Toast.LENGTH_SHORT).show();
                                // isCuisine = false;
                            }
                            if (productDetailsSizweModelList.isEmpty()) {
                                txtNoSizes.setVisibility(View.VISIBLE);

                            }
                            if (productDetailsSpcificationModelList.isEmpty()) {
                                txtNoSizes.setVisibility(View.VISIBLE);

                            }
                            if (productDetailsSizweModelList.isEmpty()) {
                                txtNoSizes.setVisibility(View.VISIBLE);

                            }
                        } catch (Exception e) {
                            // pgGif.setVisibility(View.GONE);

                            Log.v("cuisines exception", e.toString());
                        }
                        break;
                } //cvPgBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                //   imgNoDataFound.setVisibility(View.VISIBLE);
                Toast.makeText(ProdDetailsActivity.this, "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }

    private void setData() {

        txtProductName.setText(productDetailsModelList.get(0).getName());
        txtProductPrice.setText(getResources().getString(R.string.currancy) + " "+(productDetailsModelList.get(0).getMrpprice()));
        txtCategoryName.setText(productDetailsModelList.get(0).getCategoryName());
        txtProductDescription.setText(productDetailsModelList.get(0).getDescription());
     //   txtProductSpecification.setText(productDetailsSpcificationModelList.get(0).getDescription());

    }


/*
    private void setData() {

        id = getIntent().getStringExtra("id");
        nameString = getIntent().getStringExtra("name");
        priceFloat = getIntent().getFloatExtra("price", 500);
        brand = getIntent().getStringExtra("brand");
        imgString = getIntent().getStringExtra("image");

        Picasso.with(this)
                .load(imgString)
                .placeholder(R.drawable.coming)
                .error(R.drawable.coming)
                .into(image1);

        name.setText(nameString);
        price.setText("$" + String.valueOf(priceFloat));
        soldby.setText(brand);

        //view pager
        for (int i = 0; i < productDetailsGalleryModelList.size(); i++) {
            SliderModel imageModel = new SliderModel();
            imageModel.setImageDrawable(imgString);
            productImgList.add(imageModel);
        }

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SliderImgAdapter(this, productImgList));

        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = productImgList.size();

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


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
        toolbar = findViewById(R.id.toolbar);
        addtocart = findViewById(R.id.btnAddTocart);
        wishlistbtn = findViewById(R.id.btnWishlist);
        sessionManager = new SessionManager(this);
        image1 = findViewById(R.id.prodImg);
        name = findViewById(R.id.prodName);
        soldby = findViewById(R.id.soldBy);
        price = findViewById(R.id.prodPrice);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);

        txtProductName = findViewById(R.id.txtProductName);
        txtProductDescription = findViewById(R.id.txtprodDetails);
        txtProductSpecification = findViewById(R.id.txtProductSpecification);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        sizelayout = findViewById(R.id.sizeView);
        txtNoSizes = findViewById(R.id.txtNoSizes);
        txtCategoryName = findViewById(R.id.txtCategoryName);
    }

    private void setUpImageSlider() {

        if (productDetailsGalleryModelList.size() == 0) {

            productDetailsGalleryModelList.add(new ProductDetailsGalleryModel("0", "0", productDetailsModelList.get(0).getImage(), "0", "0"));
        }

        mPager.setAdapter(new SliderImgAdapter(getApplicationContext(), productDetailsGalleryModelList, productDetailsGalleryModelList.get(0).getImage()));

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = productDetailsGalleryModelList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();

        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void setupSize() {

        radioGroupSize = new RadioGroup(this);
        radioGroupSize.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < productDetailsSizweModelList.size(); i++) {

            radiobtnSize = new RadioButton(this);
            radiobtnSize.setId(i);
            ProductDetailsSizweModel productDetailsSizweModel = productDetailsSizweModelList.get(i);
            radiobtnSize.setText(productDetailsSizweModel.getSize());
            radiobtnSize.setTextSize(12);
            radiobtnSize.setTextColor(getResources().getColor(R.color.colorPrimary));
            radiobtnSize.setBackgroundResource(R.drawable.radiobtn_background);
            radiobtnSize.setButtonDrawable(R.drawable.radiobuttondefault);
            radiobtnSize.setGravity(Gravity.CENTER);

            RadioGroup.LayoutParams params_soiled = new RadioGroup.LayoutParams(this, null);
            params_soiled.setMargins(10, 0, 10, 0);
            radiobtnSize.setLayoutParams(params_soiled);

            radioGroupSize.addView(radiobtnSize);

            radioGroupSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int checkedBtnId = group.getCheckedRadioButtonId();
                    RadioButton radioButton = group.findViewById(checkedBtnId);
                    sizeString = radioButton.getText().toString();
                }
            });

        }
        sizelayout.addView(radioGroupSize);
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
