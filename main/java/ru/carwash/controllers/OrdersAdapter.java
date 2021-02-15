package ru.carwash.controllers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.models.Order;
import ru.carwash.view.fragments.ViewOrderFragment;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private FragmentActivity context; // для доступа к FragmentManager
    private ArrayList<Order> orders;

    public OrdersAdapter(FragmentActivity activity, ArrayList<Order> orders) {
        this.context = activity;
        this.orders = orders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout parent;
        private final TextView tvStatus;
        private final TextView tvCarWash;
        private final TextView tvData;

        public ViewHolder(@NonNull View view) {
            super(view);

            parent = view.findViewById(R.id.clOrderItem);
            tvStatus = view.findViewById(R.id.tvStatusValue);
            tvCarWash = view.findViewById(R.id.tvCarWash);
            tvData = view.findViewById(R.id.tvData);
        }

        public ConstraintLayout getParent() {return parent;}
        public TextView getTvStatus() {return tvStatus;}
        public TextView getTvCarWash() {return tvCarWash;}
        public TextView getTvData() {return tvData;}
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new OrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getParent().setOnClickListener(v -> {
            context.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new ViewOrderFragment(orders.get(position)))
                    .addToBackStack("view order")
                    .commit();
        });
        if (orders.get(position).getStatus() == Order.ACCEPTED_STATUS) {
            holder.getTvStatus().setText(R.string.accepted_status); // ставим текст "Принят"
            holder.getTvStatus().setTextColor(context.getResources().getColor(R.color.accepted_status)); // задаем соответствующий цвет текста
        } else if(orders.get(position).getStatus() == Order.COMPLETED_STATUS) {
            holder.getTvStatus().setText(R.string.completed_status);
            holder.getTvStatus().setTextColor(context.getResources().getColor(R.color.completed_status));
        } else if(orders.get(position).getStatus() == Order.CANCELED_STATUS) {
            holder.getTvStatus().setText(R.string.canceled_status);
            holder.getTvStatus().setTextColor(context.getResources().getColor(R.color.canceled_status));
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
