package grdp.emart.store.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import grdp.emart.store.Common;
import grdp.emart.store.Config;
import grdp.emart.store.MVP.SignUpResponse;
import grdp.emart.store.Retrofit.Api;
import grdp.emart.store.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login extends AppCompatActivity {


    @BindViews({grdp.emart.store.R.id.email, grdp.emart.store.R.id.password})
    List<EditText> editTexts;
    @BindView(grdp.emart.store.R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;
    @BindView(grdp.emart.store.R.id.appIcon)
    ImageView appIcon;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_login);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

            }
        });

    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @OnClick({grdp.emart.store.R.id.txtSignUp, grdp.emart.store.R.id.txtForgotPassword, grdp.emart.store.R.id.skipLoginLayout, grdp.emart.store.R.id.signIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.txtSignUp:
                Config.moveTo(Login.this, SignUp.class);
                break;
            case grdp.emart.store.R.id.txtForgotPassword:
                Config.moveTo(Login.this, ForgotPassword.class);
                break;
            case grdp.emart.store.R.id.skipLoginLayout:
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("from", "skip");
                startActivity(intent);
                finishAffinity();
                break;

            case grdp.emart.store.R.id.signIn:
                if (Config.validateEmail(editTexts.get(0),Login.this) && validatePassword(editTexts.get(1))) {
                    login();
                }
                break;
        }
    }

    private boolean validatePassword(EditText editText) {
        if (editText.getText().toString().trim().length() > 5) {
            return true;
        } else if (editText.getText().toString().trim().length() > 0) {
            editText.setError("Password must be of 6 characters");
            editText.requestFocus();
            return false;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void login() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        // sending gcm token to server
        Api.getClient().login(editTexts.get(0).getText().toString().trim(),
                editTexts.get(1).getText().toString().trim(),
                "email",
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        pDialog.dismiss();
                        Log.d("signUpResponse", signUpResponse.getUserid() + "");
                        Toast.makeText(Login.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            Common.saveUserData(Login.this, "email", editTexts.get(1).getText().toString());
                            Common.saveUserData(Login.this, "userId", signUpResponse.getUserid() + "");
                            Config.moveTo(Login.this, MainActivity.class);
                            finishAffinity();
                        } else if (signUpResponse.getSuccess().equalsIgnoreCase("notactive")) {
                            Config.moveTo(Login.this, AccountVerification.class);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pDialog.dismiss();

                        Log.e("error", error.toString());
                    }
                });
    }
}
