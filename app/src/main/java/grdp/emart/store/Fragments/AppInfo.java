package grdp.emart.store.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import grdp.emart.store.Config;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppInfo extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({grdp.emart.store.R.id.termsLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.termsLayout:
                ((MainActivity) getActivity()).loadFragment(new TermsAndConditions(), true);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.title.setText("App Info");
        Config.getCartList(getActivity(), true);
    }
}
