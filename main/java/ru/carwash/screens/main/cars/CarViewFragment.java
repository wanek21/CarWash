package ru.carwash.screens.main.cars;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.carwash.carwash.R;

import ru.carwash.dto.Car;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageButton btnEditCar;
    private TextView tvCarName;
    private TextView tvCarNumber;
    private TextView tvCarRegion;
    private TextView tvCarCategory;
    private ImageView imgCar;

    private Car car;

    public CarViewFragment() {
        // Required empty public constructor
    }
    public CarViewFragment(Car car) {
        this.car = car;
    }

    public static CarViewFragment newInstance(String param1, String param2) {
        CarViewFragment fragment = new CarViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        car = getArguments().getParcelable("car");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_view, container, false);
        btnEditCar = view.findViewById(R.id.btnEdit);
        tvCarName = view.findViewById(R.id.tvCarName);
        tvCarNumber = view.findViewById(R.id.tvNumber);
        tvCarRegion = view.findViewById(R.id.tvRegion);
        tvCarCategory = view.findViewById(R.id.tvCategory);

        btnEditCar.setOnClickListener(v -> {
            Bundle bundleCar = new Bundle();
            bundleCar.putParcelable("car",car);
            Navigation.findNavController(getActivity(),R.id.fragment_container).navigate(R.id.viewCar_to_editCar,bundleCar);
        });
        tvCarName.setText(car.getBrand() + " " + car.getModel());
        tvCarNumber.setText(car.getCarNumber());
        tvCarRegion.setText(car.getRegion());
        tvCarCategory.setText(car.getCategory());
        return view;
    }
}