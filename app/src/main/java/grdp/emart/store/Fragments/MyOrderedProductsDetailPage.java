package grdp.emart.store.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import grdp.emart.store.Adapters.DetailOrderProductListAdapter;
import grdp.emart.store.Adapters.WishListAdapter;
import grdp.emart.store.Config;
import grdp.emart.store.MVP.Ordere;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MyOrderedProductsDetailPage extends Fragment {

    View view;
    @BindView(grdp.emart.store.R.id.orderedProductsRecyclerView)
    RecyclerView orderedProductsRecyclerView;
    public static List<Ordere> orderes;
    @BindViews({grdp.emart.store.R.id.orderNo, grdp.emart.store.R.id.date, grdp.emart.store.R.id.totalAmount, grdp.emart.store.R.id.paymentMode, grdp.emart.store.R.id.shippingAddress, grdp.emart.store.R.id.orderStatus})
    List<TextView> textViews;
    public static int pos;
    public static String currency;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_my_ordered_products_detail, container, false);
        ButterKnife.bind(this, view);
        MainActivity.title.setText("");
        setData();
        setProductsData();

        return view;
    }

    private void setData() {
        if (orderes.get(pos).getOrdredproduct().get(0).getCurrency().equalsIgnoreCase("USD"))
            currency = "$";
        else
            currency = "₹";
        textViews.get(0).setText(orderes.get(pos).getOrderid());
        textViews.get(1).setText(orderes.get(pos).getDate());
        textViews.get(3).setText(orderes.get(pos).getPaymentmode());
        textViews.get(4).setText(orderes.get(pos).getAddress());
        textViews.get(5).setText(orderes.get(pos).getOrdredproduct().get(0).getOrderstatus());
        textViews.get(2).setText(currency + " " + orderes.get(pos).getTotal());
    }


    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Config.getCartList(getActivity(), true);
    }

    private void setProductsData() {
        WishListAdapter wishListAdapter;
        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        orderedProductsRecyclerView.setLayoutManager(gridLayoutManager);
        DetailOrderProductListAdapter myOrdersAdapter = new DetailOrderProductListAdapter(getActivity(), orderes.get(pos).getOrdredproduct());
        orderedProductsRecyclerView.setAdapter(myOrdersAdapter);

    }
}
