package example.com.newnav1.api;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Programmer on 3/29/2017.
 */

public class NewOrderRequest extends StringRequest {
    private static final String New_Order_Requested = "172.246.241.129/NewOrder.php";
    private Map<String, String> params;

    // يتم تعريف اسم الموبايل واسم المستخدم والميل والباسورد ليتم تسجيل الدخول
    public NewOrderRequest(String customor_name, String customor_number, String customor_location, String seller_location, String product_name, String request_date, Response.Listener<String> listener) {
        super(Method.POST, New_Order_Requested, listener, null);
        params = new HashMap<>();
        params.put("Customer_name", customor_name);
        params.put("Customer_phone_no", customor_number);
        params.put("Custome_location", customor_location);
        params.put("Seller_location", seller_location);
        params.put("Product_Name", product_name);
        params.put("Request_date", request_date);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
