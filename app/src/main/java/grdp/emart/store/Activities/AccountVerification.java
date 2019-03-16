package grdp.emart.store.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import grdp.emart.store.Config;
import grdp.emart.store.MVP.SignUpResponse;
import grdp.emart.store.R;
import grdp.emart.store.Retrofit.Api;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountVerification extends AppCompatActivity {

    @BindView(grdp.emart.store.R.id.resendEmail)
    Button resendEmail;
    @BindView(grdp.emart.store.R.id.email)
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_account_verification);
        ButterKnife.bind(this);
    }


    @OnClick({grdp.emart.store.R.id.resendEmail, grdp.emart.store.R.id.signUp, grdp.emart.store.R.id.login, grdp.emart.store.R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case grdp.emart.store.R.id.resendEmail:
                if (Config.validateEmail(email,AccountVerification.this))
                resendEmail();
                break;
            case grdp.emart.store.R.id.signUp:
                Config.moveTo(AccountVerification.this, SignUp.class);
                break;
            case grdp.emart.store.R.id.login:
                Config.moveTo(AccountVerification.this, Login.class);
                break;
            case grdp.emart.store.R.id.back:
                finish();
                break;
        }
    }

    private void resendEmail() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(AccountVerification.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(grdp.emart.store.R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        // sending gcm token to server
        Api.getClient().resentEmail(email.getText().toString().trim(),
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        pDialog.dismiss();
                        Log.d("resendEmailResponse", signUpResponse.getSuccess() + "");
                        Toast.makeText(AccountVerification.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            finish();
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
