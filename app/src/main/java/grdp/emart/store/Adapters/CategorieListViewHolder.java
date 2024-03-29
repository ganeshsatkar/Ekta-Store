package grdp.emart.store.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import grdp.emart.store.Fragments.ProductsList;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;

import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.Fragments.ProductsList;


/**
 * Created by AbhiAndroid
 */
public class CategorieListViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView catName;
    CardView cardView;

    public CategorieListViewHolder(final Context context, View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(grdp.emart.store.R.id.categoryIcon);
        catName = (TextView) itemView.findViewById(grdp.emart.store.R.id.categoryName);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductsList.categoryPosition = getAdapterPosition();
                ((MainActivity) context).loadFragment(new ProductsList(), true);
            }
        });
    }
}
