package grdp.emart.store.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import grdp.emart.store.MVP.Product;
import grdp.emart.store.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import grdp.emart.store.MVP.Product;

public class CartListViewHolder extends RecyclerView.ViewHolder {

    ImageView image1;
    ImageView delete;
    TextView productName1, price1, actualPrice1, discountPercentage1, quantity, size, color, txtGurantee;
    CardView cardView1;
    @BindView(grdp.emart.store.R.id.totalAmount)
    LinearLayout totalAmount;
    @BindViews({grdp.emart.store.R.id.txtPrice, grdp.emart.store.R.id.price, grdp.emart.store.R.id.delivery,  grdp.emart.store.R.id.tax,  grdp.emart.store.R.id.amountPayable,  grdp.emart.store.R.id.txtTax})
    List<TextView> textViews;

    public CartListViewHolder(final Context context, View itemView, List<Product> productList) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        image1 = (ImageView) itemView.findViewById(grdp.emart.store.R.id.productImage1);
        delete = (ImageView) itemView.findViewById(grdp.emart.store.R.id.delete);
        productName1 = (TextView) itemView.findViewById(grdp.emart.store.R.id.productName1);
        size = (TextView) itemView.findViewById(grdp.emart.store.R.id.size);
        color = (TextView) itemView.findViewById(grdp.emart.store.R.id.color);
        price1 = (TextView) itemView.findViewById(grdp.emart.store.R.id.price1);
        quantity = (TextView) itemView.findViewById(grdp.emart.store.R.id.quantity);
        txtGurantee = (TextView) itemView.findViewById(grdp.emart.store.R.id.txtGurantee);
        actualPrice1 = (TextView) itemView.findViewById(grdp.emart.store.R.id.actualPrice1);
        discountPercentage1 = (TextView) itemView.findViewById(grdp.emart.store.R.id.discountPercentage1);
        cardView1 = (CardView) itemView.findViewById(grdp.emart.store.R.id.cardView1);


    }
}
