package example.com.newnav1.deliverman;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import example.com.newnav1.R;

// عبارة عن الطلبات المكتوبه من قبل البائع والتي تظهر في شاشة المندوب والمفروض يوافق عليها المندوب أو لا

public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View MyView;
    Button orders_delveried, orders_waiting, orders_willdelvery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyView = inflater.inflate(R.layout.fragment_dorders, container, false);
        orders_delveried = (Button) MyView.findViewById(R.id.btn_orders_delveried);
        orders_waiting = (Button) MyView.findViewById(R.id.btn_orders_waiting);
        orders_willdelvery = (Button) MyView.findViewById(R.id.btn_orders_willdelvery);

        orders_delveried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersDeliverd.class);
                startActivity(intent);
            }
        });

        orders_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersWaiting.class);
                startActivity(intent);
            }
        });
        orders_willdelvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersWillDelivery.class);
                startActivity(intent);
            }
        });

        return MyView;
    }

}
