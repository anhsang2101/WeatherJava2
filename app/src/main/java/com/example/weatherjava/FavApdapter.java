package com.example.weatherjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherjava.model.ItemFav;
import com.example.weatherjava.model.ItemWeekly;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavApdapter extends RecyclerView.Adapter<FavApdapter.ViewHolder> {

    static ArrayList<ItemFav> items;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        DatabaseReference dbRef;
        TextView txtName;
        ImageView icWeather;
        TextView txtDate;
        TextView txtTemp;
        TextView txtDescription;
        Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            dbRef = FirebaseDatabase.getInstance().getReference("ItemFav");


            txtName = (TextView) view.findViewById(R.id.city);
            icWeather = (ImageView) view.findViewById(R.id.weather_img);
            txtDate = (TextView) view.findViewById(R.id.day);
            txtTemp = (TextView) view.findViewById(R.id.temp);
            txtDescription = (TextView) view.findViewById(R.id.desc);

            btnDelete = (Button) view.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Add code to handle button click
                    int position = getAdapterPosition();
                    String idTemp = items.get(position).getId();
                    DatabaseReference deleteTask = dbRef.child(idTemp);
                    deleteTask.removeValue();

                }
            });


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String name = items.get(position).getName();
        String description = items.get(position).getDescription();
        String img = items.get(position).getImg();
        String date = items.get(position).getDate();
        String tempMin = items.get(position).getMinTemp();
        String tempMax = items.get(position).getMaxTemp();


        holder.txtName.setText(name);
        Picasso.get().load("https://openweathermap.org/img/w/"+ img +".png").into(holder.icWeather);
        holder.txtDate.setText(date);
        holder.txtTemp.setText(tempMin + " - " + tempMax + " ℃");
        holder.txtDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    FavApdapter(){

    }

    FavApdapter(Context context ,ArrayList<ItemFav> data){
        this.context = context;
        this.items=data;
    }
}
