package com.sp.mini_assignment.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sp.mini_assignment.FavouriteCarpark.FavouriteCarpark;
import com.sp.mini_assignment.Home.Main;
import com.sp.mini_assignment.Interfaces.OnItemClickListener;
import com.sp.mini_assignment.R;

import java.util.List;

public class CarparkAdapter extends RecyclerView.Adapter<CarparkAdapter.ViewHolder> {
    private List<Carpark> carparkList;
    private Context context;

    private ImageView favouriteBtn;

    private OnItemClickListener listener;

    favouriteCarparkHelper favouriteCarparkHelper;



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
        private ImageView carparkImage, favouriteBtn;

        private TextView carparkName, carparkPrice, carparkDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favouriteBtn = itemView.findViewById(R.id.favouriteBtn);
            carparkImage = itemView.findViewById(R.id.home_carparkImage);
            carparkName = itemView.findViewById(R.id.home_carparkName);
            // carparkPrice = itemView.findViewById(R.id.home_carparkDistance);
            carparkDistance = itemView.findViewById(R.id.home_carparkDistance);
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


                    }
                    else {
                        favouriteBtn.setImageResource(R.drawable.favourite_logo); // Unfavourite
                        carpark.setFavStatus("0");
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
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}

