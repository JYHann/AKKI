package com.example.sanhak3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Temp extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp,container,false);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
