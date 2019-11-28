package com.example.clothesshop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.adapter.ExpandableListAdapter;
import com.example.clothesshop.adapter.SubChildAdapter;
import com.example.clothesshop.databinding.ActivityHomeBinding;
import com.example.clothesshop.fragments.HomeFragment;
import com.example.clothesshop.fragments.WomanFragment;
import com.example.clothesshop.interfaces.AddToCartListener;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.CartModel;
import com.example.clothesshop.model.MainCategoryBasemodel;
import com.example.clothesshop.model.MainCategoryModel;
import com.example.clothesshop.model.MenuModel;
import com.example.clothesshop.model.SubCategoryBaseModel;
import com.example.clothesshop.model.SubCategoryModel;
import com.example.clothesshop.utility.BlockStatusService;
import com.example.clothesshop.utility.CheckConnectivity;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.clothesshop.utility.ConstantVariables.CHECK_VERSION;
import static com.example.clothesshop.utility.ConstantVariables.GET_SUB_CATEGORY;
import static com.example.clothesshop.utility.ConstantVariables.MAIN_CATEGORIES;

public class HomeActivity extends AppCompatActivity
        implements View.OnTouchListener, NavigationView.OnNavigationItemSelectedListener, AddToCartListener {

    private ActivityHomeBinding mBinder;
    private ExpandableListAdapter expandableListAdapter;
    private SubChildAdapter subChildAdapter;
    private ExpandableListView expandableListView;
    private List<MenuModel> headList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> listDataChild = new HashMap<>();
    HashMap<MenuModel, List<MenuModel>> listDataSubChild = new HashMap<>();

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Fragment fragment;
    private ViewPager mViewPager;
    private TabLayout tabs;

    private SearchView searchView;

    private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.

    private float downRawX, downRawY;
    private float dX, dY;

    int _xDelta;
    int _yDelta;

    RelativeLayout rootlayout;

    Menu mMenu;
    MenuItem cartItem;
    TextView textCartItemCount;
    SessionManager sessionManager;

    CheckConnectivity checkConnectivity;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    private String CheckVersion = "GetVersion";
    final String get_categories_url = "ShowMainCategory";
    final String get_Sub_categories_url = "GetSubCategory";

    List<MainCategoryModel> mainCategoryModelList = new ArrayList<>();
    List<SubCategoryModel> subCategoryModelsList = new ArrayList<>();


    NavigationView navigationView;
    MenuItem menuItem_History, menuItem_wallet, menuItem_Referearn;
    String[] playlists;
    MenuModel menuWomen;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_home);

        checkConnectivity = new CheckConnectivity();

        init();
        setupToolbar();
        setupFloatingButton();
        getCategories();
         prepareMenuData();
         populateMenuData();
        setupViewPagerTabs(mainCategoryModelList);

        getVersion();
//setFeaturesData();
        // setup drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }




