package grdp.emart.store.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import grdp.emart.store.Activities.AccountVerification;
import grdp.emart.store.Common;
import grdp.emart.store.Config;
import grdp.emart.store.Activities.Login;
import grdp.emart.store.MVP.SignUpResponse;
import grdp.emart.store.MVP.UserProfileResponse;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;
import grdp.emart.store.Retrofit.Api;
import grdp.emart.store.Activities.SignUp;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.Activities.SignUp;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyProfile extends Fragment {

    View view;
    @BindViews({grdp.emart.store.R.id.fullNameEdt, grdp.emart.store.R.id.mobEditText, grdp.emart.store.R.id.cityEditText, grdp.emart.store.R.id.areaEditText, grdp.emart.store.R.id.buildingEditText, grdp.emart.store.R.id.pincodeEditText, grdp.emart.store.R.id.stateEditText, grdp.emart.store.R.id.landmarkEditText,})
    List<EditText> editTexts;
    UserProfileResponse userProfileResponseData;
    @BindView(grdp.emart.store.R.id.submitBtn)
    Button submitBtn;
    @BindViews({grdp.emart.store.R.id.male, grdp.emart.store.R.id.female})
    List<CircleImageView> circleImageViews;
    String gender = "";
    @BindView(grdp.emart.store.R.id.profileLayout)
    LinearLayout profileLayout;
    @BindView(grdp.emart.store.R.id.loginLayout)
    LinearLayout loginLayout;
    @BindView(grdp.emart.store.R.id.logout)
    Button logout;

    @BindView(grdp.emart.store.R.id.verifyEmailLayout)
    LinearLayout verifyEmailLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(grdp.emart.store.R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, view);
        MainActivity.title.setText("My Profile");
        if (!MainActivity.userId.equalsIgnoreCase("")) {
            getUserProfileData();
        } else {
            profileLayout.setVisibility(View.INVISIBLE);
            loginLayout.setVisibility(View.VISIBLE);
        }
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

            }
        });
        return view;
    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void setUserProfileData() {
//        logout.setText("Logout ( "+userProfileResponseData.get);
        editTexts.get(0).setText(userProfileResponseData.getName());
        editTexts.get(1).setText(userProfileResponseData.getMobile());
        editTexts.get(2).setText(userProfileResponseData.getCity());
        editTexts.get(3).setText(userProfileResponseData.getLocality());
        editTexts.get(4).setText(userProfileResponseData.getFlat());
        editTexts.get(5).setText(userProfileResponseData.getPincode());
        editTexts.get(6).setText(userProfileResponseData.getState());
        editTexts.get(7).setText(userProfileResponseData.getLandmark());
        try {
            if (userProfileResponseData.getGender().equalsIgnoreCase("Female")) {
                circleImageViews.get(0).setImageResource(grdp.emart.store.R.drawable.male_unselect);
                circleImageViews.get(1).setImageResource(grdp.emart.store.R.drawable.female_select);
                gender = "female";
            } else if (userProfileResponseData.getGender().equalsIgnoreCase("male")) {

                circleImageViews.get(0).setImageResource(grdp.emart.store.R.drawable.male_select);
                circleImageViews.get(1).setImageResource(grdp.emart.store.R.drawable.female_unselect);
                gender = "male";
            }
        } catch (Exception e) {

        }
    }

    @OnClick({grdp.emart.store.R.id.male, grdp.emart.store.R.id.female, grdp.emart.store.R.id.submitBtn, grdp.emart.store.R.id.logout, grdp.emart.store.R.id.loginNow, grdp.emart.store.R.id.txtSignUp, grdp.emart.store.R.id.verfiyNow})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.male:
                circleImageViews.get(0).setImageResource(grdp.emart.store.R.drawable.male_select);
                circleImageViews.get(1).setImageResource(grdp.emart.store.R.drawable.female_unselect);
                gender = "male";
                break;
            case grdp.emart.store.R.id.female:
                circleImageViews.get(0).setImageResource(grdp.emart.store.R.drawable.male_unselect);
                circleImageViews.get(1).setImageResource(grdp.emart.store.R.drawable.female_select);
                gender = "female";
                break;
            case grdp.emart.store.R.id.submitBtn:
                if (gender.equalsIgnoreCase("")) {
                    Config.showCustomAlertDialog(getActivity(), "Please choose your gender to update your profile", "",
                            SweetAlertDialog.ERROR_TYPE);
                } else if (validate(editTexts.get(0))
                        && validate(editTexts.get(1))
                        && validate(editTexts.get(2))
                        && validate(editTexts.get(3))
                        && validate(editTexts.get(4))
                        && validate(editTexts.get(5))
                        && validate(editTexts.get(6)))
                    updateProfile();
                break;
            case grdp.emart.store.R.id.logout:
                logout();
                break;
            case grdp.emart.store.R.id.loginNow:
                Config.moveTo(getActivity(), Login.class);
                break;
            case grdp.emart.store.R.id.txtSignUp:
                Config.moveTo(getActivity(), SignUp.class);
                break;

            case grdp.emart.store.R.id.verfiyNow:
                Config.moveTo(getActivity(), AccountVerification.class);
                break;
        }
    }

    private void logout() {

        final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitleText("Are you sure you want to logout?");
        alertDialog.setCancelText("Cancel");
        alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismissWithAnimation();
            }
        });
        alertDialog.show();
        Button btn = (Button) alertDialog.findViewById(grdp.emart.store.R.id.confirm_button);
        btn.setBackground(getResources().getDrawable(grdp.emart.store.R.drawable.custom_dialog_button));
        btn.setText("Logout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.saveUserData(getActivity(), "email", "");
                Common.saveUserData(getActivity(), "userId", "");
                Config.moveTo(getActivity(), Login.class);
                getActivity().finishAffinity();

            }
        });
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Config.getCartList(getActivity(), true);
    }


    public void getUserProfileData() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Api.getClient().getUserProfile(
                MainActivity.userId, new Callback<UserProfileResponse>() {
                    @Override
                    public void success(UserProfileResponse userProfileResponse, Response response) {
                        userProfileResponseData = userProfileResponse;
                        pDialog.dismiss();
                        if (userProfileResponse.getSuccess().equalsIgnoreCase("false")) {
                            profileLayout.setVisibility(View.INVISIBLE);
                            verifyEmailLayout.setVisibility(View.VISIBLE);
                        } else
                            setUserProfileData();


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pDialog.dismiss();

                    }
                });
    }

    public void updateProfile() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Api.getClient().updateProfile(
                MainActivity.userId,
                editTexts.get(0).getText().toString().trim(),
                editTexts.get(2).getText().toString().trim(),
                editTexts.get(6).getText().toString().trim(),
                editTexts.get(5).getText().toString().trim(),
                editTexts.get(3).getText().toString().trim(),
                editTexts.get(4).getText().toString().trim(),
                gender,
                editTexts.get(1).getText().toString().trim(),
                editTexts.get(7).getText().toString().trim(),
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        pDialog.dismiss();
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            Config.showCustomAlertDialog(getActivity(),
                                    "Profile Status",
                                    "Profile updated",
                                    SweetAlertDialog.SUCCESS_TYPE);
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pDialog.dismiss();

                    }
                });
    }

}
