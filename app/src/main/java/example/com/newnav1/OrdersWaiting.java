package example.com.newnav1;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.newnav1.adapter.ReportAdapter;
import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.Category;
import example.com.newnav1.modal.Order;
import example.com.newnav1.modal.Order_;
import example.com.newnav1.modal.User;
import example.com.newnav1.modal.listitme;
import example.com.newnav1.views.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

                        startActivity(new Intent(OrdersWaiting.this, OrderDetailsActivity.class).putExtra("order", ordersList.get(position)));

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

        Call<Order> orders = service.getAll(app.getData("id"));

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
        @GET("watting_order.php")
        Call<Order> getAll(@Query("id") String id);
    }

}
