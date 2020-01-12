package com.example.simonsays;

import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

public class GameFragment extends Fragment {
    private AppCompatButton btGreen;
    private AppCompatButton btRed;
    private AppCompatButton btYellow;
    private AppCompatButton btBlue;
    private TextView tvScore;
    private ArrayList<Integer> playSecuence;
    private Random random;
    private int turn;
    private Button btPlay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btPlay=getActivity().findViewById(R.id.btPlay);
        btGreen=getActivity().findViewById(R.id.btGreen);
        btBlue=getActivity().findViewById(R.id.btBlue);
        btYellow=getActivity().findViewById(R.id.btYellow);
        btRed=getActivity().findViewById(R.id.btRed);
        tvScore=getActivity().findViewById(R.id.tvScore);
        tvScore.setBackgroundColor(Color.BLACK);
        tvScore.bringToFront();

        btGreen.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.green_button));
        btYellow.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.yellow_button));
        btRed.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.red_button));
        btBlue.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.blue_button));


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random=new Random();
                turn=1;
                btPlay.setEnabled(false);
                btPlay.setVisibility(View.GONE);

            }
        });


    }


    





}
