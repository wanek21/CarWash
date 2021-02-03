package ru.carwash.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carwash.carwash.R;

import ru.carwash.models.Car;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnSaveCar;

    private Car car;

    public EditCarFragment() {
        // Required empty public constructor
    }
    public EditCarFragment(Car car) {
        this.car = car;
    }

    public static EditCarFragment newInstance(String param1, String param2) {
        EditCarFragment fragment = new EditCarFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_car, container, false);
        btnSaveCar = view.findViewById(R.id.btnSave);
        btnSaveCar.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .popBackStack();
        });
        return view;
    }
}