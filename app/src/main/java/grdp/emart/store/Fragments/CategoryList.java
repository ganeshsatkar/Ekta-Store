package grdp.emart.store.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import grdp.emart.store.Adapters.CategoryListAdapter;
import grdp.emart.store.Config;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;
import grdp.emart.store.Activities.SplashScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryList extends Fragment {

    View view;
    @BindView(grdp.emart.store.R.id.categoryRecyclerView)
    RecyclerView categoryRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, view);
        setCategoryData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.search.setVisibility(View.VISIBLE);
        MainActivity.title.setText("Categories");
        Config.getCartList(getActivity(),true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.search.setVisibility(View.GONE);
    }

    private void setCategoryData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity(), SplashScreen.categoryListResponseData);
        categoryRecyclerView.setAdapter(categoryListAdapter);
    }
}
