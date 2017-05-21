package example.com.newnav1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {

    EditText etMail, etPassword;
    String User_email, User_Password;
    private AoiInterFace service;
    private AppCompatActivity that = this;
    AppPrefes app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //save user data
        app = new AppPrefes(this, "app");
         /*
         * Set Up Connection
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "http://ypinsksa.com/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AoiInterFace.class);


// يتم تعريف جميع المتغيرات التي نستخدمها في شاشة تسجيل الدخول
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        final TextView text_new_user = (TextView) findViewById(R.id.text_new_user);
        final TextView text_forget = (TextView) findViewById(R.id.text_forget);
        final Button btn_login = (Button) findViewById(R.id.btn_login);

        text_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(registerIntent);
            }
        });
        text_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent1 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(registerIntent1);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    public void register() {
        intialize();
        if (!validate()) {

        } else {
            onSignInSuccess();
        }

    }

    public void onSignInSuccess() {
        final Call<User> login = service.login(User_email, User_Password);
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                if (response.code() == 200) {
                    Log.w("", "onResponse: " + response );
                    User user = response.body();
                    if (user.getCode() > 0) {
                        app.saveData("name", user.getName());
                        app.saveData("lname", user.getLastName());
                        app.saveData("email", user.getEmail());
                        app.saveData("phone", user.getPhone());
                        app.saveData("type", user.getUserType());
                        app.saveData("id", "" + user.getId());
                        app.saveData("did", "" + user.getDeliveryMan());
                        //if (user.getUserType().equals("0")) {
                            startActivity(new Intent(that, Navigation.class));
                        //}
                    } else {
                        Toast.makeText(that, "Error", Toast.LENGTH_SHORT).show();

                        MaterialDialog dialog = new MaterialDialog.Builder(that)
                                .title(R.string.app_name)
                                .content("" + user.getMessage())
                                .positiveText(android.R.string.ok)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public boolean validate() {
        boolean valid = true;
        if (User_email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(User_email).matches()) {
            etMail.setError("Please entre valid email address");
            valid = false;
        }
        if (User_Password.isEmpty()) {
            etPassword.setError("Please entre Password");
            valid = false;
        }

        return valid;
    }

    public void intialize() {
        User_email = etMail.getText().toString().trim();
        User_Password = etPassword.getText().toString().trim();
    }

    public interface AoiInterFace {
        @FormUrlEncoded
        @POST("login.php")
        Call<User> login(@Field("email") String name,
                         @Field("password") String password

        );


    }
}
