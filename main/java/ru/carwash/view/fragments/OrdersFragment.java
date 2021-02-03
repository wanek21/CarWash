package ru.carwash.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.controllers.OrdersAdapter;
import ru.carwash.models.Order;

public class OrdersFragment extends Fragment {

    private RecyclerView recyclerViewOrders;

    private ArrayList<Order> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerViewOrders = view.findViewById(R.id.rvOrders);

        orders = new ArrayList<Order>(12);
        orders.add(new Order(0));
        orders.add(new Order(1));
        orders.add(new Order(2));

        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrdersAdapter ordersAdapter = new OrdersAdapter(getActivity(),orders);
        recyclerViewOrders.setAdapter(ordersAdapter);
        return view;
    }
}