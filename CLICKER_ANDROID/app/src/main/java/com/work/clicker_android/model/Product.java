package com.work.clicker_android.model;

public class Product {

    String name_improve;
    long value_improve;
    float speed_improve;
    long level_improve;

    public long getLevel_improve() {
        return level_improve;
    }

    public void setLevel_improve(long level_improve) {
        this.level_improve = level_improve;
    }

    public Product(String name_improve, float speed_improve, long value_improve, long level_improve){
        this.name_improve = name_improve;
        this.speed_improve = speed_improve;
        this.value_improve = value_improve;
        this.level_improve = level_improve;
    }
    public Product(){

    }

    public String getName_improve() {
        return name_improve;
    }

    public void setName_improve(String name_improve) {
        this.name_improve = name_improve;
    }

    public long getValue_improve() {
        return value_improve;
    }

    public void setValue_improve(long value_improve) {
        this.value_improve = value_improve;
    }

    public float getSpeed_improve() {
        return speed_improve;
    }

    public void setSpeed_improve(float speed_improve) {
        this.speed_improve = speed_improve;
    }
}
