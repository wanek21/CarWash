package ru.carwash.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.speech.SpeechRecognizer;

import com.carwash.carwash.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.carwash.view.fragments.AcceptNewOrderFragment;
import ru.carwash.view.fragments.CarsFragment;
import ru.carwash.view.fragments.OrdersFragment;
import ru.carwash.view.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "CAR_WASH";
    private final String TAG_AUTO_FRAGMENT = "CARS_FRAGMENT";
    private final String TAG_ORDERS_FRAGMENT = "ORDERS_FRAGMENT";
    private final String TAG_MAP_FRAGMENT = "MAP_FRAGMENT";
    private final String TAG_PROFILE_FRAGMENT = "PROFILE_FRAGMENT";

    private BottomNavigationView navigationBar;
    private Fragment carsFragment;
    private Fragment ordersFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CarWashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.nav_bar);
        carsFragment = new CarsFragment();
        ordersFragment = new OrdersFragment();
        profileFragment = new ProfileFragment();
        setCurrentFragment(carsFragment, TAG_AUTO_FRAGMENT);

        navigationBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.auto:
                    setCurrentFragment(carsFragment, TAG_AUTO_FRAGMENT);
                    break;
                case R.id.orders:
                    setCurrentFragment(ordersFragment, TAG_ORDERS_FRAGMENT);
                    break;
                case R.id.profile:
                    setCurrentFragment(profileFragment, TAG_PROFILE_FRAGMENT);
                    break;
            }

            return true;
        });
    }

    private void setCurrentFragment(Fragment fragment, String tag) {
        Fragment fragmentForChecking = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentForChecking == null || !fragmentForChecking.isVisible()) { // проверяем открыт ли уже этот фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,fragment,tag)
                    .commit();
        }
    }
}