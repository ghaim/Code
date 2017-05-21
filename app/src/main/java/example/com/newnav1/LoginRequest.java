package example.com.newnav1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
 // هنا يتم تعريف ايميل المستخدم وكلمة المروو ليمر بها الي نافذة الدخول
public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://ypinsksa.com/login.php";
    private Map<String, String> params;

    public LoginRequest(String User_email, String User_Password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("User_email", User_email);
        params.put("User_Password", User_Password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
