package com.work.clicker_android.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
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

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public synchronized void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.name_improve.setText(productList.get(position).getName_improve());
        holder.speed_improve.setText("+" + productList.get(position).getSpeed_improve() + " клик");
        holder.value_improve.setText("Цена: " + productList.get(position).getValue_improve());

        improve = new improvements();
        holder.name_improve.setOnClickListener(view -> {

            if(MainActivity.cash >= productList.get(position).getValue_improve()){
                improve.Buying(productList.get(position).getValue_improve(), position,
                        productList.get(position).getSpeed_improve(), context);
                productList.get(position).setValue_improve(improvements.costAutoClick[position]);

                holder.value_improve.setText("Цена: " + productList.get(position).getValue_improve()*2);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, "Валюты недостаточно для покупки улучшения!", Toast.LENGTH_LONG).show();
            }
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
