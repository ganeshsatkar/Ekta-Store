package grdp.emart.store.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import grdp.emart.store.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by AbhiAndroid
 */
public class MyOrdersViewHolder extends RecyclerView.ViewHolder {

    @BindView(grdp.emart.store.R.id.orderedProductsRecyclerView)
    RecyclerView orderedProductsRecyclerView;
    @BindView(grdp.emart.store.R.id.viewOrderDetails)
    TextView viewOrderDetails;
    @BindView(grdp.emart.store.R.id.date)
    TextView date;


    public MyOrdersViewHolder(final Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
