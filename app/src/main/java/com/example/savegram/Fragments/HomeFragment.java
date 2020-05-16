package com.example.savegram.Fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.savegram.ClipBoardManager.ClipboardService;
import com.example.savegram.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Switch toggle = view.findViewById(R.id.startstop);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked) startService();
               else stopService();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void startService() {
        ContextCompat.startForegroundService(getActivity(), new Intent(getActivity(), ClipboardService.class));
    }

    public void stopService() {
        Intent serviceIntent = new Intent(getActivity(), ClipboardService.class);
        getActivity().stopService(serviceIntent);
    }
}
