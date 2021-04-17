package ru.carwash.screens.main.orders;

import android.content.Context;
import android.graphics.Color;
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

import ru.carwash.dto.Car;

/* Адаптер для RecycleView списка машин, который находится во фрагменте создания заказа */
public class CarsInOrderAdapter extends RecyclerView.Adapter<CarsInOrderAdapter.ViewHolder> {

    private Context context; // для доступа к FragmentManager
    private ArrayList<Car> cars;

    private int selectedCar = RecyclerView.NO_POSITION;

    public CarsInOrderAdapter(FragmentActivity activity, ArrayList<Car> cars) {
        this.context = activity;
        this.cars = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout parent;
        private final TextView tvCarName;
        private final TextView imgCarNumber;
        private final TextView imgRegion;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            parent = view.findViewById(R.id.clCarItem);
            tvCarName = view.findViewById(R.id.tvCarName);
            imgCarNumber = view.findViewById(R.id.imgNumber);
            imgRegion = view.findViewById(R.id.imgRegion);
        }

        public ConstraintLayout getParent() { return parent; }
        public TextView getTvCarName() { return tvCarName; }
        public TextView getImgCarNumber() { return imgCarNumber; }
        public TextView getImgRegion() { return imgRegion; }
    }

    @NonNull
    @Override
    public CarsInOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_in_order_item, parent, false);
        return new CarsInOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsInOrderAdapter.ViewHolder holder, int position) {
        holder.itemView.setSelected(selectedCar == position);

        // изменяем цвет текста названия машины, если машина подсвечена/не подсвечена
        if(selectedCar == position) holder.getTvCarName().setTextColor(Color.WHITE);
        else holder.getTvCarName().setTextColor(Color.BLACK);

        holder.getParent().setOnClickListener(v -> { // обработчик нажатия на элемент в списке для подсветки выделенного авто
            notifyItemChanged(selectedCar);
            selectedCar = holder.getLayoutPosition();
            notifyItemChanged(selectedCar);
        });
        holder.getTvCarName().setText(cars.get(position).getBrand() + " " + cars.get(position).getModel());
        holder.getImgCarNumber().setText(cars.get(position).getCarNumber());
        holder.getImgRegion().setText(cars.get(position).getRegion());
    }



    @Override
    public int getItemCount() {
        return cars.size();
    }
}
