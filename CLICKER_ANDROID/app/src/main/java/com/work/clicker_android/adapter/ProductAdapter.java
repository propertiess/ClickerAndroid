package com.work.clicker_android.adapter;

import static com.work.clicker_android.improvements.buy_sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.clicker_android.MainActivity;
import com.work.clicker_android.R;
import com.work.clicker_android.improvements;
import com.work.clicker_android.model.Product;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
    improvements improve;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList){
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productItems = LayoutInflater.from(context).inflate(R.layout.product_store,parent,false);
        return new ProductViewHolder(productItems);
    }

    @Override
    public synchronized void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.name_improve.setText(productList.get(position).getName_improve());
        holder.speed_improve.setText("+" + productList.get(position).getSpeed_improve() + " клик");
        holder.value_improve.setText("Цена: " + productList.get(position).getValue_improve());
        improve = new improvements();
        holder.name_improve.setOnClickListener(view -> {
            if(MainActivity.cash >= productList.get(position).getValue_improve()){
                if (improvements.level_button_autoclick == 1) {
                        MainActivity.startAutoClick();
                    }
                buy_sound = MediaPlayer.create(improve.cash_store.getContext(), R.raw.sound_buy);
                buy_sound.start();
                buy_sound.setOnCompletionListener(MediaPlayer::release);
                MainActivity.cash -= productList.get(position).getValue_improve();
                MainActivity.cash_amount.setText(String.valueOf((int) MainActivity.cash));

                improve.cash_store.setText(String.valueOf((int) MainActivity.cash));
                improvements.costAutoClick *= 2;
                productList.get(position).setValue_improve(improvements.costAutoClick);
//                holder.value_improve.setText("Цена: " + productList.get(position).getValue_improve()*2);

                MainActivity.multiply += productList.get(position).getSpeed_improve();
                System.out.println(position);
                improvements.level_button_autoclick++;

                notifyDataSetChanged();
//            if(productList == null) {
//                productList = new ArrayList<>();
//            }

//            productList = copy;
//            productList.remove(position);
//            productList.add(position, new Product(name_improve, speed_improve, costAutoClick, level_button_autoclick));
//            copy = productList;

            }
            else {
                Toast.makeText(improve.cash_store.getContext(), "Валюты недостаточно для покупки улучшения!", Toast.LENGTH_LONG).show();
            }
//            try {
//                notifyDataSetChanged();
//            } catch (Exception e){
//                System.out.println("Error notify");
//            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {
        Button name_improve;
        TextView speed_improve, value_improve;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name_improve = itemView.findViewById(R.id.autoclick_button);
            speed_improve = itemView.findViewById(R.id.powerofclick);
            value_improve = itemView.findViewById(R.id.cost_autoclick);

        }

    }
}
