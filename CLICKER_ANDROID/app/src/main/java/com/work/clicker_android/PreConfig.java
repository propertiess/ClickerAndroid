package com.work.clicker_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.clicker_android.model.Product;

import java.lang.reflect.Type;
import java.util.List;

public class PreConfig {
    // Strings are for look for arrays
    private static final String PRODUCTLIST_KEY = "productlist_key";
    private static final String COST_KEY = "cost_key";
    private static final String LEVEL_KEY = "level_key";

    // write array in the bd
    public static void writeListInPref(Context context, List<Product> productList){
        Gson gson = new Gson();
        String jsonString = gson.toJson(productList);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PRODUCTLIST_KEY, jsonString);
        editor.apply();
    }

    public static void writeCost(Context context, long[] cost){
        Gson gson = new Gson();
        String jsonString = gson.toJson(cost);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(COST_KEY, jsonString);
        editor.apply();
    }

    public static long[] readCost(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(COST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<long[] >() {}.getType();

        return gson.fromJson(jsonString, type);
    }
    public static void writeLevel(Context context, long[] level){
        Gson gson = new Gson();
        String jsonString = gson.toJson(level);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LEVEL_KEY, jsonString);
        editor.apply();
    }

    public static long[] readLevel(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LEVEL_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<long[] >() {}.getType();

        return gson.fromJson(jsonString, type);
    }
}
