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

public class BookedSlotAdapter extends RecyclerView.Adapter<BookedSlotAdapter.ViewHolder> {

    private Carpark carpark;
    private Context context;
    public BookedSlotAdapter(Carpark carpark, Context context) {
        this.carpark = carpark;
        this.context = context;
    }

    @NonNull
    @Override
    public BookedSlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_slot_recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedSlotAdapter.ViewHolder holder, int position) {

        holder.bind(carpark);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView carparkImage;
        private TextView carparkName, carparkPrice, carparkSlot, carparkDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carparkImage = itemView.findViewById(R.id.booked_slot_carpark_image);
            carparkName = itemView.findViewById(R.id.booked_slot_carpark_name);
            carparkSlot = itemView.findViewById(R.id.booked_slot_carpark_slot);
            carparkDate = itemView.findViewById(R.id.booked_slot_carpark_date);
            carparkPrice = itemView.findViewById(R.id.booked_slot_carpark_price);



        }

        public void bind(Carpark carpark) {
            // Load the image into the ImageView using Glide
            Glide.with(context)
                    .load(carpark.getCarparkImage())
                    .into(carparkImage);

            carparkName.setText(carpark.getCarparkName());
            carparkSlot.setText(carpark.getCarparkLevel());
            carparkDate.setText(carpark.getCarparkDate());
            carparkPrice.setText(carpark.getCarparkPrice());
        }
    }


}
