package com.example.weatherjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherjava.model.ItemFav;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FavActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    RecyclerView favItems;
    Button btnAdd;
    Button btnRefresh;

    EditText edtAdd;

    ArrayList<ItemFav> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ket qua", "du lieu " + city);
        items = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("ItemFav");

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(FavActivity.this);
                dialog.setTitle("Type city's name");
                edtAdd = new EditText(FavActivity.this);
                edtAdd.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog.setView(edtAdd);
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FavActivity.this, edtAdd.getText().toString(), Toast.LENGTH_SHORT).show();

                        getItemFavByName(edtAdd.getText().toString(), new OnItemFavReceivedListener() {
                            @Override
                            public void onItemFavReceived(ItemFav itemFav) {
                                itemFav.setId(dbRef.push().getKey());

                                dbRef.child(itemFav.getId()).setValue(itemFav).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(FavActivity.this, "add thanh cong", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(FavActivity.this, "loi", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Toast.makeText(FavActivity.this, "nice", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemFavError(Exception e) {

                            }
                        });

                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FavActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFavList();
            }
        });

        fetchFavList();


    }



    public void fetchFavList(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                if(snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()){
                        ItemFav data = i.getValue(ItemFav.class);
                        items.add(data);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ArrayList<ItemFav> a = new ArrayList<>();
//        a.add(new ItemFav("1","2","3","4","5","6"));

        favItems = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FavActivity.this, LinearLayoutManager.VERTICAL, false);
        favItems.setLayoutManager(layoutManager);
        favItems.setHasFixedSize(true);
        favItems.setAdapter(new FavApdapter(FavActivity.this, items));
    }

//    public ItemFav getItemFavByName(String name){
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(FavActivity.this);
//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + name + "&units=metric&appid=67067b4298e6ce5edc4c4ad2eb43428e";
//        final ItemFav[] i = new ItemFav[1];
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//
//                            // city name
//                            JSONObject jsonObject = new JSONObject(response);
//                            String day = jsonObject.getString("dt");
//                            String name = jsonObject.getString("name");
//                            if(name.equals("Turan")){
//                                name="Da Nang";
//                            }
//
//
//                            //date
//                            long dayLong = Long.valueOf(day);
//                            Date date = new Date(dayLong*1000L);
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
//                            String Day = simpleDateFormat.format(date);
//
//                            //icon, description
//                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
//                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
//                            String icon = jsonObjectWeather.getString("icon");
//                            String description = jsonObjectWeather.getString("description");
//
//
//
//                            //temp max min
//                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
//                            String tempMax = jsonObjectMain.getString("temp_max");
//                            String tempMin = jsonObjectMain.getString("temp_min");
//
//
//                            i[0] = new ItemFav(name,icon,Day,tempMax,tempMin,description);
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(stringRequest);
//
//        return i[0];
//    }
public void getItemFavByName(String name, final OnItemFavReceivedListener listener){

    RequestQueue requestQueue = Volley.newRequestQueue(FavActivity.this);
    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + name + "&units=metric&appid=67067b4298e6ce5edc4c4ad2eb43428e";

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        // city name
                        JSONObject jsonObject = new JSONObject(response);
                        String day = jsonObject.getString("dt");
                        String cityName = jsonObject.getString("name");
                        if(cityName.equals("Turan")){
                            cityName="Da Nang";
                        }


                        //date
                        long dayLong = Long.valueOf(day);
                        Date date = new Date(dayLong*1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        String dayOfWeek = simpleDateFormat.format(date);

                        //icon, description
                        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String icon = jsonObjectWeather.getString("icon");
                        String description = jsonObjectWeather.getString("description");

                        //temp max min
                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        String tempMax = jsonObjectMain.getString("temp_max");
                        String tempMin = jsonObjectMain.getString("temp_min");

                        ItemFav i = new ItemFav(cityName,icon,dayOfWeek,tempMax,tempMin,description);

                        // call the callback with the retrieved ItemFav object
                        listener.onItemFavReceived(i);

                    } catch (JSONException e) {
                        // call the callback with an error
                        listener.onItemFavError(e);
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // call the callback with an error
            listener.onItemFavError(error);
        }
    });
    requestQueue.add(stringRequest);

}

    // callback interface
    public interface OnItemFavReceivedListener {
        void onItemFavReceived(ItemFav itemFav);
        void onItemFavError(Exception e);
    }

}