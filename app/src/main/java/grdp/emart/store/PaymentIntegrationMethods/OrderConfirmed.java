package grdp.emart.store.PaymentIntegrationMethods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import grdp.emart.store.Activities.MainActivity;
import grdp.emart.store.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import grdp.emart.store.Activities.MainActivity;

public class OrderConfirmed extends AppCompatActivity {

    @BindView(grdp.emart.store.R.id.continueShopping)
    Button continueShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_order_confirmed);
        ButterKnife.bind(this);
    }

    @OnClick(grdp.emart.store.R.id.continueShopping)
    public void onClick(View view) {
        Intent intent = new Intent(OrderConfirmed.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderConfirmed.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
