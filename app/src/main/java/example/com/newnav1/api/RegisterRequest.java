package example.com.newnav1.api;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "172.246.241.129/Register_mandob.php";
    private Map<String, String> params;
    // يتم تعريف اسم الموبايل واسم المستخدم والميل والباسورد ليتم تسجيل الدخول
    public RegisterRequest( String User_Phone_no, String User_Fname, String User_Lname,String User_email, String User_Password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("User_Phone_no", User_Phone_no);
        params.put("User_Fname", User_Fname);
        params.put("User_Lname", User_Lname);
        params.put("User_email", User_email);
        params.put("User_Password", User_Password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
