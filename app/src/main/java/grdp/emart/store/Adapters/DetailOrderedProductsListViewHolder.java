package grdp.emart.store.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import grdp.emart.store.MVP.Product;
import grdp.emart.store.R;

import java.util.List;

import butterknife.ButterKnife;
import grdp.emart.store.MVP.Product;

public class DetailOrderedProductsListViewHolder extends RecyclerView.ViewHolder {

    ImageView image1;
    TextView productName1,size,color,qty,price;

    public DetailOrderedProductsListViewHolder(final Context context, View itemView, List<Product> productList) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        image1 = (ImageView) itemView.findViewById(grdp.emart.store.R.id.productImage1);
        productName1 = (TextView) itemView.findViewById(grdp.emart.store.R.id.productName1);
        size = (TextView) itemView.findViewById(grdp.emart.store.R.id.size);
        color = (TextView) itemView.findViewById(grdp.emart.store.R.id.color);
        qty = (TextView) itemView.findViewById(grdp.emart.store.R.id.quantity);
        price = (TextView) itemView.findViewById(grdp.emart.store.R.id.price);



    }
}
