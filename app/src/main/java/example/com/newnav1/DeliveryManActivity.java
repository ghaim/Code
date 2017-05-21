package example.com.newnav1;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import example.com.newnav1.modal.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class DeliveryManActivity extends AppCompatActivity {

    TextView back;
    private AoiInterFace service;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandoob);
        final TextView Viewmail = (TextView) findViewById(R.id.View_mail);
        final TextView Viewmobile = (TextView) findViewById(R.id.View_mobile);
        final TextView Viewname = (TextView) findViewById(R.id.View_name);
        TextView back = (TextView) findViewById(R.id.back);

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         /*
         * Set Up Connection
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "http://ypinsksa.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AoiInterFace.class);

        Call<User> user = service.getDeliveryMan(getIntent().getExtras().getString("id"));

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    Viewmail.setText(user.getEmail());
                    Viewname.setText(user.getName() + " " + user.getLastName());
                    Viewmobile.setText(user.getPhone());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }


    public interface AoiInterFace {
        @FormUrlEncoded
        @POST("deliveryman.php")
        Call<User> getDeliveryMan(@Field("id") String name


        );


    }
}
