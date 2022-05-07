package com.work.clicker_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity  {
    public static boolean isActive = true;
    public static TextView cash_amount, auto_multiply;
    MediaPlayer coinSound;
    private Animation imageAnimation;
    private ImageButton coinButton;
    static Thread autoClick;
    private Button store;
    public static float multiply = 0;
    public static float cash;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();

//        autoClick = new AutoClick();

        EventHandler();


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        try {
//            if (!autoClick.isAlive()) {
//                autoClick.start();
//            }
//        }
//        catch (Exception e){
//
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            if (autoClick.isAlive()) {
//                autoClick.interrupt();
//            }
//        } catch (Exception e){
//
//        }
//    }
    private void init() {
        handler = new Handler();
        cash_amount = findViewById(R.id.cash);
        coinButton = findViewById(R.id.coinButton);
        store = findViewById(R.id.toTheStore);
        auto_multiply = findViewById(R.id.multiply);
        cash = 0;
        imageAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            autoClick.interrupt();
            System.out.println("Thread has stopped");
        } catch (Exception e){

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        try {
            if(!autoClick.isAlive()) {
                startAutoClick();
            }
            System.out.println("Thread has returned");
        }catch (Exception e){

        }

    }

    private void EventHandler(){
        coinButton.setOnClickListener(view -> {
            coinSound = MediaPlayer.create(this, R.raw.coinsound);
            coinSound.start();
            coinSound.setOnCompletionListener(MediaPlayer::release);

            cash++;
            cash_amount.setText(String.valueOf((int)cash));
            coinButton.startAnimation(imageAnimation);
        });
        store.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, improvements.class);
            startActivity(intent);

        });

    }
    public static void startAutoClick(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                while(isActive){
                    try{
                        Thread.sleep(1000);
                    }
                    catch (Exception e){
                        break;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            cash_float += multiply;
                            cash += multiply;
                            System.out.println(cash);
                            cash_amount.setText("" + (int)cash);
                            improvements.cash_store.setText("" + (int)cash);
                            String temp = String.format("%.1f", multiply);
                            auto_multiply.setText(temp + " мощность автоклика в секунду");


                        }
                    });
                }
            }
        };
        autoClick = new Thread(runnable);
        autoClick.start();
    }





}