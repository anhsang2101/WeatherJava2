package com.example.weatherjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherjava.model.ItemWeekly;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecycleViewApdapter extends RecyclerView.Adapter<RecycleViewApdapter.ViewHolder> {

    ArrayList<ItemWeekly> items;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icWeather;
        TextView txtDate;
        TextView txtTemp;
        TextView txtDescription;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            icWeather = (ImageView) view.findViewById(R.id.weather_img);
            txtDate = (TextView) view.findViewById(R.id.day);
            txtTemp = (TextView) view.findViewById(R.id.temp);
            txtDescription = (TextView) view.findViewById(R.id.desc);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekly_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        String description = items.get(position).getDescription();
        String img = items.get(position).getImg();
        String date = items.get(position).getDate();
        String tempMin = items.get(position).getMinTemp();
        String tempMax = items.get(position).getMaxTemp();



        Picasso.get().load("https://openweathermap.org/img/w/"+ img +".png").into(holder.icWeather);
        holder.txtDate.setText(date);
        holder.txtTemp.setText(tempMin + " - " + tempMax + " ℃");
        holder.txtDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    RecycleViewApdapter(){

    }

    RecycleViewApdapter(Context context ,ArrayList<ItemWeekly> data){
        this.context = context;
        this.items=data;
    }
}
