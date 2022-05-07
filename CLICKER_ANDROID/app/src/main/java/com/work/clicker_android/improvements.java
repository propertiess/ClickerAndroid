package com.work.clicker_android;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.work.clicker_android.adapter.ProductAdapter;
import com.work.clicker_android.model.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class improvements extends AppCompatActivity {
    public static TextView cash_store;
    public static MediaPlayer buy_sound;
    public static long costAutoClick = 13;
    public static long level_button_autoclick = 1;
    public  List<Product> productList;
    public static List<Product> copy;
    @NonNull
    ProductAdapter.ProductViewHolder holder;
    RecyclerView rv;
    ProductAdapter productAdapter;
    Handler handler;
    int count = 1;
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
        if(productList == null) {
            productList = new ArrayList<>();
        }
        productList.add(new Product("Mining", 0.1f, costAutoClick, level_button_autoclick));
        productList.add(new Product("NFT", 0.3f, 100, level_button_autoclick));
        for (int i = 0; i < productList.size(); i++) {
            setProductRecycler(productList);

            System.out.println(productList.size());
            System.out.println(productList);
        }

        System.out.println("Count " + count);
        handler = new Handler(); // write in onCreate function
        cash_store = findViewById(R.id.cashInTheStore);
//        cost_autoclick = findViewById(R.id.cost_autoclick);
//        cost_autoclick_2 = findViewById(R.id.cost_autoclick2);
//        buy_autoclick = findViewById(R.id.autoclick_button);
//        buy_autoclick_2 = findViewById(R.id.autoclick_button2);
        cash_store.setText(String.valueOf((int)MainActivity.cash));
//        cost_autoclick.setText("Цена: \n" + costAutoClick);
//        cost_autoclick_2.setText("Цена: \n" + costAutoClick_2);
//        EventHandler(buy_autoclick);
//        EventHandler(buy_autoclick_2);
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


    public void Buying(long value, int position,String name_improve, float speed_improve){
        if(MainActivity.cash >= value){
            level_button_autoclick++;
            if (level_button_autoclick > 1 && !MainActivity.autoClick.isAlive()) {
                MainActivity.startAutoClick();
            }
            buy_sound = MediaPlayer.create(cash_store.getContext(), R.raw.sound_buy);
            buy_sound.start();
            buy_sound.setOnCompletionListener(MediaPlayer::release);
            MainActivity.cash -= value;
            MainActivity.cash_amount.setText(String.valueOf((int) MainActivity.cash));

            cash_store.setText(String.valueOf((int) MainActivity.cash));
            costAutoClick *= 2;
            MainActivity.multiply += speed_improve;
            System.out.println(position);
//            if(productList == null) {
//                productList = new ArrayList<>();
//            }
            System.out.println(productList);
            System.out.println(copy);

//            productList = copy;
//            productList.remove(position);
//            productList.add(position, new Product(name_improve, speed_improve, costAutoClick, level_button_autoclick));
//            copy = productList;

        }
        else {
            Toast.makeText(cash_store.getContext(), "Валюты недостаточно для покупки улучшения!", Toast.LENGTH_LONG).show();
        }

    }
//    private void EventHandler(Button button) {
//        button.setOnClickListener(view -> {
//            long value = 0;
//            if(button == buy_autoclick){
//                value = costAutoClick;
//            }
//            else if(button == buy_autoclick_2){
//                value = costAutoClick_2;
//            }
//                if (MainActivity.cash >= value ) {
//                    if (level_button_autoclick == 2) {
//                        MainActivity.startAutoClick();
//                    }
//                    buy_sound = MediaPlayer.create(this, R.raw.sound_buy);
//                    buy_sound.start();
//                    buy_sound.setOnCompletionListener(MediaPlayer::release);
//                    MainActivity.cash -= value;
//                    MainActivity.cash_amount.setText(String.valueOf((int) MainActivity.cash));
//
//                    cash_store.setText(String.valueOf((int) MainActivity.cash));
//                    if (button == buy_autoclick) {
//                        costAutoClick *= level_button_autoclick;
//                        MainActivity.multiply += 0.1;
//                        level_button_autoclick++;
//                        cost_autoclick.setText("Цена: \n" + costAutoClick);
//
//
//                    }
//                    else if (button == buy_autoclick_2){
//                        costAutoClick_2 *= level_button_autoclick_2;
//                        MainActivity.multiply += 0.3;
//                        level_button_autoclick_2++;
//                        cost_autoclick_2.setText("Цена: \n" + costAutoClick_2);
//
//                    }
//
//                }
//                else {
//                    Toast.makeText(this, "Валюты недостаточно для покупки улучшения!", Toast.LENGTH_LONG).show();
//                }
//        });
//    }
    private void setProductRecycler(List<Product> productList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rv = findViewById(R.id.products);
        rv.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this,productList);

        rv.setAdapter(productAdapter);

    }

}





