package grdp.emart.store.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import grdp.emart.store.Config;
import grdp.emart.store.MVP.SignUpResponse;
import grdp.emart.store.Retrofit.Api;
import grdp.emart.store.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForgotPassword extends AppCompatActivity {
    @BindViews({grdp.emart.store.R.id.emailId})
    List<EditText> editTexts;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_forgot_password);
        ButterKnife.bind(this);

    }

    @OnClick({grdp.emart.store.R.id.back, grdp.emart.store.R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.back:
                finish();
                break;
            case grdp.emart.store.R.id.submit:
                if (Config.validateEmail(editTexts.get(0),ForgotPassword.this)) {
                    forgotPassword();
                }
                break;
        }
    }

    private void forgotPassword() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        // sending gcm token to server
        Api.getClient().forgotPassword(editTexts.get(0).getText().toString().trim(),
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        pDialog.dismiss();
                        Log.d("signUpResponse", signUpResponse.getMessage());
                        Toast.makeText(ForgotPassword.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            Config.moveTo(ForgotPassword.this, Login.class);
                            finishAffinity();
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
