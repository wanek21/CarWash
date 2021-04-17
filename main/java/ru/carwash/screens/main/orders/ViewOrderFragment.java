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
import android.widget.ImageButton;

import com.carwash.carwash.R;

import java.util.ArrayList;

import ru.carwash.dto.Order;
import ru.carwash.dto.Service;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Order order;

    private ImageButton btnEdit;
    private RecyclerView rvServices;
    private Button btnLeaveReview;

    public ViewOrderFragment() {
        // Required empty public constructor
    }
    public ViewOrderFragment(Order order) {
        this.order = order;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewOrderFragment newInstance(String param1, String param2) {
        ViewOrderFragment fragment = new ViewOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = getArguments().getParcelable("order");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order, container, false);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnLeaveReview = view.findViewById(R.id.btnLeaveReview);
        btnLeaveReview.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(),R.id.fragment_container).navigate(R.id.action_viewOrder_to_leaveReview);
        });
        rvServices = view.findViewById(R.id.rvServices);

        if(order.getStatus() == Order.CANCELED_STATUS) {
            btnEdit.setVisibility(View.INVISIBLE);
            btnLeaveReview.setVisibility(View.INVISIBLE);
        } else if(order.getStatus() == Order.ACCEPTED_STATUS) btnLeaveReview.setVisibility(View.INVISIBLE);
        else if(order.getStatus() == Order.COMPLETED_STATUS) btnEdit.setVisibility(View.INVISIBLE);

        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service("Сполоснуть водой", 250));
        services.add(new Service("Техническая мойка", 150));
        services.add(new Service("Комплексная мойка", 350));
        order.setServices(services);
        rvServices.setLayoutManager(new LinearLayoutManager(getContext()));

        ServicesAdapter servicesAdapter = new ServicesAdapter(getActivity(), services, false);
        rvServices.setAdapter(servicesAdapter);

        return view;
    }
}