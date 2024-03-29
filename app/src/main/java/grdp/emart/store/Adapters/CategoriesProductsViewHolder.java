package grdp.emart.store.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import grdp.emart.store.Fragments.Home;
import grdp.emart.store.Fragments.ProductsList;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;

import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.Fragments.Home;
import grdp.emart.store.Fragments.ProductsList;


/**
 * Created by AbhiAndroid
 */
public class CategoriesProductsViewHolder extends RecyclerView.ViewHolder {

    TextView catName;
    CardView cardView;
    RecyclerView productsRecyclerView;
    Button viewAll;
    LinearLayout homeCategoryProductLayout;
    RelativeLayout homeCategoryRelativeLayout;

    public CategoriesProductsViewHolder(final Context context, View itemView) {
        super(itemView);
        productsRecyclerView = (RecyclerView) itemView.findViewById(grdp.emart.store.R.id.productsRecyclerView);
        catName = (TextView) itemView.findViewById(grdp.emart.store.R.id.categoryName);
        viewAll = (Button) itemView.findViewById(grdp.emart.store.R.id.viewAll);
        homeCategoryProductLayout = (LinearLayout) itemView.findViewById(grdp.emart.store.R.id.homeCategoryProductLayout);
        homeCategoryRelativeLayout = (RelativeLayout) itemView.findViewById(grdp.emart.store.R.id.homeCategoryRelativeLayout);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Home.swipeRefreshLayout.isRefreshing()) {
                    ProductsList.categoryPosition = getAdapterPosition();
                    ((MainActivity) context).loadFragment(new ProductsList(), true);
                }
            }
        });
    }
}
