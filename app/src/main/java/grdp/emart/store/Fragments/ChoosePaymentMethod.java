package grdp.emart.store.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import grdp.emart.store.Config;
import grdp.emart.store.MVP.UserProfileResponse;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.PaymentIntegrationMethods.RazorPayIntegration;
import grdp.emart.store.R;
import grdp.emart.store.Retrofit.Api;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import grdp.emart.store.Activities.MainActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChoosePaymentMethod extends Fragment {

    View view;
    @BindView(grdp.emart.store.R.id.addNewAddressLayout)
    LinearLayout addNewAddressLayout;
    @BindView(grdp.emart.store.R.id.addressCheckBox)
    CheckBox addressCheckBox;
    @BindView(grdp.emart.store.R.id.addNewAddress)
    TextView addNewAddress;
    @BindView(grdp.emart.store.R.id.fillAddress)
    TextView fillAddress;
    @BindView(grdp.emart.store.R.id.paymentMethodsGroup)
    RadioGroup paymentMethodsGroup;
    @BindView(grdp.emart.store.R.id.makePayment)
    Button makePayment;
    String paymentMethod;
    @BindView(grdp.emart.store.R.id.progressBar)
    ProgressBar progressBar;
    @BindView(grdp.emart.store.R.id.choosePaymentLayout)
    LinearLayout choosePaymentLayout;
    @BindViews({grdp.emart.store.R.id.fullNameEdt, grdp.emart.store.R.id.mobEditText, grdp.emart.store.R.id.cityEditText, grdp.emart.store.R.id.areaEditText, grdp.emart.store.R.id.buildingEditText, grdp.emart.store.R.id.pincodeEditText, grdp.emart.store.R.id.stateEditText, grdp.emart.store.R.id.landmarkEditText,})
    List<EditText> editTexts;
    public static String address, mobileNo,userEmail;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layout = grdp.emart.store.R.layout.fragment_choose_payment_method;
        view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
        MainActivity.title.setText("Choose Payment Method");
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.cartCount.setVisibility(View.GONE);
        getUserProfileData();
        addressCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    addNewAddressLayout.setVisibility(View.GONE);
                    addNewAddress.setText("Add New Address");

                }
            }
        });
        choosePaymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cartCount.setVisibility(View.VISIBLE);
    }

    @OnClick({grdp.emart.store.R.id.addNewAddress, grdp.emart.store.R.id.makePayment, grdp.emart.store.R.id.fillAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.addNewAddress:
                addNewAddressLayout.setVisibility(View.VISIBLE);
                addressCheckBox.setChecked(false);
                addNewAddress.setText("Use This Address");
                break;
            case grdp.emart.store.R.id.makePayment:
                if (!addressCheckBox.isChecked()) {
                    if (addNewAddressLayout.getVisibility() == View.VISIBLE) {
                        if (validate(editTexts.get(0))
                                && validate(editTexts.get(1))
                                && validate(editTexts.get(2))
                                && validate(editTexts.get(3))
                                && validate(editTexts.get(4))
                                && validate(editTexts.get(5))
                                && validate(editTexts.get(6))) {
                            String s = "";
                            if (editTexts.get(6).getText().toString().trim().length() > 0) {
                                s = ", " + editTexts.get(6).getText().toString().trim();
                            }
                            address = editTexts.get(0).getText().toString().trim()
                                    + ", "
                                    + editTexts.get(4).getText().toString().trim()
                                    + s
                                    + ", " + editTexts.get(3).getText().toString().trim()
                                    + ", " + editTexts.get(2).getText().toString().trim()
                                    + ", " + editTexts.get(6).getText().toString().trim()
                                    + ", " + editTexts.get(5).getText().toString().trim()
                                    + "\n" + editTexts.get(1).getText().toString().trim();
                            mobileNo = editTexts.get(1).getText().toString().trim();
                            moveNext();
                        }
                    } else {
                        Config.showCustomAlertDialog(getActivity(),
                                "Please choose your saved address or add new to make payment",
                                "",
                                SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    moveNext();
                }

                break;
            case grdp.emart.store.R.id.fillAddress:
                ((MainActivity) getActivity()).loadFragment(new MyProfile(), true);
                break;
        }

    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void moveNext() {
        switch (paymentMethodsGroup.getCheckedRadioButtonId()) {

            case grdp.emart.store.R.id.razorPay:
                paymentMethod = "razorPay";
                intent = new Intent(getActivity(), RazorPayIntegration.class);
                startActivity(intent);
                break;
            case grdp.emart.store.R.id.cod:
                paymentMethod = "cod";
                Config.addOrder(getActivity(),
                        "COD",
                        "COD");
                break;

            default:
                paymentMethod = "";
                Config.showCustomAlertDialog(getActivity(),
                        "Payment Method",
                        "Select your payment method to make payment",
                        SweetAlertDialog.NORMAL_TYPE);
                break;


        }

        Log.d("paymentMethod", paymentMethod);
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    public void getUserProfileData() {
        progressBar.setVisibility(View.VISIBLE);
        Api.getClient().getUserProfile(
                MainActivity.userId, new Callback<UserProfileResponse>() {
                    @Override
                    public void success(UserProfileResponse userProfileResponse, Response response) {
                        progressBar.setVisibility(View.GONE);
                        userEmail=userProfileResponse.getEmail();
                        String s = "";
                        if (!userProfileResponse.getLandmark().equalsIgnoreCase("")) {
                            s = ", " + userProfileResponse.getLandmark();
                        }
                        if (userProfileResponse.getFlat().equalsIgnoreCase("")) {
                            addressCheckBox.setChecked(false);
                            addressCheckBox.setVisibility(View.GONE);
                            fillAddress.setVisibility(View.VISIBLE);
                        } else {
                            address = userProfileResponse.getName()
                                    + ", "
                                    + userProfileResponse.getFlat()
                                    + s
                                    + ", " + userProfileResponse.getLocality()
                                    + ", " + userProfileResponse.getCity()
                                    + ", " + userProfileResponse.getState()
                                    + ", " + userProfileResponse.getPincode()
                                    + "\n" + userProfileResponse.getMobile();
                            addressCheckBox.setText(address);
                            mobileNo = userProfileResponse.getMobile();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
}
