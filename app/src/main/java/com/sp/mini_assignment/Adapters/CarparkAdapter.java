package com.sp.mini_assignment.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sp.mini_assignment.FavouriteCarpark.FavouriteCarpark;
import com.sp.mini_assignment.GoogleMap.Map_GPS_Fragment;
import com.sp.mini_assignment.Home.Main;
import com.sp.mini_assignment.Interfaces.OnDirectionsClickListener;
import com.sp.mini_assignment.Interfaces.OnItemClickListener;
import com.sp.mini_assignment.R;

import java.util.List;
import java.util.Map;

public class CarparkAdapter extends RecyclerView.Adapter<CarparkAdapter.ViewHolder> {
    private List<Carpark> carparkList;
    private Context context;

    private ImageView favouriteBtn;

    private OnItemClickListener listener;

    private OnDirectionsClickListener directionsClickListener;


    favouriteCarparkHelper favouriteCarparkHelper;

    FavouriteCarparkAdapter favouriteCarparkAdapter;


    public CarparkAdapter(List<Carpark> carparkList, Context context, favouriteCarparkHelper helper) {
        this.carparkList = carparkList;
        this.context = context;
        this.favouriteCarparkHelper = helper;
        favouriteCarparkHelper = new favouriteCarparkHelper(context);
    }

    @NonNull
    @Override
    public CarparkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view_row, parent, false);


        return new ViewHolder(view);
    }

    // Bind data to the viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carpark carpark = carparkList.get(position);
        if (carpark != null) {
            holder.bind(carpark);
        }
    }

    @Override
    public int getItemCount() {
        return carparkList.size();
    }


    // HOLDER AND BIND DATA FOR THE RECYCLER VIEW ROW

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView carparkImage, favouriteBtn, carparkDirections, carparkCapacityLogo;

        private TextView carparkName, carparkPrice, carparkDistance, carparkCapacity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favouriteBtn = itemView.findViewById(R.id.favouriteBtn);
            carparkImage = itemView.findViewById(R.id.home_carparkImage);
            carparkName = itemView.findViewById(R.id.home_carparkName);
            // carparkPrice = itemView.findViewById(R.id.home_carparkDistance);
            carparkDistance = itemView.findViewById(R.id.home_carparkDistance);
            carparkCapacityLogo = itemView.findViewById(R.id.home_recycler_view_capacity_logo);
            carparkDirections = itemView.findViewById(R.id.home_recycler_view_map);
            carparkCapacity = itemView.findViewById(R.id.home_recycler_view_capacity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            carparkDirections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            directionsClickListener.onDirectionsClick(position);
                        }
                    }
                }
            });

            favouriteBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Carpark carpark = carparkList.get(position);


                    if (carpark.getFavStatus().equals("0")) { // Not Yet Favourited
                        favouriteBtn.setImageResource(R.drawable.favourited_star_logo);
                        carpark.setFavStatus("1");
                        // Change image of favourite button to FILLED to indicate it has been favourited
                        favouriteCarparkHelper.insertIntoTheDatabase(carpark.getCarparkName(), carpark.getCarparkImage(), carpark.getFavStatus());
                        Log.d("carparkName inserted", carpark.getCarparkName());
                        Log.d("carparkImage inserted", carpark.getCarparkImage());

                        Log.d("favStatus inserted", carpark.getFavStatus());


                    } else {
                        favouriteBtn.setImageResource(R.drawable.favourite_logo); // Unfavourite
                        carpark.setFavStatus("0");
                        favouriteCarparkHelper.removeFav(carpark);


                        //  notifyDataSetChanged();
                    }
                }
            });



        }

        public void bind(Carpark carpark) {
            // Load the image into the ImageView using Glide
            Glide.with(context)
                    .load(carpark.getCarparkImage())
                    .into(carparkImage);

            carparkName.setText(carpark.getCarparkName());
            carparkDistance.setText(carpark.getCarparkDistance());
            favouriteBtn.setImageResource(R.drawable.favourite_logo);

            if (carpark.getKey_id() == 1) {
                carparkCapacity.setText("Capacity: 54");
            } else if (carpark.getKey_id() == 2) {
                carparkCapacity.setText("Capacity: 24%");
            } else if (carpark.getKey_id() == 3) {
                carparkCapacity.setText("Capacity: 89%");
            } else if (carpark.getKey_id() == 4) {
                carparkCapacity.setText("Capacity: 2%");
            } else if (carpark.getKey_id() == 5) {
                carparkCapacity.setText("Capacity: 40%");
            }

            if (carpark.getCarparkCapacity() == "full") {
                carparkCapacityLogo.setImageResource(R.drawable.capacity_full);

            } else if (carpark.getCarparkCapacity() == "half") {
                carparkCapacityLogo.setImageResource(R.drawable.capacity_half);
            } else if (carpark.getCarparkCapacity() == "empty") {
                carparkCapacityLogo.setImageResource(R.drawable.capacity_status_empty);
            }

        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public void setDirectionsClickListener(OnDirectionsClickListener listener) {
        this.directionsClickListener = listener;
    }

}


