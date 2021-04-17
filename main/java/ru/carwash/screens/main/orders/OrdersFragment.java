package ru.carwash.screens.main.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.dto.Order;

/* Фрагмент, содержащий список заказов и кнопку для создания нового заказа */
public class OrdersFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private Button btnNewOrder;

    private ArrayList<Order> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerViewOrders = view.findViewById(R.id.rvOrders);
        btnNewOrder = view.findViewById(R.id.btnNewOrder);
        btnNewOrder.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(),R.id.fragment_container).navigate(R.id.action_ordersList_to_createOrder);
        });

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