/*
 private void setFeaturesData(){

     Menu nav_Menu = navigationView.getMenu();

     if (Arrays.asList(playlists).contains(getResources().getString(R.string.wallet))) {

     } else {
         menuItem_wallet.setVisible(false);

     }
     if (Arrays.asList(playlists).contains(getResources().getString(R.string.history))) {
         menuItem_History.setVisible(true);

     } else {
         menuItem_History.setVisible(false);

     }  if (Arrays.asList(playlists).contains(getResources().getString(R.string.referAndeEarn))) {
         menuItem_Referearn.setVisible(true);

     } else {
         menuItem_Referearn.setVisible(false);

     }

 }
*/

    private void getCategories() {
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        String url = getApplicationContext().getResources().getString(R.string.base_url) + get_categories_url;

        mVolleyService.getDataVolley(MAIN_CATEGORIES, url);
    }

    private void getSubCategory(String main_id) {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, this);

        HashMap<String, String> params = new HashMap<>();
        params.put("main_category_auto_id", main_id);

        mVolleyService.postDataVolleyParameters(GET_SUB_CATEGORY,
                this.getResources().getString(R.string.base_url) + get_Sub_categories_url, params);
    }

    private void getVersion() {

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        mVolleyService.getDataVolley(CHECK_VERSION, this.getResources().getString(R.string.base_url) + CheckVersion);
    }

    private void initVolleyCallback() {

        //   cvPgBar.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case CHECK_VERSION:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            String msg = jsonObj.getString("msg");

                            if (status == 1) {

                                checkVersion(Double.valueOf(msg));
                                finish();
                            }
                        } catch (Exception e) {

                            Log.v("Customer Activity", e.toString());
                        }

                        break;
                    case MAIN_CATEGORIES:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {

                                Gson gson = new Gson();

                                MainCategoryBasemodel mainCategoryBasemodel = gson.fromJson(response, MainCategoryBasemodel.class);

                                mainCategoryModelList = mainCategoryBasemodel.getAllmaincategories();

                                setupViewPagerTabs(mainCategoryModelList);

                                List<MainCategoryModel> mainCategoryModelList = mainCategoryBasemodel.getAllmaincategories();

                                for (MainCategoryModel mainCategoryModel : mainCategoryModelList) {

                                    getSubCategory(mainCategoryModel.getId());

                                }


                            } else {
                                // isCuisine = false;
                            }
                        } catch (Exception e) {
                            // pgGif.setVisibility(View.GONE);
                            Log.v("cuisines exception", e.toString());
                        }
                        break;

                    case GET_SUB_CATEGORY:
                        try {
                            jsonObj = new JSONObject(response);

                            int status = jsonObj.getInt("status");
                            if (status == 1) {

                                Gson gson = new Gson();
                                SubCategoryBaseModel subCategoryBaseModel = gson.fromJson(response, SubCategoryBaseModel.class);
                                subCategoryModelsList = subCategoryBaseModel.getAllSubcategories();

                            } else {
                                // isCuisine = false;
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
                Toast.makeText(getApplicationContext(), "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
            }
        };
    }

    public void checkVersion(Double newVersion) {
        Double currentVersionCode = 1.0;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersionCode = Double.valueOf(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        Log.v("data_info", String.valueOf(newVersion));

        if (currentVersionCode < newVersion) {
            android.app.AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new android.app.AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new android.app.AlertDialog.Builder(this);
            }
            builder.setTitle(getResources().getString(R.string.app_name))
                    .setCancelable(false)
                    .setMessage("New version is available.")
                    .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "https://play.google.com";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    // .setIcon(R.drawable.hotellogo)
                    .show();
        }
    }


    private void setupViewPagerTabs(List<MainCategoryModel> mainCategoryModelList) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (int i = 0; i < mainCategoryModelList.size(); i++) {

            HomeFragment newFragment = new HomeFragment();

            Bundle bundle = new Bundle();
            bundle.putString("main_id", mainCategoryModelList.get(i).getId());
            newFragment.setArguments(bundle);
            adapter.addFragment(newFragment, mainCategoryModelList.get(i).getType());


          /*
            if (mainCategoryModelList.get(i).getMainCategoryId().equals("MCAT101")) {

                HomeFragment newFragment = HomeFragment.newInstance(mainCategoryModelList.get(i).getId());
                adapter.addFragment(newFragment, mainCategoryModelList.get(i).getType());


            } else if (mainCategoryModelList.get(i).getMainCategoryId().equals("MCAT102")) {

                HomeFragment womanFragment = HomeFragment.newInstance("MCAT102");

                adapter.addFragment(womanFragment, mainCategoryModelList.get(i).getType());

            } else if (mainCategoryModelList.get(i).getMainCategoryId().equals("MCAT103")) {

                HomeFragment kidFragment = HomeFragment.newInstance("MCAT103");

                adapter.addFragment(kidFragment, mainCategoryModelList.get(i).getType());

            }*/
        }

        mViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(mViewPager);


    }

    private void setupHome() {
        fragment = new WomanFragment();
        loadFragment();
    }

    private void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frgment_frame, fragment)
                .addToBackStack(String.valueOf(fm))
                .commit();
    }


    private void prepareMenuData() {

       /* for (int i = 0; i < mainCategoryModelList.size(); i++) {

            menuWomen = new MenuModel(mainCategoryModelList.get(i).getType(), R.drawable.women, true, true, true);
            headList.add(menuWomen);

            List<MenuModel> subChildModelList = new ArrayList<>();

//                getSubCategory(mainCategoryModelList.get(i).getId());

            for (int j = 0; j < subCategoryModelsList.size(); j++) {

                MenuModel subchildModel = new MenuModel(subCategoryModelsList.get(j).getName(), R.drawable.women, false, false, false);
                subChildModelList.add(subchildModel);
            }

            if (menuWomen.isChildren()) {
                listDataSubChild.put(menuWomen, subChildModelList);

            }

        }

        menuWomen = new MenuModel("Wishlist", R.drawable.women, false, false, false);
        headList.add(menuWomen);

        if (!menuWomen.isChildren()) {

            listDataChild.put(menuWomen, null);
            drawer.closeDrawer(GravityCompat.START);
        }

        expandableListAdapter = new ExpandableListAdapter(this, headList, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

    }*/


        menuWomen = new MenuModel("Women", R.drawable.women, true, true, true);
        headList.add(menuWomen);

        List<MenuModel> childModelList1 = new ArrayList<>();

        MenuModel childModel1 = new MenuModel("Indian Wear", 0, true, true, false);
        childModelList1.add(childModel1);

        List<MenuModel> subChildModelList1 = new ArrayList<>();

        MenuModel subchildmodel1 = new MenuModel("Kurtas & Kurtis", 0, false, false, false);
        subChildModelList1.add(subchildmodel1);

        MenuModel subchildmodel2 = new MenuModel("Ethnic Dresses", 0, false, false, false);
        subChildModelList1.add(subchildmodel2);

        MenuModel subchildmodel3 = new MenuModel("Leggings, Salwars, Chudidars", 0, false, false, false);
        subChildModelList1.add(subchildmodel3);

        MenuModel subchildmodel4 = new MenuModel("Sarees", 0, false, false, false);
        subChildModelList1.add(subchildmodel4);

        if (childModel1.isChildren()) {
            listDataSubChild.put(childModel1, subChildModelList1);
        }


        MenuModel childModel2 = new MenuModel("Western Wear", 0, true, true, false);
        childModelList1.add(childModel2);

        List<MenuModel> subChildModelList2 = new ArrayList<>();


        MenuModel subchildmodel21 = new MenuModel("Dresses & Jumpsuits", 0, false, false, false);
        subChildModelList2.add(subchildmodel21);


        MenuModel subchildmodel22 = new MenuModel("Tops,T-Shirts & Shirts", 0, false, false, false);
        subChildModelList2.add(subchildmodel22);

        MenuModel subchildmodel23 = new MenuModel("Jeans & Jeggings", 0, false, false, false);
        subChildModelList2.add(subchildmodel23);

        MenuModel subchildmodel24 = new MenuModel("Trousers & capris", 0, false, false, false);
        subChildModelList2.add(subchildmodel24);

        MenuModel subchildmodel25 = new MenuModel("Shorts & Skirts", 0, false, false, false);
        subChildModelList2.add(subchildmodel25);


        if (childModel1.isChildren()) {
            listDataSubChild.put(childModel2, subChildModelList2);
        }


        if (menuWomen.isChildren()) {
            listDataChild.put(menuWomen, childModelList1);
        }


        //2nd list header
        MenuModel headModel2 = new MenuModel("Men", R.drawable.men, true, true, true);
        headList.add(headModel2);

        List<MenuModel> childModelList2 = new ArrayList<>();

        MenuModel childModel3 = new MenuModel("Top Wear", 0, true, true, false);
        childModelList2.add(childModel3);

        List<MenuModel> subchildmModelList21 = new ArrayList<>();

        MenuModel subchildmodel211 = new MenuModel("T-Shirts", 0, false, false, false);
        subchildmModelList21.add(subchildmodel211);

        MenuModel subchildmodel212 = new MenuModel("Shirts", 0, false, false, false);
        subchildmModelList21.add(subchildmodel212);

        MenuModel subchildmodel213 = new MenuModel("Jackets & coats", 0, false, false, false);
        subchildmModelList21.add(subchildmodel213);


        if (childModel3.isChildren()) {
            listDataSubChild.put(childModel3, subchildmModelList21);
        }

        MenuModel childModel4 = new MenuModel("Bottom Wear", 0, true, true, false);
        childModelList2.add(childModel4);

        List<MenuModel> submodelchildList22 = new ArrayList<>();

        MenuModel subchildmodel221 = new MenuModel("Jeans", 0, false, false, false);
        submodelchildList22.add(subchildmodel221);

        MenuModel subchildmodel222 = new MenuModel("Trousers", 0, false, false, false);
        submodelchildList22.add(subchildmodel222);

        MenuModel subchildmodel223 = new MenuModel("Shorts", 0, false, false, false);
        submodelchildList22.add(subchildmodel223);

        if (childModel4.isChildren()) {
            listDataSubChild.put(childModel4, submodelchildList22);
        }
        if (headModel2.isChildren()) {
            listDataChild.put(headModel2, childModelList2);
        }

        //3rd header
        MenuModel headerModel7 = new MenuModel("Kids", R.drawable.onesie, true, true, true);
        headList.add(headerModel7);

        List<MenuModel> childModelList3 = new ArrayList<>();

        MenuModel childmodel5 = new MenuModel("Boys Clothing", 0, true, true, false);
        childModelList3.add(childmodel5);

        List<MenuModel> subchildmodellist31 = new ArrayList<>();

        MenuModel suchildmodel311 = new MenuModel("T-Shirts & shirts", 0, false, false, false);
        subchildmodellist31.add(suchildmodel311);

        MenuModel suchildmodel312 = new MenuModel("Jeans & Trousers", 0, false, false, false);
        subchildmodellist31.add(suchildmodel312);


        MenuModel suchildmodel313 = new MenuModel("Clothing Sets", 0, false, false, false);
        subchildmodellist31.add(suchildmodel313);


        MenuModel suchildmodel314 = new MenuModel("Indian Wear", 0, false, false, false);
        subchildmodellist31.add(suchildmodel314);

        if (childmodel5.isChildren()) {
            listDataSubChild.put(childmodel5, subchildmodellist31);
        }

        MenuModel childmodel6 = new MenuModel("Girls Clothing", 0, true, true, false);
        childModelList3.add(childmodel6);

        List<MenuModel> subchildmodellist32 = new ArrayList<>();

        MenuModel suchildmodel331 = new MenuModel("Dresses", 0, false, false, false);
        subchildmodellist32.add(suchildmodel331);

        MenuModel suchildmodel332 = new MenuModel("Tops & T-Shirts", 0, false, false, false);
        subchildmodellist32.add(suchildmodel332);

        MenuModel suchildmodel333 = new MenuModel("Clothing Sets", 0, false, false, false);
        subchildmodellist32.add(suchildmodel333);

        MenuModel suchildmodel334 = new MenuModel("Indian Wear", 0, false, false, false);
        subchildmodellist32.add(suchildmodel334);

        if (childmodel5.isChildren()) {
            listDataSubChild.put(childmodel6, subchildmodellist32);
        }

        if (headerModel7.isChildren()) {
            listDataChild.put(headerModel7, childModelList3);
        }

        //wallet
        MenuModel headModel5 = new MenuModel("Wallet", R.drawable.wallet, true, false, false);
        headList.add(headModel5);

        if (!headModel5.isChildren()) {
            listDataChild.put(headModel5, null);
            listDataSubChild.put(headModel5, null);
        }
        //profile
        MenuModel headModel4 = new MenuModel("Account", R.drawable.profile, true, false, false);
        headList.add(headModel4);


        //History
        MenuModel headModel51 = new MenuModel("History", R.drawable.profile, true, false, false);
        headList.add(headModel51);
        if (!menuWomen.isChildren()){

            listDataChild.put(menuWomen, null);

        }



//        //Wishlist
//        MenuModel headModel3=new MenuModel("Wishlist",R.drawable.wishlist,true,false,false);
//        headList.add(headModel3);
//
//        if (!headModel3.isChildren()){
//            childList.put(headModel3,null);
//            listDataSubChild.put(headModel3,null);
//
//        }

//        //orders
//        MenuModel headModel4=new MenuModel("Orders",R.drawable.order,true,false,false);
//        headList.add(headModel4);
//
//        if (!headModel4.isChildren()){
//            childList.put(headModel4,null);
//        }
//
//        //refer & earn
//        MenuModel headModel5=new MenuModel("Refer & Earn",R.drawable.refer,true,false,false);
//        headList.add(headModel5);
//
//        if (!headModel5.isChildren()){
//            childList.put(headModel5,null);
//        }
//

        System.out.println(headList.toString());
        System.out.println(listDataChild.toString());
        System.out.println(listDataSubChild.toString());


/*
    private void populatNavMenuData() {

        subChildAdapter = new SubChildAdapter(this, headList, listDataChild, listDataSubChild);
        subChildAdapter.notifyDataSetChanged();
        expandableListView.setAdapter(subChildAdapter);

        Log.v("headList", String.valueOf(headList));
        Log.v("listDataChild", String.valueOf(listDataChild));
        Log.v("listDataSubChild", String.valueOf(listDataSubChild));


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (headList.get(groupPosition).isGroup()) {
                    if (!headList.get(groupPosition).isChildren()) {
                        if ((headList.get(groupPosition).getMenuName()).equals("Home")) {

                            //pushFragment(HomeFragment.newInstance("1"), true);
                            drawer.closeDrawer(GravityCompat.START);

                            //expandableListView.setGroupIndicator(R.drawable.lock);
                        } else if ((headList.get(groupPosition).getMenuName()).equals("Login")) {

                            startActivity(new Intent(HomeActivity.this, Login.class));
                            //expandableListView.setGroupIndicator(R.drawable.lock);

                        } else if ((headList.get(groupPosition).getMenuName()).equals("Logout")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setTitle("Automobile");
// builder.setIcon(R.drawable.icon_exit);
                            builder.setMessage("Do you want to logout?")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            sessionManager.logoutUser();
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            //expandableListView.setGroupIndicator(R.drawable.lock);

                        } else if ((headList.get(groupPosition).getMenuName()).equals("Account")) {

                            startActivity(new Intent(HomeActivity.this, AccountActivity.class));

                        }

                       */
/* else if ((headList.get(groupPosition).getMenuName()).equals("Wishlist"))
                        {

                            pushFragment(WishlistFragment.newInstance(), true);
                            drawer.closeDrawer(GravityCompat.START);

                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            getSupportActionBar().setDisplayShowHomeEnabled(false);
                         //   tvTitle.setText("Wishlist");
                            ivWallet.setVisibility(View.GONE);

                            ivBack.setVisibility(View.VISIBLE);
                            ivBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);

                                }
                            });

                        }

                        else if ((headList.get(groupPosition).getMenuName()).equals("My Orders"))
                        {

                            pushFragment(OrdersFragment.newInstance(), true);
                            drawer.closeDrawer(GravityCompat.START);

                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            getSupportActionBar().setDisplayShowHomeEnabled(false);
                            tvTitle.setText("My Orders");
                            ivWallet.setVisibility(View.GONE);
                            ivBack.setVisibility(View.VISIBLE);
                            ivBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);

                                }
                            });

                        }

                        else if ((headList.get(groupPosition).getMenuName()).equals("Terms & Conditions"))
                        {
                            startActivity(new Intent(HomeActivity.this, TermsActivity.class));


                        }*//*

                    }
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (listDataChild.get(headList.get(groupPosition)) != null) {
                    //    else if ((childList.get(headList.get(groupPosition)).get(childPosition).getMenuName()).equals("Western Wear")){
//                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    }
//                    else if ((childList.get(headList.get(groupPosition)).get(childPosition).getMenuName()).equals("Top Wear")){
//                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    }
//                    else if ((childList.get(headList.get(groupPosition)).get(childPosition).getMenuName()).equals("Bottom Wear")){
//                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    }
                }
                return false;
            }
        });


    }
*/


    }
    private void populateMenuData () {
            //expandableListAdapter=new ExpandableListAdapter(this,headList,childList);
            subChildAdapter = new SubChildAdapter(this, headList, listDataChild, listDataSubChild);
            subChildAdapter.notifyDataSetChanged();
            expandableListView.setAdapter(subChildAdapter);

            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;

                @Override
                public void onGroupExpand(int groupPosition) {
                    if (groupPosition != previousGroup)
                        expandableListView.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            });

            // group click listener
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (headList.get(groupPosition).isGroup()) {
                        if (!headList.get(groupPosition).isChildren()) {
                            if ((headList.get(groupPosition).getMenuName()).equals("Account")) {
                                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                            }
                        }
                    }
                    return false;
                }
            });
            // Wallet click listener
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (headList.get(groupPosition).isGroup()) {
                        if (!headList.get(groupPosition).isChildren()) {
                            if ((headList.get(groupPosition).getMenuName()).equals("Wallet")) {
                                startActivity(new Intent(HomeActivity.this, WalletActivity.class));
                            }
                        }
                    }
                    return false;
                }
            });
        // Wallet click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (headList.get(groupPosition).isGroup()) {
                    if (!headList.get(groupPosition).isChildren()) {
                        if ((headList.get(groupPosition).getMenuName()).equals("History")) {
                            startActivity(new Intent(HomeActivity.this, OrderHistoryActivity.class));

                        }
                    }
                }
                return true;
            }
        });
            //child click listener
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    if (listDataChild.get(headList.get(groupPosition)) != null) {
                        //    else if ((childList.get(headList.get(groupPosition)).get(childPosition).getMenuName()).equals("Western Wear")){
//                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    }
//                    else if ((childList.get(headList.get(groupPosition)).get(childPosition).getMenuName()).equals("Top Wear")){
//                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    }
//                    else if ((childList.get(headList.get(groupPosition)).get(childPosition).getMenuName()).equals("Bottom Wear")){
//                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    }
                    }
                    return false;
                }
            });
        }

    private void setupFloatingButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //fab.setOnTouchListener(HomeActivity.this);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Garment Shop");

    }

    private void init() {
        fab = findViewById(R.id.fab);
        expandableListView = mBinder.expandableListView;
        mViewPager = findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tabs);
        rootlayout = findViewById(R.id.rootLayout);
        sessionManager = new SessionManager(this);
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Confirm Exit");
            //  builder.setIcon(R.drawable.icon_exit);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory(Intent.CATEGORY_HOME);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        this.mMenu = menu;
        this.cartItem = mMenu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void addToCart() {
        setupBadge();
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                v.setLayoutParams(layoutParams);
                break;
        }
        rootlayout.invalidate();
        return true;
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
