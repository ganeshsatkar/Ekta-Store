package grdp.emart.store.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import grdp.emart.store.Common;
import grdp.emart.store.Config;
import grdp.emart.store.Fragments.AppInfo;
import grdp.emart.store.Fragments.FAQ;
import grdp.emart.store.Fragments.Home;
import grdp.emart.store.Fragments.MyCartList;
import grdp.emart.store.Fragments.MyOrders;
import grdp.emart.store.Fragments.MyProfile;
import grdp.emart.store.Fragments.MyWishList;
import grdp.emart.store.Fragments.ProductDetail;
import grdp.emart.store.Fragments.SearchProducts;
import grdp.emart.store.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import grdp.emart.store.Fragments.AppInfo;
import grdp.emart.store.Fragments.FAQ;
import grdp.emart.store.Fragments.Home;
import grdp.emart.store.Fragments.MyCartList;
import grdp.emart.store.Fragments.MyOrders;
import grdp.emart.store.Fragments.MyWishList;
import grdp.emart.store.Fragments.ProductDetail;
import grdp.emart.store.Fragments.SearchProducts;


public class MainActivity extends AppCompatActivity {

    String from;
    public static LinearLayout toolbarContainer;
    public static View toolbar, searchLayout;
    @BindView(grdp.emart.store.R.id.searchView)
    SearchView searchView;
    boolean doubleBackToExitPressedOnce = false;
    public static ImageView menu, back, cart, search;
    public static DrawerLayout drawerLayout;
    public static String currency, userId;
    @BindView(grdp.emart.store.R.id.navigationView)
    NavigationView navigationView;
    public static TextView title, cartCount;
    int count;
    public static ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_main);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
        getCurrency();
        getUserId();
        Intent intent = getIntent();
        try {
            from = intent.getStringExtra("from");
            if (from.equalsIgnoreCase("signUp")) {
                Config.showCustomAlertDialog(MainActivity.this,
                        "Verification email sent successfully",
                        "Please check your inbox and confirm your email address. The email may take upto 5 minutes to reach your inbox\n\nIf you didn't receive email from us, make sure to check your spam folder.",
                        SweetAlertDialog.WARNING_TYPE);
            }
        } catch (Exception e) {
            Log.e("errorOccur", "Error");
        }
        // customized searchView
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(id);
        searchEditText.setTextSize(12);
        searchLayout = findViewById(grdp.emart.store.R.id.searchLayout);
        toolbarContainer = (LinearLayout) findViewById(grdp.emart.store.R.id.toolbar_container);
        toolbar = findViewById(grdp.emart.store.R.id.toolbar);
        loadFragment(new Home(), false);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case grdp.emart.store.R.id.myWishList:
                        loadFragment(new MyWishList(), true);

                        break;
                    case grdp.emart.store.R.id.myCart:
                        loadFragment(new MyCartList(), true);
                        break;
                    case grdp.emart.store.R.id.myOrders:
                        loadFragment(new MyOrders(), true);
                        break;
                    case grdp.emart.store.R.id.myProfile:
                        loadFragment(new MyProfile(), true);
                        break;
                   /* case grdp.emart.store.R.id.faq:
                        loadFragment(new FAQ(), true);
                        break;*/
                    case grdp.emart.store.R.id.appInfo:
                        loadFragment(new AppInfo(), true);
                        break;
                    case grdp.emart.store.R.id.share:
                        shareApp();
                        break;
                    case grdp.emart.store.R.id.rateApp:
                        // perform click on Rate Item
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        break;
                    case grdp.emart.store.R.id.email:
                        openGmail();
                        break;
                    case grdp.emart.store.R.id.call:
                        call();
                        break;
                }
                return false;
            }
        });
        displayFirebaseRegId(); // display firebase id

        if (getIntent().getBooleanExtra("isFromNotification", false)) {
            ProductDetail.productList.clear();
            ProductDetail.productList.addAll(SplashScreen.imagesList1);
            ProductDetail productDetail = new ProductDetail();
            Bundle bundle = new Bundle();
            bundle.putInt("position", 0);
            productDetail.setArguments(bundle);
            loadFragment(productDetail, true);
        }

    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getResources().getString(grdp.emart.store.R.string.contactNo)));
        startActivity(intent);
    }

    private void openGmail() {
        // perform click on Email ID
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        final PackageManager pm = getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        String className = null;
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                className = info.activityInfo.name;

                if (className != null && !className.isEmpty()) {
                    break;
                }
            }
        }
        emailIntent.setData(Uri.parse("mailto:" + getResources().getString(grdp.emart.store.R.string.emailId)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Of " + getResources().getString(grdp.emart.store.R.string.app_name));
        emailIntent.setClassName("com.google.android.gm", className);
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException ex) {
            // handle error
        }

    }

    public void shareApp() {
        // share app with your friends
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(grdp.emart.store.R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Try this " + getResources().getString(grdp.emart.store.R.string.app_name) + " App: https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
        startActivity(Intent.createChooser(shareIntent, "Share Using"));
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("FCM", "Firebase reg id: " + regId);
        if (!TextUtils.isEmpty(regId)) {
        } else
            Log.d("Firebase", "Firebase Reg Id is not received yet!");
    }

    private void getUserId() {
        if (Common.getSavedUserData(MainActivity.this, "userId").equalsIgnoreCase("")) {
            userId = "";
        } else {
            userId = Common.getSavedUserData(MainActivity.this, "userId");
            Log.d("userId", userId);
        }

    }

    private void getCurrency() {
        try {
            if (SplashScreen.allProductsData.get(0).getCurrency().equalsIgnoreCase("USD"))
                currency = "$";
            else
                currency = "₹";
        } catch (Exception e) {
        }
    }

    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(grdp.emart.store.R.id.drawer_layout);
        progressBar = (ProgressBar) findViewById(grdp.emart.store.R.id.progressBar);
        title = (TextView) findViewById(grdp.emart.store.R.id.title);
        cartCount = (TextView) findViewById(grdp.emart.store.R.id.cartCount);
        menu = (ImageView) findViewById(grdp.emart.store.R.id.menu);
        cart = (ImageView) findViewById(grdp.emart.store.R.id.cart);
        back = (ImageView) findViewById(grdp.emart.store.R.id.back);
        search = (ImageView) findViewById(grdp.emart.store.R.id.search);
    }

    @Override
    public void onBackPressed() {
        // double press to exit
        if (menu.getVisibility() == View.VISIBLE) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        } else {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public void showToolbar() {
        toolbarContainer.clearAnimation();
        toolbarContainer
                .animate()
                .translationY(0)
                .start();

    }

    public void lockUnlockDrawer(int lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
        if (lockMode == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            menu.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
            searchLayout.setVisibility(View.GONE);
            showToolbar();

        } else {
            menu.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);

        }

    }

    @OnClick({grdp.emart.store.R.id.menu, grdp.emart.store.R.id.back, grdp.emart.store.R.id.cart, grdp.emart.store.R.id.cartCount, grdp.emart.store.R.id.searchTextView, grdp.emart.store.R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.menu:
                if (!Home.swipeRefreshLayout.isRefreshing())
                    if (!MainActivity.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
                    }
                break;
            case grdp.emart.store.R.id.back:
                removeCurrentFragmentAndMoveBack();
                break;
            case grdp.emart.store.R.id.searchTextView:
            case grdp.emart.store.R.id.search:
                if (!Home.swipeRefreshLayout.isRefreshing())
                    loadFragment(new SearchProducts(), true);
                break;
            case grdp.emart.store.R.id.cart:
            case grdp.emart.store.R.id.cartCount:
                if (!Home.swipeRefreshLayout.isRefreshing())
                    loadFragment(new MyCartList(), true);

                break;

        }
    }

    public void removeCurrentFragmentAndMoveBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.remove(fragment);
        trans.commit();*/
        fragmentManager.popBackStack();
    }

    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(grdp.emart.store.R.id.frameLayout, fragment);
        if (bool) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


}
