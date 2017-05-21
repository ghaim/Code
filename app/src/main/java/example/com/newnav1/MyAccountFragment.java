package example.com.newnav1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import example.com.newnav1.api.AppPrefes;

import static android.app.Activity.RESULT_OK;

// الصفحه الرئيسية

public class MyAccountFragment extends Fragment {

    View MyView;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    ImageView imageView;
    private SharedPreferences mPrefs;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    AppPrefes app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_my_account, container, false);
        app = new AppPrefes(getContext(), "app");
        imageView = (ImageView) MyView.findViewById(R.id.imageView);
        EditText View_name = (EditText) MyView.findViewById(R.id.View_name);
        final EditText View_mobile = (EditText) MyView.findViewById(R.id.View_mobile);
        EditText View_mail = (EditText) MyView.findViewById(R.id.View_mail);


        app.getData("lname");

        app.getData("type");
        app.getData("id");


        View_name.setText(app.getData("name"));
        View_mobile.setText(app.getData("phone"));
        View_mail.setText(app.getData("email"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        return MyView;
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

}
