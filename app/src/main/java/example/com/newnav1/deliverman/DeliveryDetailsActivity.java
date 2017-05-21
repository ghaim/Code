package example.com.newnav1.deliverman;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import example.com.newnav1.DeliveryManActivity;
import example.com.newnav1.R;
import example.com.newnav1.Rate;
import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.Order;
import example.com.newnav1.modal.Order_;
import example.com.newnav1.modal.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
// هنا يتم وضع الطلبات الجديدة التي نكتبها والتي تظهر عند المندووب


public class DeliveryDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AppPrefes app;
    private AoiInterFace service;

    public DeliveryDetailsActivity() {
        // Required empty public constructor
    }

    Order_ order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorder_details);
        app = new AppPrefes(this, "app");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        order = (Order_) getIntent().getExtras().getSerializable("order");

        TextView Paymenttype = (TextView) findViewById(R.id.Payment_type);
        TextView Requestdate = (TextView) findViewById(R.id.Request_date);
        TextView sellertime = (TextView) findViewById(R.id.seller_time);
        TextView deliveryprice = (TextView) findViewById(R.id.delivery_price);
        TextView productName = (TextView) findViewById(R.id.productName);
        TextView Sellerni = (TextView) findViewById(R.id.Seller_ni);
        TextView Customeni = (TextView) findViewById(R.id.Custome_ni);
        TextView Customerphoneno = (TextView) findViewById(R.id.Customer_phone_no);
        TextView Customername = (TextView) findViewById(R.id.Customer_name);
        AppCompatButton accept = (AppCompatButton) findViewById(R.id.accept);
        AppCompatButton delivered = (AppCompatButton) findViewById(R.id.delivered);

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

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "172.246.241.129")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AoiInterFace.class);

        if (getIntent().getExtras().getString("screen").equals("waiting")) {
            accept.setVisibility(View.VISIBLE);
        }
        if (getIntent().getExtras().getString("screen").equals("delivering")) {
            delivered.setVisibility(View.VISIBLE);
        }


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Order> get = service.getAll(order.getId(), app.getData("did"));
                get.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.code() == 200) {
                            Toast.makeText(DeliveryDetailsActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        });
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Order> get = service.delivered(order.getId(), app.getData("did"));
                get.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.code() == 200) {
                            Toast.makeText(DeliveryDetailsActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(order.getSellerLocation().split(",")[0]), Double.parseDouble(order.getSellerLocation().split(",")[1]));
        mMap.addMarker(new MarkerOptions().position(sydney).title(order.getCustomerNeighborhood()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public interface AoiInterFace {
        @GET("accept_delivery.php")
        Call<Order> getAll(@Query("rid") String id, @Query("id") String rid);

        @GET("deliverydeliverd.php")
        Call<Order> delivered(@Query("rid") String id, @Query("id") String rid);
    }

}

