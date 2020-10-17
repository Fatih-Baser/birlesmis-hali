package com.example.birlesmishali;

import android.graphics.Bitmap;

public class Slider {

    private  String cityName;
    private  Bitmap cityIcon;


    public Slider(String cityName, Bitmap cityIcon) {
        this.cityName = cityName;
        this.cityIcon = cityIcon;

    }

    public String getCityName() {
        return cityName;
    }

    public Bitmap getCityIcon() {
        return cityIcon;
    }


}
