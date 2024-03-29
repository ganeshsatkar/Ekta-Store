package grdp.emart.store.PaymentIntegrationMethods;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import grdp.emart.store.Adapters.CartListAdapter;
import grdp.emart.store.Config;
import grdp.emart.store.Fragments.ChoosePaymentMethod;
import grdp.emart.store.Fragments.MyCartList;
import grdp.emart.store.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import grdp.emart.store.Fragments.ChoosePaymentMethod;
import grdp.emart.store.Fragments.MyCartList;

public class RazorPayIntegration extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_razor_pay_integration);
        startPayment();
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(grdp.emart.store.R.string.app_name));
            options.put("description", "Payment for "+ MyCartList.cartistResponseData.getProducts().size()+" products");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            JSONObject preFill = new JSONObject();
            preFill.put("email", ChoosePaymentMethod.userEmail);
            preFill.put("contact", ChoosePaymentMethod.mobileNo);
            options.put("prefill", preFill);
            String payment = CartListAdapter.totalAmountPayable;

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Config.addOrder(RazorPayIntegration.this,
                razorpayPaymentID,
                "RazorPay Payment-Gateway");

    }

    @Override
    public void onPaymentError(int code, String response) {
        finish();
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
}
