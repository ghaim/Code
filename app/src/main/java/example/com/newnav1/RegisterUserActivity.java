package example.com.newnav1;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.Category;
import example.com.newnav1.modal.User;
import example.com.newnav1.views.SekizbitSwitch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

// في حالة نريد تسجيل مستخدم جديد يتم تعريف كل المتغيرات المستخدمة
@SuppressWarnings("ConstantConditions")
public class RegisterUserActivity extends AppCompatActivity {
    private static final String TAG = "RegisterUserActivity";
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    String categoryId = "0";
    String User_Phone_no, User_Fname, User_Lname, User_email, User_Password, password1;
    TextView back;
    ImageView imageView;
    Uri imageUri;
    int userType;
    AppPrefes app;
    private static final int PICK_IMAGE = 100;
    private EditText editmobilenum;
    private EditText editfirstname;
    private EditText editsecondname;
    private EditText editmail;
    private EditText editpass;
    private EditText editpass1;
    private android.support.v7.widget.AppCompatButton category;
    private android.widget.CheckBox checkBox;
    private Button btnreg;
    private android.widget.LinearLayout activitynewuser;
    private AoiInterFace service;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
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


        this.activitynewuser = (LinearLayout) findViewById(R.id.activity_new_user);
        this.btnreg = (Button) findViewById(R.id.btn_reg);
        this.checkBox = (CheckBox) findViewById(R.id.checkBox);
        this.category = (AppCompatButton) findViewById(R.id.category);
        this.editpass1 = (EditText) findViewById(R.id.edit_pass1);
        this.editpass = (EditText) findViewById(R.id.edit_pass);
        this.editmail = (EditText) findViewById(R.id.edit_mail);
        this.editsecondname = (EditText) findViewById(R.id.edit_second_name);
        this.editfirstname = (EditText) findViewById(R.id.edit_first_name);
        this.editmobilenum = (EditText) findViewById(R.id.edit_mobile_num);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SekizbitSwitch mySwitch = new SekizbitSwitch(findViewById(R.id.sekizbit_switch));
        mySwitch.setSelected(0);
        mySwitch.setOnChangeListener(new SekizbitSwitch.OnSelectedChangeListener() {
            @Override
            public void OnSelectedChange(SekizbitSwitch sender) {
                if (sender.getCheckedIndex() == 0) {
                    category.setVisibility(View.VISIBLE);
                    userType = 0;
                } else if (sender.getCheckedIndex() == 1) {
                    category.setVisibility(View.GONE);
                    userType = 1;

                }
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        Call<Category> categories = service.getAll();
        categories.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, retrofit2.Response<Category> response) {

                if (response.code() == 200) {

                    for (int i = 0; i < response.body().getCategory().size(); i++) {
                        Log.w(TAG, "onResponse: " + response.toString());
                        name.add(response.body().getCategory().get(i).getName());
                        id.add(response.body().getCategory().get(i).getId());
                    }
                } else {
                    Toast.makeText(RegisterUserActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Category> call, Throwable t) {

            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(RegisterUserActivity.this)
                        .title(R.string.app_name)
                        .items(name)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                categoryId = id.get(which);
                            }
                        })
                        .show();
            }
        });
    }

    public void register() {
        intialize();
        if (!validate()) {

        } else {
            PostRegister();
        }

    }

    public void PostRegister() {


        final Call<User> register = service.register(User_Fname, User_Lname, User_email, User_Password, User_Phone_no, "" + userType, categoryId);


        register.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (user.getCode() > 0) {
                        app.saveData("name", user.getName());
                        app.saveData("lname", user.getLastName());
                        app.saveData("email", user.getEmail());
                        app.saveData("phone", user.getPhone());
                        app.saveData("type", user.getUserType());
                        app.saveData("id", "" + user.getId());
                        if (user.getUserType().equals("0")) {
                            startActivity(new Intent(RegisterUserActivity.this, Navigation.class));
                        }
                    } else {
                        Toast.makeText(RegisterUserActivity.this, "Error", Toast.LENGTH_SHORT).show();

                        MaterialDialog dialog = new MaterialDialog.Builder(RegisterUserActivity.this)
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
        if (User_Phone_no.isEmpty() || User_Phone_no.length() <= 9) {
            editmobilenum.setError("Please entre valid mobile");
            valid = false;
        }
        if (User_Fname.isEmpty()) {
            editfirstname.setError("Please entre your first name");
            valid = false;
        }
        if (User_Lname.isEmpty()) {
            editsecondname.setError("Please entre your secend name");
            valid = false;
        }

        if (User_email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(User_email).matches()) {
            editmail.setError("Please entre valid email address");
            valid = false;
        }
        if (!User_Password.equals(password1)) {
            editpass.setError("Make sure that your password is identical");
            editpass1.setError("Make sure that your password is identical");
            valid = false;
        }

        return valid;
    }

    public void intialize() {
        User_Phone_no = editmobilenum.getText().toString().trim();
        User_Fname = editfirstname.getText().toString().trim();
        User_Lname = editsecondname.getText().toString().trim();
        User_email = editmail.getText().toString().trim();
        User_Password = editpass.getText().toString().trim();
        password1 = editpass1.getText().toString().trim();
    }

    private void openGallery() {

        Intent gallerey = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallerey, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requeCode, int resultCode, Intent data) {

        super.onActivityResult(requeCode, resultCode, data);

        if (resultCode == RESULT_OK && requeCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    public interface AoiInterFace {
        @FormUrlEncoded
        @POST("register.php")
        Call<User> register(@Field("name") String name,
                            @Field("lname") String lastName,
                            @Field("email") String email,
                            @Field("password") String password,
                            @Field("phone") String phone,
                            @Field("type") String type,
                            @Field("business_id") String business_id
        );


        @GET("category.php")
        Call<Category> getAll();
    }}

