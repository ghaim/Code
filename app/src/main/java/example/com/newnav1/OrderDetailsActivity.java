package example.com.newnav1;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.Order_;
// هنا يتم وضع الطلبات الجديدة التي نكتبها والتي تظهر عند المندووب


public class OrderDetailsActivity extends AppCompatActivity {


    AppPrefes app;

    public OrderDetailsActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        AppCompatButton deliveryman = (AppCompatButton) findViewById(R.id.delivery_man);
        AppCompatButton rate = (AppCompatButton) findViewById(R.id.rate);

        final Order_ order = (Order_) getIntent().getExtras().getSerializable("order");

        TextView Paymenttype = (TextView) findViewById(R.id.Payment_type);
        TextView Requestdate = (TextView) findViewById(R.id.Request_date);
        TextView sellertime = (TextView) findViewById(R.id.seller_time);
        TextView deliveryprice = (TextView) findViewById(R.id.delivery_price);
        TextView productName = (TextView) findViewById(R.id.productName);
        TextView Sellerni = (TextView) findViewById(R.id.Seller_ni);
        TextView Customeni = (TextView) findViewById(R.id.Custome_ni);
        TextView Customerphoneno = (TextView) findViewById(R.id.Customer_phone_no);
        TextView Customername = (TextView) findViewById(R.id.Customer_name);

        if (order != null) {
            Paymenttype.append("  : " + order.getPaymentType());
            sellertime.append("  : " + order.getPickingTime());
            Requestdate.append("  : " + order.getDeliveringTime());
            deliveryprice.append("  : " + order.getDeliveryPrice());
            productName.append("  : " + order.getCatogeryId());
            Sellerni.append("  :  " + order.getSellerNeighborhood());
            Customeni.append("  :  " + order.getCustomerNeighborhood());
            Customerphoneno.append("  :  " + order.getCustomerPhoneNo());
            Customername.append("  :  " + order.getName());
            if (order.getDeliveryManId().equals("")) {
                deliveryman.setVisibility(View.GONE);
                deliveryman.setVisibility(View.GONE);
            }
            deliveryman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OrderDetailsActivity.this, DeliveryManActivity.class).putExtra("id", order.getDeliveryManId()));
                }
            });

            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OrderDetailsActivity.this, Rate.class).putExtra("id", order.getDeliveryManId()));
                }
            });


        }


    }


}

