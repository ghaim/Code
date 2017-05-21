package example.com.newnav1;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int wid = ds.widthPixels;
        int hig = ds.heightPixels;

        getWindow().setLayout((int)(wid*.9),(int)(hig*.8));

    }
}
