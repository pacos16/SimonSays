package com.example.simonsays;

import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Handler handler;
    private Executor executor;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        turn=0;
        btPlay=getActivity().findViewById(R.id.btPlay);
        btGreen=getActivity().findViewById(R.id.btGreen);
        btBlue=getActivity().findViewById(R.id.btBlue);
        btYellow=getActivity().findViewById(R.id.btYellow);
        btRed=getActivity().findViewById(R.id.btRed);
        tvScore=getActivity().findViewById(R.id.tvScore);
        tvScore.setBackgroundColor(Color.BLACK);
        tvScore.bringToFront();
        tvScore.setText(String.valueOf(turn));
        handler=new Handler();

        btGreen.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.green_button));
        btYellow.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.yellow_button));
        btRed.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.red_button));
        btBlue.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.blue_button));
        executor= Executors.newSingleThreadExecutor();

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random=new Random();
                turn=1;
                btPlay.setEnabled(false);
                btPlay.setVisibility(View.GONE);
                playSecuence=new ArrayList<>();
                playSecuence.add(random.nextInt(4)+1);
                play();

            }
        });


    }

    public class BeepAndStateSelected implements Runnable{

        private int position;
        public BeepAndStateSelected(int position){
            this.position=position;
        }
        @Override
        public void run() {
            switch (playSecuence.get(position)){
                case 1:
                    btGreen.setPressed(true);
                    break;
                case 2:
                    btRed.setPressed(true);
                    break;
                case 3:
                    btYellow.setPressed(true);
                    break;
                case 4:
                    btBlue.setPressed(true);
                    break;
                default:
                    Log.i("Cagadas","La has cagado con el random");
            }
            ResetColor resetColor=new ResetColor(position);
            WaitAndReset waitAndReset=new WaitAndReset(resetColor);
            executor.execute(waitAndReset);
        }
    }

    public class WaitAndBeep implements Runnable{

        BeepAndStateSelected beepAndStateSelected;

        public WaitAndBeep(BeepAndStateSelected beepAndStateSelected) {
            this.beepAndStateSelected = beepAndStateSelected;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                handler.post(beepAndStateSelected);
            }catch (InterruptedException ie){

            }
        }
    }

    public class WaitAndReset implements Runnable{

        ResetColor resetColor;

        public WaitAndReset(ResetColor resetColor) {
            this.resetColor = resetColor;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(300);
                handler.post(resetColor);
            }catch (InterruptedException ie){

            }

        }
    }

    public class ResetColor implements Runnable{
        private int position;

        public ResetColor(int position) {
            this.position = position;
        }

        @Override
        public void run() {
            switch (playSecuence.get(position)){
                case 1:
                    btGreen.setPressed(false);
                    break;
                case 2:
                    btRed.setPressed(false);
                    break;
                case 3:
                    btYellow.setPressed(false);
                    break;
                case 4:
                    btBlue.setPressed(false);
                    break;
                    default:
                        Log.i("Cagadas","La has cagado con el random");
            }
            if(position<playSecuence.size()-1){
                BeepAndStateSelected beepAndStateSelected=new BeepAndStateSelected(position+1);
                WaitAndBeep waitAndBeep=new WaitAndBeep(beepAndStateSelected);
                executor.execute(waitAndBeep);
            }else{
                playSecuence.add(random.nextInt(4)+1);
            }
        }
    }


    private void play(){

        BeepAndStateSelected beepAndStateSelected=new BeepAndStateSelected(0);
        WaitAndBeep waitAndBeep=new WaitAndBeep(beepAndStateSelected);
        executor.execute(waitAndBeep);

    }

    public void jugadorPlay(){

    }

    public void disableButtons(){

    }

    public void enableButtons(){

    }




}
