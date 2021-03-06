package example.com.newnav1.deliverman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import example.com.newnav1.OrderDetailsActivity;
import example.com.newnav1.R;
import example.com.newnav1.adapter.ReportAdapter;
import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.Order;
import example.com.newnav1.modal.Order_;
import example.com.newnav1.views.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OrdersWaiting extends AppCompatActivity {
    private List<Order_> ordersList;

    TextView back;
    private AoiInterFace service;
    AppPrefes app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_waiting);
        setTitle("قيد القبول");
        app = new AppPrefes(this, "app");
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        startActivity(new Intent(OrdersWaiting.this, DeliveryDetailsActivity.class).putExtra("order", ordersList.get(position)).putExtra("screen","waiting"));

                    }
                })
        );

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "http://ypinsksa.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AoiInterFace.class);

        Call<Order> orders = service.getAll();

        orders.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, retrofit2.Response<Order> response) {
                if (response.code() == 200) {
                    if (response.body().getCode() > 0) {
                        ordersList = response.body().getOrders();
                        ReportAdapter ordersAdapter = new ReportAdapter(ordersList);
                        rv.setAdapter(ordersAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });

    }


    public interface AoiInterFace {
        @GET("d_watting_order.php")
        Call<Order> getAll();
    }

}
