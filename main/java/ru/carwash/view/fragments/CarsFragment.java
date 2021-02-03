package ru.carwash.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.controllers.CarsAdapter;
import ru.carwash.models.Car;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnNext;
    private RecyclerView recyclerViewCars;

    public CarsFragment() {
        // Required empty public constructor
    }

    public static CarsFragment newInstance(String param1, String param2) {
        CarsFragment fragment = new CarsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cars,container,false);
        btnNext = view.findViewById(R.id.btnNext);
        recyclerViewCars = view.findViewById(R.id.rvCars);
        recyclerViewCars.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new CarViewFragment())
                    .addToBackStack("view car")
                    .commit();
        });

        btnNext.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new AddCarFragment())
                    .addToBackStack("add car")
                    .commit();
        });
        ArrayList<Car> cars = new ArrayList<>(2);
        cars.add(new Car("Audi","TT","A999AA","152","Легковая"));
        cars.add(new Car("Toyota","Camry","A234AA","52","Легковая"));

        CarsAdapter carsAdapter = new CarsAdapter(getActivity(),cars);
        recyclerViewCars.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCars.setAdapter(carsAdapter);
        return view;
    }
}