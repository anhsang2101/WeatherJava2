package com.example.weatherjava;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherjava.model.Weather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PopupDialog extends Dialog{
    ImageView icWeather;
    TextView txtCity;
    TextView txtDate;
    TextView txtStatus;
    TextView txtTemp;
    private double latitude;
    private double longitude;

    public void mapping() {
        icWeather = findViewById(R.id.weather_resource);
        txtCity = findViewById(R.id.city);
        txtDate = findViewById(R.id.date);
        txtStatus = findViewById(R.id.condition);
        txtTemp = findViewById(R.id.temp_condition);
    }
    public PopupDialog(@NonNull Context context, double latitude, double longitude) {
        super(context);
        this.latitude = latitude;
        this.longitude = longitude;

        setContentView(R.layout.popup);
        mapping();
        GetCurrentWeatherData(String.valueOf(latitude),String.valueOf(longitude),context);

    }

    public void GetCurrentWeatherData(String lat, String lon, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=67067b4298e6ce5edc4c4ad2eb43428e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            // city name
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            if(name.equals("Turan")){
                                name="Da Nang";
                            }

                            //date
                            long dayLong = Long.valueOf(day);
                            Date date = new Date(dayLong*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                            String Day = simpleDateFormat.format(date);

                            //icon, description, status
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");
//                            String description = jsonObjectWeather.getString("description");


                            // temp
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");


                            Weather weather = new Weather(name,Day,temp,status,icon);
                            weather.toString();

                            txtCity.setText(weather.getName());
                            txtDate.setText(weather.getDate());
                            txtStatus.setText(weather.getStatus());
                            Picasso.get().load("https://openweathermap.org/img/w/"+weather.getIcStatus()+".png").into(icWeather);
                            txtTemp.setText(weather.getTemp()+" ℃");




                        } catch (JSONException e) {

                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    // Override necessary methods and implement your dialog functionality
        // ...


}
