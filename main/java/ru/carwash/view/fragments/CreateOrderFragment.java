package ru.carwash.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.carwash.carwash.R;

/* Фрагмент для процесса создания\заполнения заказа
*  Представляет из себя Toolbar и ViewPager, в котором свайпаются\переключаются шаги заказа */
public class CreateOrderFragment extends Fragment {

    private ImageButton btnBack;
    private TextView tvNext;
    private TextView tvStep;
    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private final int COUNT_ORDERING_STEPS = 3; // кол-во шагов (фрагментов) при заполнении заказа

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_order, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        });
        tvNext = view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        });
        tvStep = view.findViewById(R.id.tvStep);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3); // делаем так, чтобы состояние фрагментов сохранялось при свайпах
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tvStep.setText("Шаг " + ++position + "/3");
            }
        });
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new OrderingStep1Fragment();
                case 1: return new OrderingStep2Fragment();
                case 2: return new OrderingStep3Fragment();

                default: return new OrderingStep1Fragment();
            }
        }

        @Override
        public int getCount() {
            return COUNT_ORDERING_STEPS;
        }


    }
}