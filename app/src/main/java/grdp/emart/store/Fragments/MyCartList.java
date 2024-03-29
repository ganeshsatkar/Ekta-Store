package grdp.emart.store.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import grdp.emart.store.Activities.AccountVerification;
import grdp.emart.store.Adapters.CartListAdapter;
import grdp.emart.store.Config;
import grdp.emart.store.Activities.Login;
import grdp.emart.store.MVP.CartistResponse;
import grdp.emart.store.MVP.Product;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;
import grdp.emart.store.Retrofit.Api;
import grdp.emart.store.Activities.SignUp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyCartList extends Fragment {

    View view;

    @BindView(grdp.emart.store.R.id.categoryRecyclerView)
    RecyclerView productsRecyclerView;
    public static int categoryPosition = 0;
    public static List<Product> productsData = new ArrayList<>();
    public static CartistResponse cartistResponseData;
    @BindView(grdp.emart.store.R.id.proceedToPayment)
    Button proceedToPayment;
    public static Context context;
    @BindView(grdp.emart.store.R.id.emptyCartLayout)
    LinearLayout emptyCartLayout;
    @BindView(grdp.emart.store.R.id.loginLayout)
    LinearLayout loginLayout;
    @BindView(grdp.emart.store.R.id.continueShopping)
    Button continueShopping;

    @BindView(grdp.emart.store.R.id.verifyEmailLayout)
    LinearLayout verifyEmailLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_cart_list, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        MainActivity.title.setText("My Cart");
        proceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment(new ChoosePaymentMethod(), true);

            }
        });
        if (!MainActivity.userId.equalsIgnoreCase("")) {
            getCartList();
        } else {
            proceedToPayment.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @OnClick({grdp.emart.store.R.id.continueShopping, grdp.emart.store.R.id.loginNow, grdp.emart.store.R.id.txtSignUp, grdp.emart.store.R.id.verfiyNow})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.continueShopping:
                Config.moveTo(getActivity(), MainActivity.class);
                getActivity().finish();
                break;
            case grdp.emart.store.R.id.loginNow:
                Config.moveTo(getActivity(), Login.class);
                break;
            case grdp.emart.store.R.id.txtSignUp:
                Config.moveTo(getActivity(), SignUp.class);
                break;

            case grdp.emart.store.R.id.verfiyNow:
                Config.moveTo(getActivity(), AccountVerification.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.cart.setVisibility(View.VISIBLE);
    }

    public void getCartList() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Api.getClient().getCartList(MainActivity.userId, new Callback<CartistResponse>() {
            @Override
            public void success(CartistResponse cartistResponse, Response response) {

                cartistResponseData = cartistResponse;
                pDialog.dismiss();
                productsData = new ArrayList<>();
                productsData = cartistResponse.getProducts();
                if (cartistResponse.getSuccess().equalsIgnoreCase("false")) {
                    verifyEmailLayout.setVisibility(View.VISIBLE);
                    proceedToPayment.setVisibility(View.GONE);
                } else {
                    try {
                        Log.d("cartId", cartistResponse.getCartid());
                        cartistResponse.getProducts().size();
                        setProductsData();
                    } catch (Exception e) {
                        proceedToPayment.setVisibility(View.GONE);
                        emptyCartLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("errorInCartList", error.toString());

                pDialog.dismiss();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.cart.setVisibility(View.GONE);
        Config.getCartList(getActivity(), false);
    }

    private void setProductsData() {
        CartListAdapter wishListAdapter;
        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        wishListAdapter = new CartListAdapter(getActivity(), productsData);
        productsRecyclerView.setAdapter(wishListAdapter);

    }
}
