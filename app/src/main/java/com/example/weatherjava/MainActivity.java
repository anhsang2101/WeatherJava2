package com.example.weatherjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherjava.databinding.ActivityMainBinding;
import com.example.weatherjava.model.Weather;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavigationView navMenu;
    ImageView icWeather;
    ImageView icDescription;
    Button btnSearch;
    EditText edtSearch;
    TextView txtCity;
    TextView txtDate;
    TextView txtStatus;
    TextView txtTemp;
    TextView txtHumidity;
    TextView txtWind;
    TextView txtFeelLike;
    TextView txtSunset;
    TextView txtSunrise;
    TextView txtDescription;

    View viewNavHeader;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapping();

        GetCurrentWeatherData("danang");

        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_today:
                        navTodayEvent();
                        return true;
                    case R.id.nav_change_city:
                        navChangeCityEvent();
                        return true;
                    case R.id.nav_weekly:
                        navWeeklyEvent();
                        return true;
                    case R.id.nav_fav:
                        navFavEvent();
                        return true;
                    case R.id.nav_map:
                        navMapEvent();
                        return true;
                }
                return false;
            }
        });

    }

    public void mapping() {
        navMenu = binding.navMenu;
        txtCity = binding.city;
        txtDate = binding.date;
        txtStatus = binding.condition;
        txtTemp = binding.tempCondition;
        txtHumidity= binding.humidityValue;
        txtWind = binding.windValue;
        txtFeelLike = binding.temperature;
        icWeather = binding.weatherResource;
        icDescription = binding.descriptionImg;
        txtSunrise = binding.sunriseValue;
        txtSunset = binding.sunsetValue;
        txtDescription = binding.descriptionValue;
    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=67067b4298e6ce5edc4c4ad2eb43428e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            // city name
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            name = jsonObject.getString("name");
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
                            String description = jsonObjectWeather.getString("description");



                            //temp,humidity,feelLike
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");
                            String humidity = jsonObjectMain.getString("humidity");
                            String feelLike = jsonObjectMain.getString("feels_like");

                            //wind
                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");

                            //sunset, sunrise
                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String sunriseDateString = jsonObjectSys.getString("sunrise");
                            String sunsetDateString = jsonObjectSys.getString("sunset");


                            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                            long sunsetDateLong = Long.valueOf(sunsetDateString);
                            Date sunsetDate = new Date(sunsetDateLong*1000L);
                            String sunset = hourFormat.format(sunsetDate);

                            long sunriseDateLong = Long.valueOf(sunriseDateString);
                            Date sunriseDate = new Date(sunriseDateLong*1000L);
                            String sunrise = hourFormat.format(sunriseDate);



                            Weather weather = new Weather(name,Day,description,status,temp,humidity,feelLike,wind,sunrise,sunset,icon);
                            weather.toString();

                            txtCity.setText(weather.getName());
                            txtDate.setText(weather.getDate());
                            txtDescription.setText(weather.getDescription());
                            txtStatus.setText(weather.getStatus());
                            Picasso.get().load("https://openweathermap.org/img/w/"+weather.getIcStatus()+".png").into(icWeather);
                            Picasso.get().load("https://openweathermap.org/img/w/"+weather.getIcStatus()+".png").into(icDescription);
                            txtTemp.setText(weather.getTemp()+" ℃");
                            txtHumidity.setText(weather.getHumidity()+" %");
                            txtFeelLike.setText(weather.getFeelLike()+ " ℃");
                            txtWind.setText(weather.getWind() + " m/s");
                            txtSunrise.setText(weather.getSunrise());
                            txtSunset.setText(weather.getSunset());




                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Nhap sai ten", Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Nhap sai ten", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void navTodayEvent(){
        Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();
    }
    public void navChangeCityEvent(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Type city's name");
        edtSearch = new EditText(MainActivity.this);
        edtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
        dialog.setView(edtSearch);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, edtSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                String city = edtSearch.getText().toString();
                GetCurrentWeatherData(city);
//                Toast.makeText(MainActivity.this, "Thay doi thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    public void navWeeklyEvent(){
//        String city = edtSearch.getText().toString();
//        String city = "saigon";
        String city = name;
        Intent intent = new Intent(MainActivity.this, WeeklyActivity.class);
        intent.putExtra("name", city);
        startActivity(intent);
    }
    public void navFavEvent(){
//        String city = "saigon";
        Intent intent = new Intent(MainActivity.this, FavActivity.class);
//        intent.putExtra("name", city);
        startActivity(intent);
    }

    public void navMapEvent(){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }


}