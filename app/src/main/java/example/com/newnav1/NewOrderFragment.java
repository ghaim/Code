package example.com.newnav1;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import example.com.newnav1.api.AppPrefes;
import example.com.newnav1.modal.Category;
import example.com.newnav1.modal.User;
import io.blackbox_vision.datetimepickeredittext.view.TimePickerEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
// هنا يتم وضع الطلبات الجديدة التي نكتبها والتي تظهر عند المندووب


public class NewOrderFragment extends Fragment {

    //todo date piker
    //todo remove lat & lang
    // TODO: Rename parameter arguments, choose names that match
    View MyView;
    EditText EtPlace;
    Button button;
    EditText customorname, customorphone, customlocation, sellerlocation, delivery_price, Payment_type;
    TimePickerEditText sellerTime, requestdate;
    String cName, cNumber, cNi, sNi, pName, dTime, cLocation, sLoaction;
    AppCompatButton productname;
    int PLACE_PICKER_REQUEST = 1;
    private AoiInterFace service;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    private String categoryId;
    AppPrefes app;
    private String latlang;

    public NewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyView = inflater.inflate(R.layout.fragment_new_order, container, false);
        app = new AppPrefes(getContext(), "app");
        /*
         * Set Up Connection
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "http://ypinsksa.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AoiInterFace.class);


        customorname = (EditText) MyView.findViewById(R.id.Customer_name);
        customorphone = (EditText) MyView.findViewById(R.id.Customer_phone_no);
        customlocation = (EditText) MyView.findViewById(R.id.Custome_ni);
        sellerlocation = (EditText) MyView.findViewById(R.id.Seller_ni);
        productname = (AppCompatButton) MyView.findViewById(R.id.productName);
        delivery_price = (EditText) MyView.findViewById(R.id.delivery_price);
        Payment_type = (EditText) MyView.findViewById(R.id.Payment_type);

        requestdate = (TimePickerEditText) MyView.findViewById(R.id.Request_date);
        sellerTime = (TimePickerEditText) MyView.findViewById(R.id.seller_time);

        EtPlace = (EditText) MyView.findViewById(R.id.place);
        button = (Button) MyView.findViewById(R.id.map_costomor);

        sellerTime.setManager(getActivity().getSupportFragmentManager());
        requestdate.setManager(getActivity().getSupportFragmentManager());
        configure_button();

        final Button make_order = (Button) MyView.findViewById(R.id.make_order);

        make_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }

            public void register() {
                intialize();
                if (!validate()) {

                } else {
                    onRegisterSuccess();
                }

            }


            public void onRegisterSuccess() {

                Call<User> order = service.makeOrder(cNumber, cName, cNi, "Not Defined", latlang, sNi, categoryId, Payment_type.getText().toString(), delivery_price.getText().toString(), "0", "0", sellerTime.getText().toString(), requestdate.getText().toString(), categoryId, app.getData("id"));
                order.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.w(TAG, "onResponse: " + response.toString());
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });


        sellerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Call<Category> categories = service.getAll();
        categories.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, retrofit2.Response<Category> response) {

                if (response.code() == 200) {

                    for (int i = 0; i < response.body().getCategory().size(); i++) {
                        name.add(response.body().getCategory().get(i).getName());
                        id.add(response.body().getCategory().get(i).getId());
                    }
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Category> call, Throwable t) {

            }
        });


        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.app_name)
                        .items(name)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                categoryId = id.get(which);
                                productname.setText(name.get(which));
                            }
                        })
                        .show();
            }
        });

        return MyView;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.
                                ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        if (cName.isEmpty()) {
            customorname.setError("قم بكتابة اسمك");
            valid = false;
        }
        if (cNumber.isEmpty()) {
            customorphone.setError("قم بكتابة رقم موبايلك");
            valid = false;
        }
        if (cNi.isEmpty()) {
            customlocation.setError("قم بتحديد مكانك");
            valid = false;
        }

        if (sNi.isEmpty()) {
            sellerlocation.setError("قم بكتابة مكان البائع");
            valid = false;
        }
        if (pName.isEmpty()) {
            productname.setError("قم بكتابة اسم المنتج التي تريده");
            valid = false;
        }
        if (dTime.isEmpty()) {
            requestdate.setError("قم بكتابة تاريخ الطلب");
            valid = false;
        }

        return valid;
    }

    public void intialize() {
        cName = customorname.getText().toString();
        cNumber = customorphone.getText().toString();
        cNi = customlocation.getText().toString();
        sNi = sellerlocation.getText().toString();
        pName = productname.getText().toString();
        dTime = requestdate.getText().toString();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
                String place_name = place.getName().toString();
                String place_addrress = place.getAddress().toString();
                latlang = place.getLatLng().latitude + "," + place.getLatLng().longitude;
                Log.w(TAG, "onActivityResult: " + latlang);
            }
        }
    }

    public interface AoiInterFace {
        @FormUrlEncoded
        @POST("newOrder.php")
        Call<User> makeOrder(@Field("cPhone") String cPhone,
                             @Field("cName") String cName,
                             @Field("cNiHood") String cNiHood,
                             @Field("cNi") String cLocation,
                             @Field("sLocation") String sLocation,
                             @Field("sNiHood") String sNiHood,
                             @Field("pName") String pName,
                             @Field("pPrice") String pPrice,
                             @Field("dPrice") String dPrice,
                             @Field("carSize") String carSize,
                             @Field("payment") String payment,
                             @Field("pikingTime") String pikingTime,
                             @Field("deliverTime") String deliverTime,
                             @Field("cId") String cId,
                             @Field("sId") String sId
        );


        @GET("category.php")
        Call<Category> getAll();
    }


}

