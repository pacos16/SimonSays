package com.example.simonsays;

import android.graphics.Color;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
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
    private int secuencePosition;
    private SoundPool soundPool;
    private int sound1;
    private int sound2;
    private int sound3;
    private int sound4;


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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool=new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        }
        sound1=soundPool.load(getContext(),R.raw.beep01,1);
        sound2=soundPool.load(getContext(),R.raw.beep02,1);
        sound3=soundPool.load(getContext(),R.raw.beep03,1);
        sound4=soundPool.load(getContext(),R.raw.beep04,1);
        btGreen.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.green_button));
        btYellow.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.yellow_button));
        btRed.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.red_button));
        btBlue.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.blue_button));
        executor= Executors.newSingleThreadExecutor();
        btPlay.setEnabled(false);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                btPlay.setEnabled(true);
            }
        });

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



        View.OnClickListener onClickListener=new View.OnClickListener() {

            int numero;
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btGreen:
                        soundPool.play(sound1,1,1,1,0,1);
                        numero=1;
                        break;
                    case R.id.btRed:
                        soundPool.play(sound2,1,1,1,0,1);
                        numero=2;
                        break;
                    case R.id.btYellow:
                        soundPool.play(sound3,1,1,1,0,1);
                        numero=3;
                        break;
                    case R.id.btBlue:
                        soundPool.play(sound4,1,1,1,0,1);
                        numero=4;
                        break;

                }

                if(checkPlay(numero,secuencePosition)){
                    if(secuencePosition<playSecuence.size()-1){
                        secuencePosition++;
                    }else{
                        playSecuence.add(random.nextInt(4)+1);
                        turn++;
                        play();
                    }
                }else{
                    //puntuaiones
                    restartGame();

                }
            }
        };

        btBlue.setOnClickListener(onClickListener);
        btGreen.setOnClickListener(onClickListener);
        btRed.setOnClickListener(onClickListener);
        btYellow.setOnClickListener(onClickListener);


    }

    public void restartGame(){
        secuencePosition=0;
        playSecuence=new ArrayList<>();
        playSecuence.add(random.nextInt(4)+1);
        turn=1;
        play();
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
                    soundPool.play(sound1,1,1,1,0,1);
                    break;
                case 2:
                    btRed.setPressed(true);
                    soundPool.play(sound2,1,1,1,0,1);
                    break;
                case 3:
                    btYellow.setPressed(true);
                    soundPool.play(sound3,1,1,1,0,1);
                    break;
                case 4:
                    btBlue.setPressed(true);
                    soundPool.play(sound4,1,1,1,0,1);
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
            soundPool.autoPause();
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
                jugadorPlay();
            }
        }
    }


    private void play(){
        tvScore.setText(String.valueOf(turn));
        disableButtons();
        BeepAndStateSelected beepAndStateSelected=new BeepAndStateSelected(0);
        WaitAndBeep waitAndBeep=new WaitAndBeep(beepAndStateSelected);
        executor.execute(waitAndBeep);

    }

    public void jugadorPlay(){
        enableButtons();
        secuencePosition=0;
    }

    public boolean checkPlay(int numero,int posicion){
        if(numero==playSecuence.get(posicion)){
            return true;
        }
        return false;
    }

    public void disableButtons(){
        btGreen.setEnabled(false);
        btRed.setEnabled(false);
        btYellow.setEnabled(false);
        btBlue.setEnabled(false);
    }

    public void enableButtons(){
        btGreen.setEnabled(true);
        btRed.setEnabled(true);
        btYellow.setEnabled(true);
        btBlue.setEnabled(true);
    }




}
