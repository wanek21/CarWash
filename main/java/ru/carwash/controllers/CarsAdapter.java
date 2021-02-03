package ru.carwash.controllers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.models.Car;
import ru.carwash.view.activities.MainActivity;
import ru.carwash.view.fragments.CarViewFragment;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

    private FragmentActivity activity; // для доступа к FragmentManager
    private ArrayList<Car> cars;

    public CarsAdapter(FragmentActivity activity, ArrayList<Car> cars) {
        this.activity = activity;
        this.cars = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout parent;
        private final ImageView imgCar;
        private final TextView tvCarName;
        private final TextView imgCarNumber;
        private final TextView imgRegion;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            parent = view.findViewById(R.id.clCarItem);
            imgCar = view.findViewById(R.id.imgCar);
            tvCarName = (TextView) view.findViewById(R.id.tvCarName);
            imgCarNumber = view.findViewById(R.id.imgNumber);
            imgRegion = view.findViewById(R.id.imgRegion);
        }

        public ConstraintLayout getParent() { return parent; }
        public ImageView getImgCar() { return imgCar; }
        public TextView getTvCarName() { return tvCarName; }
        public TextView getImgCarNumber() { return imgCarNumber; }
        public TextView getImgRegion() { return imgRegion; }
    }

    @NonNull
    @Override
    public CarsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);
        Log.d(MainActivity.TAG,"created");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.ViewHolder holder, int position) {
        holder.getParent().setOnClickListener(v -> { // обработчик нажатия на элемент в списке
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new CarViewFragment(cars.get(position)))
                    .addToBackStack("view car")
                    .commit();
        });
        holder.getImgCar().setImageResource(R.drawable.ic_car_default);
        holder.getTvCarName().setText(cars.get(position).getBrand() + " " + cars.get(position).getModel());
        holder.getImgCarNumber().setText(cars.get(position).getCarNumber());
        holder.getImgRegion().setText(cars.get(position).getRegion());
        Log.d(MainActivity.TAG,"bind");
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
