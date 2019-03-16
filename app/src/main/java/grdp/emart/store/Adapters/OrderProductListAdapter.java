package grdp.emart.store.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import grdp.emart.store.MVP.Product;
import grdp.emart.store.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import grdp.emart.store.MVP.Product;


/**
 * Created by AbhiAndroid
 */
public class OrderProductListAdapter extends RecyclerView.Adapter<OrderedProductsListViewHolder> {
    Context context;
    List<Product> productList;

    public OrderProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public OrderedProductsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(grdp.emart.store.R.layout.ordered_products_list_items1, null);
        OrderedProductsListViewHolder OrderedProductsListViewHolder = new OrderedProductsListViewHolder(context, view, productList);
        return OrderedProductsListViewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderedProductsListViewHolder holder, final int position) {

        holder.productName1.setText(productList.get(position).getProductName());

        try {
            Log.d("image", productList.get(position).getImages().get(0));
            Picasso.with(context)
                    .load(productList.get(position).getImages().get(0))
                    .resize(Integer.parseInt(context.getResources().getString(grdp.emart.store.R.string.targetProductImageWidth1)),Integer.parseInt(context.getResources().getString(grdp.emart.store.R.string.targetProductImageHeight)))
                    .placeholder(grdp.emart.store.R.drawable.defaultimage)
                    .into(holder.image1);
        } catch (Exception e) {
            holder.image1.setImageResource(grdp.emart.store.R.drawable.defaultimage);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
