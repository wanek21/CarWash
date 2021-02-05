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

import ru.carwash.controllers.CarsInOrderAdapter;
import ru.carwash.models.Car;

public class OrderingStep1Fragment extends Fragment {

    private RecyclerView recyclerViewCars;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordering_step1, container, false);

        recyclerViewCars = view.findViewById(R.id.rvCars);

        ArrayList<Car> cars = new ArrayList<>(2);
        cars.add(new Car("Audi","TT","A999AA","152","Легковая"));
        cars.add(new Car("Toyota","Camry","A234AA","52","Легковая"));
        CarsInOrderAdapter carsInOrderAdapter = new CarsInOrderAdapter(getActivity(), cars);
        recyclerViewCars.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCars.setAdapter(carsInOrderAdapter);

        return view;
    }
}