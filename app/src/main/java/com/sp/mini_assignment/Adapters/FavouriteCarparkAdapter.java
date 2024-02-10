package com.sp.mini_assignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sp.mini_assignment.R;
import com.sp.mini_assignment.FavouriteCarpark.FavouriteCarpark;

import java.util.ArrayList;
import java.util.List;

public class FavouriteCarparkAdapter extends RecyclerView.Adapter<FavouriteCarparkAdapter.ViewHolder> {

    private List<Carpark> carparkList;
    private Context context;

    public FavouriteCarparkAdapter(Context context) {
        this.context = context;
        this.carparkList = new ArrayList<>(); // Initialize an empty list
    }

    public void setCarparkList(List<Carpark> carparkList) {
        this.carparkList = carparkList;
        notifyDataSetChanged(); // Notify the adapter that the data set has changed
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_carpark_recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView carparkImage;
        private TextView carparkName, carparkDistance;

        public ViewHolder(View view) {
            super(view);
            carparkImage = view.findViewById(R.id.favourite_carparkImage);
            carparkName = view.findViewById(R.id.favourite_carparkName);
            carparkDistance = view.findViewById(R.id.favourite_carparkDistance);
        }

        public void bind(Carpark carpark) {
            // Load the image into the ImageView using Glide
            Glide.with(context)
                    .load(carpark.getCarparkImage())
                    .into(carparkImage);
            carparkName.setText(carpark.getCarparkName());
            carparkDistance.setText(carpark.getCarparkDistance());
        }
    }
}
