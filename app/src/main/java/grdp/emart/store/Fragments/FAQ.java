package grdp.emart.store.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.Config;
import grdp.emart.store.MVP.FAQResponse;
import grdp.emart.store.R;
import grdp.emart.store.Retrofit.Api;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FAQ extends Fragment {

    View view;
    @BindView(grdp.emart.store.R.id.faq)
    WebView faq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_faq, container, false);
        ButterKnife.bind(this, view);
        getFAQ();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.title.setText("");
        Config.getCartList(getActivity(), true);
    }

    public void getFAQ() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Api.getClient().getFAQ(new Callback<FAQResponse>() {
            @Override
            public void success(FAQResponse faqResponse, Response response) {
                pDialog.dismiss();
                MainActivity.title.setText(faqResponse.getTitle());
                faq.loadDataWithBaseURL(null, faqResponse.getDescription(), "text/html", "utf-8", null);

            }

            @Override
            public void failure(RetrofitError error) {
                pDialog.dismiss();

            }
        });
    }
}
