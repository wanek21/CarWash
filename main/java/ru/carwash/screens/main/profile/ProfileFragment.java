package ru.carwash.screens.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.carwash.carwash.R;

public class ProfileFragment extends Fragment {

    private ImageButton btnEdit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(),R.id.fragment_container).navigate(R.id.action_profile_to_editProfile);
        });
        return view;
    }
}