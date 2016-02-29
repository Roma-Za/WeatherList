package com.example.roman.weatherlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class Cities {
    private SharedPreferences prefs;

    public Cities(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);

    }

    public String [] getMyCity(){
        String city = prefs.getString("myCity", "");
        String [] arr = {city};
        return arr;
    }

    public void setMyCity(String city){
        prefs.edit().putString("myCity", city).commit();
        addCity(city);
    }

    private void addCity(String city) {
        if(!prefs.contains("cities")){
            prefs.edit().putString("cities", city).commit();
        }else {
            String s = prefs.getString("cities", "");
            String[] arr = TextUtils.split(s, ",");
            boolean flag = false;
            for (String i : arr) {
                if (i.compareToIgnoreCase(city) == 0) {
                    flag = true;
                }
            }

            if (!flag) {
                prefs.edit().putString("cities", s + "," + city).commit();
            }
        }
    }

    public void setCities(String [] cities){
        String s = TextUtils.join(",", cities);
        prefs.edit().putString("cities", s).commit();
    }

    public String [] getCities(){
        if(prefs.contains("cities")) {
            String s = prefs.getString("cities", "");
            return TextUtils.split(s, ",");
        }else{
            String [] arr = {"zmiiv"};
            prefs.edit().putString("cities", "zmiiv").commit();
            return arr;
        }
    }

    public void setCity(String city){
        addCity(city);
    }
}
