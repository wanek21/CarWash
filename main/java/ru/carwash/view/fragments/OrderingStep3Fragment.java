package ru.carwash.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.controllers.ServicesAdapter;
import ru.carwash.models.Service;
import ru.carwash.view.activities.MainActivity;

public class OrderingStep3Fragment extends Fragment {

    private RecyclerView rvServices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordering_step3, container, false);
        rvServices = view.findViewById(R.id.rvServices);

        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service("Сполоснуть водой", 250));
        services.add(new Service("Техническая мойка", 150));
        services.add(new Service("Комплексная мойка", 350));
        services.add(new Service("Пылесос", 50));
        services.add(new Service("Протереть стекла", 100));
        services.add(new Service("Наружная мойка с коврами", 100));
        services.add(new Service("Помыть фары", 70));
        services.add(new Service("Протереть очки", 70));


        ServicesAdapter servicesAdapter = new ServicesAdapter(getActivity(), services);
        rvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServices.setAdapter(servicesAdapter);

        return view;
    }
}