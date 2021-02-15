package ru.carwash.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.carwash.carwash.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ru.carwash.models.Service;
import ru.carwash.view.activities.MainActivity;
import ru.carwash.view.fragments.AcceptNewOrderFragment;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Service> services;
    private boolean isClickable = true;

    private final ArrayList<Integer> selectedServices = new ArrayList<>();

    public ServicesAdapter(Activity activity, ArrayList<Service> services) {
        this.context = activity;
        this.services = services;
    }

    public ServicesAdapter(Activity activity, ArrayList<Service> services, boolean isClickable) {
        this.context = activity;
        this.services = services;
        this.isClickable = isClickable;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout parent;
        private final TextView tvService;
        private final TextView tvPrice;
        private final View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.clMain);
            tvService = itemView.findViewById(R.id.tvService);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            line = itemView.findViewById(R.id.line);
        }

        public ConstraintLayout getParent() { return parent; }
        public TextView getTvService() {return tvService;}
        public TextView getTvPrice() {return tvPrice;}
        public View getLine() {return line;}
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item, parent, false);
        return new ViewHolder(view);
    }

    public ArrayList<Service> getSelectedServices() {
        ArrayList<Service> result = new ArrayList<>();
        for (Integer position :
                selectedServices) {
            result.add(services.get(position));
        }
        return result;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(isClickable) {
            holder.getParent().setOnClickListener(v -> { // обработчик нажатия на элемент в списке для подсветки выделенной услуги
                if(selectedServices.contains(position)) { // убираем подсветку
                    selectedServices.remove(selectedServices.indexOf(position));
                    v.setBackgroundColor(Color.WHITE);
                    holder.getLine().setVisibility(View.VISIBLE);
                    holder.getTvService().setTextColor(context.getResources().getColor(R.color.hint));
                }
                else { // ставим подстветку
                    selectedServices.add(position);
                    v.setBackgroundColor(context.getResources().getColor(R.color.accent));
                    holder.getLine().setVisibility(View.INVISIBLE);
                    holder.getTvService().setTextColor(Color.WHITE);
                }
            });
        }
        holder.getTvService().setText(services.get(position).getName());
        holder.getTvPrice().setText(services.get(position).getPrice() + "Р");
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
