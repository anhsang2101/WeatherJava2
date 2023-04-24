package com.example.weatherjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherjava.model.ItemWeekly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeeklyActivity extends AppCompatActivity {

    String cityName = "";
    RecyclerView weeklyItems;

    ArrayList<ItemWeekly> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ket qua", "du lieu " + city);
        items = new ArrayList<>();
        Get7DaysData(city);




    }

    public void mapping(){

    }


    private void Get7DaysData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + data
                + "&units=metric&cnt=40&appid=67067b4298e6ce5edc4c4ad2eb43428e";
        RequestQueue requestQueue = Volley.newRequestQueue(WeeklyActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String cityName = jsonObjectCity.getString("name");

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for(int i =0; i < jsonArrayList.length(); i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String day  = jsonObjectList.getString("dt");
                                long dayLong = Long.valueOf(day);
                                Date date = new Date(dayLong*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE HH:mm");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_max");
                                String min = jsonObjectTemp.getString("temp_min");

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather =  jsonArrayWeather.getJSONObject(0);
                                String description = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                items.add(new ItemWeekly(icon,Day,max,min,description));
                            }
                            weeklyItems = (RecyclerView) findViewById(R.id.recyclerview);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(WeeklyActivity.this, LinearLayoutManager.VERTICAL, false);
                            weeklyItems.setLayoutManager(layoutManager);
                            weeklyItems.setHasFixedSize(true);
                            weeklyItems.setAdapter(new RecycleViewApdapter(WeeklyActivity.this, items));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("ket qua" , response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}