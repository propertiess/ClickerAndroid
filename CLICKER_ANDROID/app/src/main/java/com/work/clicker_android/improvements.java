package com.work.clicker_android;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class improvements extends AppCompatActivity {
    static TextView cash_store, cost_autoclick, cost_autoclick_2;
    MediaPlayer buy_sound;
    public static long costAutoClick = 13;
    public static long costAutoClick_2 = 100;
    static long level_button_autoclick = 2, level_button_autoclick_2 = 2;
    Button buy_autoclick, buy_autoclick_2;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_improvements);
        init();

    }
    private void init(){
        handler = new Handler(); // write in onCreate function
        cash_store = findViewById(R.id.cashInTheStore);
        cost_autoclick = findViewById(R.id.cost_autoclick);
        cost_autoclick_2 = findViewById(R.id.cost_autoclick2);
        buy_autoclick = findViewById(R.id.autoclick_button);
        buy_autoclick_2 = findViewById(R.id.autoclick_button2);
        cash_store.setText(String.valueOf((int)MainActivity.cash));
        cost_autoclick.setText("Цена: \n" + costAutoClick);
        cost_autoclick_2.setText("Цена: \n" + costAutoClick_2);
        EventHandler(buy_autoclick);
        EventHandler(buy_autoclick_2);
        if(level_button_autoclick > 2) {
            try {
                if(!MainActivity.autoClick.isAlive()) {
                    MainActivity.startAutoClick();
                }
                System.out.println("Thread has returned");
            }catch (Exception e){

            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            MainActivity.autoClick.interrupt();
            System.out.println("Thread has stopped");
        } catch (Exception e){

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if(!MainActivity.autoClick.isAlive()) {
                MainActivity.startAutoClick();
            }
            System.out.println("Thread has returned");
        }catch (Exception e){

        }

    }


    private void EventHandler(Button button) {
        button.setOnClickListener(view -> {
            long value = 0;
            if(button == buy_autoclick){
                value = costAutoClick;
            }
            else if(button == buy_autoclick_2){
                value = costAutoClick_2;
            }
                if (MainActivity.cash >= value ) {
                    if (level_button_autoclick == 2) {
                        MainActivity.startAutoClick();
                    }
                    buy_sound = MediaPlayer.create(this, R.raw.sound_buy);
                    buy_sound.start();
                    buy_sound.setOnCompletionListener(MediaPlayer::release);
                    MainActivity.cash -= value;
                    MainActivity.cash_amount.setText(String.valueOf((int) MainActivity.cash));

                    cash_store.setText(String.valueOf((int) MainActivity.cash));
                    if (button == buy_autoclick) {
                        costAutoClick *= level_button_autoclick;
                        MainActivity.multiply += 0.1;
                        level_button_autoclick++;
                        cost_autoclick.setText("Цена: \n" + costAutoClick);


                    }
                    else if (button == buy_autoclick_2){
                        costAutoClick_2 *= level_button_autoclick_2;
                        MainActivity.multiply += 0.3;
                        level_button_autoclick_2++;
                        cost_autoclick_2.setText("Цена: \n" + costAutoClick_2);

                    }

                }
                else {
                    Toast.makeText(this, "Валюты недостаточно для покупки улучшения!", Toast.LENGTH_LONG).show();
                }
        });
    }



}





