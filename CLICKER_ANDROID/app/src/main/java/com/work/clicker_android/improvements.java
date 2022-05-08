package com.work.clicker_android;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.work.clicker_android.adapter.ProductAdapter;
import com.work.clicker_android.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class improvements extends AppCompatActivity {
    public static TextView cash_store;
    public MediaPlayer buy_sound;
    public  static long[] costAutoClick;
    static String[] nameImprove;
    public static float[] speedImprove;

    private static long[] levelButtonAutoclick;

    public  List<Product> productList;
    public static List<Product> copy;
    public RecyclerView rv;
    ProductAdapter productAdapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_improvements);


        //default settings
        init();

    }


    private void init(){
        handler = new Handler();
        cash_store = findViewById(R.id.cashInTheStore);
        cash_store.setText(String.valueOf((int)MainActivity.cash));
        setDefaultValue();

        if(MainActivity.bought) {
            costAutoClick = PreConfig.readCost(this);
            levelButtonAutoclick = PreConfig.readLevel(this);
        }

        if (productList == null) {
            productList = new ArrayList<>();
        }

        for (int i = 0; i < costAutoClick.length; i++) {
            productList.add(new Product(nameImprove[i], speedImprove[i], costAutoClick[i], levelButtonAutoclick[i]));
        }

        for (int i = 0; i < productList.size(); i++) {
            setProductRecycler(productList);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            PreConfig.writeListInPref(this,productList);
            MainActivity.autoClick.interrupt();
            MainActivity.counterThread--;
            System.out.println("Thread has stopped onPause() improvements");
        } catch (Exception e){
            System.out.println("Error thread! onPause()");

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {

            if(!MainActivity.autoClick.isAlive()) {
                MainActivity.startAutoClick();
                MainActivity.counterThread++;
            }

            System.out.println("Thread has returned onResume() improvements");
        }catch (Exception e){
            System.out.println("Error thread! onResume()");

        }

    }

    public void setDefaultValue(){
        if (costAutoClick == null && levelButtonAutoclick == null) {
            costAutoClick = new long[] {13, 100, 400, 1000, 1500};
            levelButtonAutoclick = new long[5];
            Arrays.fill(levelButtonAutoclick,1);
            nameImprove = new String[] {"DOGE","BUSD","BNB", "ETH", "BTC"};
            speedImprove = new float[]{0.1f,0.3f,0.5f,1f,2f};
        }

    }

    // operation buying of coin
    public void Buying(long value, int position, float speed_improve, Context context){
        if(MainActivity.cash >= value){
            if (MainActivity.counterThread == 0) {
                MainActivity.startAutoClick();
                MainActivity.counterThread++;
            }
            buy_sound = MediaPlayer.create(context, R.raw.sound_buy);
            buy_sound.start();
            buy_sound.setOnCompletionListener(MediaPlayer::release);
            MainActivity.cash -= value;
            MainActivity.cash_amount.setText(String.valueOf((int) MainActivity.cash));

            cash_store.setText(String.valueOf((int) MainActivity.cash));
            costAutoClick[position] *= 2;
            MainActivity.multiply += speed_improve;
            System.out.println(position);
            System.out.println(productList);
            System.out.println(copy);
            levelButtonAutoclick[position] += 1;
            MainActivity.myEditor.putFloat("multiply", MainActivity.multiply);
            PreConfig.writeCost(context, costAutoClick);
            PreConfig.writeLevel(context,levelButtonAutoclick);
            MainActivity.bought = true;
            MainActivity.myEditor.putBoolean("bought", true);
            MainActivity.myEditor.commit();

        }
        else {
            Toast.makeText(context, "Валюты недостаточно для покупки улучшения!", Toast.LENGTH_LONG).show();
        }

    }


    private void setProductRecycler(List<Product> productList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rv = findViewById(R.id.products);
        rv.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this,productList);

        rv.setAdapter(productAdapter);

    }

}





