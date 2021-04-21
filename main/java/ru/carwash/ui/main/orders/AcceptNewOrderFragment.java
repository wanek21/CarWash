package ru.carwash.ui.main.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.dto.Service;

public class AcceptNewOrderFragment extends Fragment {

    private RecyclerView rvServices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_new_order, container, false);
        rvServices = view.findViewById(R.id.rvServices);

        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service("Сполоснуть водой", 250));
        services.add(new Service("Техническая мойка", 150));
        services.add(new Service("Комплексная мойка", 350));
        ServicesAdapter servicesAdapter = new ServicesAdapter(getActivity(), services, false);
        rvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServices.setAdapter(servicesAdapter);

        return view;
    }
}