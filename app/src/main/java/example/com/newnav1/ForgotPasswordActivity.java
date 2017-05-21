package example.com.newnav1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
// في حالة نسيان الباسورد يتم فتح هذه النافذة
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
    }
}
