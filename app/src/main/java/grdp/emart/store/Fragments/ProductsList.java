package grdp.emart.store.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import grdp.emart.store.Adapters.ProductListAdapter;
import grdp.emart.store.Config;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;
import grdp.emart.store.Activities.SplashScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsList extends Fragment {

    View view;

    @BindView(grdp.emart.store.R.id.categoryRecyclerView)
    RecyclerView productsRecyclerView;
    public static int categoryPosition;
    @BindView(grdp.emart.store.R.id.noProductAddedLayout)
    LinearLayout noProductAddedLayout;
    @BindView(grdp.emart.store.R.id.contShopping)
    Button contShopping;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, view);
        MainActivity.title.setText(SplashScreen.categoryListResponseData.get(categoryPosition).getCategory_name());
        setProductsData();
        contShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).removeCurrentFragmentAndMoveBack();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.search.setVisibility(View.VISIBLE);
        Config.getCartList(getActivity(), true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.search.setVisibility(View.GONE);
    }

    private void setProductsData() {
        if (SplashScreen.categoryListResponseData.get(categoryPosition).getProducts().size() > 0) {
            ProductListAdapter productListAdapter;
            GridLayoutManager gridLayoutManager;
            gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            productsRecyclerView.setLayoutManager(gridLayoutManager);
            productListAdapter = new ProductListAdapter(getActivity(), SplashScreen.categoryListResponseData.get(categoryPosition).getProducts(), categoryPosition);
            productsRecyclerView.setAdapter(productListAdapter);
        } else {
            noProductAddedLayout.setVisibility(View.VISIBLE);
        }

    }
}
