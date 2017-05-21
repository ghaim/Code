package example.com.newnav1.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import example.com.newnav1.R;
import example.com.newnav1.modal.Order_;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private List<Order_> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, order_c_ni;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.order_name);
            order_c_ni = (TextView) view.findViewById(R.id.order_c_ni);

        }
    }


    public ReportAdapter(List<Order_> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order_ movie = moviesList.get(position);


        holder.name.setText(movie.getName());
        holder.order_c_ni.setText(movie.getCustomerNeighborhood() + " - " + movie.getDeliveringTime());


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}