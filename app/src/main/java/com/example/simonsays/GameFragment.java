package com.example.simonsays;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    private AppCompatButton btGreen;
    private AppCompatButton btRed;
    private AppCompatButton btYellow;
    private AppCompatButton btBlue;
    private TextView tvScore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btGreen=getActivity().findViewById(R.id.btGreen);
        btBlue=getActivity().findViewById(R.id.btBlue);
        btYellow=getActivity().findViewById(R.id.btYellow);
        btRed=getActivity().findViewById(R.id.btRed);
        tvScore=getActivity().findViewById(R.id.tvScore);
        tvScore.bringToFront();
        


    }
}
