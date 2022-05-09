package com.work.clicker_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity  {
    public static boolean isActive = true;
    public static TextView cash_amount, auto_multiply;
    MediaPlayer coinSound;
    private Animation imageAnimation;
    private ImageButton coinButton;
    public static Thread autoClick;
    private Button store;
    public static int counterThread = 0;
    public static float multiply = 0;
    public static float cash;
    Handler handler;
    static improvements  improve;
    public static boolean bought;
    public static SharedPreferences.Editor myEditor;
    SharedPreferences myPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        // default settings
        init();

        // event listener
        EventHandler();


    }


    @SuppressLint("SetTextI18n")
    private void init() {
        handler = new Handler();
        cash_amount = findViewById(R.id.cash);
        store = findViewById(R.id.toTheStore);
        coinButton = findViewById(R.id.coinButton);
        auto_multiply = findViewById(R.id.multiply);
        improve = new improvements();
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();
        cash = myPreferences.getFloat("Money",0);
        bought = myPreferences.getBoolean("bought", false);
        multiply = myPreferences.getFloat("multiply", 0);

        if (multiply > 0) {
            startAutoClick();
            counterThread++;
        }

        @SuppressLint("DefaultLocale") String temp = String.format("%.1f", multiply);
        auto_multiply.setText(temp + " мощность автоклика в секунду");

        cash_amount.setText("" + (int)cash);

        imageAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myEditor.putFloat("Money", cash);
        try {
            myEditor.commit();
            autoClick.interrupt();

            System.out.println("Thread has stopped");


        } catch (Exception e){
            System.out.println("Error Thread onPause() MainActivity");

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
            System.out.println("Error Thread onResume() MainActivity");
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

    // execute thread
    public static void startAutoClick() {
        Handler handler = new Handler();
        @SuppressLint("SetTextI18n") Runnable runnable = () -> {
            while (isActive) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    break;
                }
                handler.post(() -> {
                    cash += multiply;
                    System.out.println(cash);
                    cash_amount.setText("" + (int) cash);
                    try {
                        improvements.cash_store.setText("" + (int) cash);
                    } catch (NullPointerException e){
                        System.out.println("NullPointerEx startAutoClick MainActivity");
                    }
                    @SuppressLint("DefaultLocale") String temp = String.format("%.1f", multiply);
                    auto_multiply.setText(temp + " мощность автоклика в секунду");


                });
            }
        };
        autoClick = new Thread(runnable);
        autoClick.start();
    }

}