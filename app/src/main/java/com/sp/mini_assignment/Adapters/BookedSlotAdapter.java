package com.sp.mini_assignment.Adapters;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.bumptech.glide.Glide;
import com.sp.mini_assignment.R;

import java.util.Calendar;

public class BookedSlotAdapter extends RecyclerView.Adapter<BookedSlotAdapter.ViewHolder> {

    private Carpark carpark;
    private Context context;
    private TextView dateTextView;
    private TextView timeTextView;

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
        private TextView carparkName, carparkPrice, carparkTime, carparkDate;
        private Button dateButton, timeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carparkImage = itemView.findViewById(R.id.booked_slot_carpark_image);
            carparkName = itemView.findViewById(R.id.booked_slot_carpark_name);
            carparkTime = itemView.findViewById(R.id.Time);
            carparkDate = itemView.findViewById(R.id.Date);
            carparkPrice = itemView.findViewById(R.id.booked_slot_carpark_price);
            dateButton = itemView.findViewById(R.id.date);
            timeButton = itemView.findViewById(R.id.time);

            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker();
                }
            });

            timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePicker();
                }
            });
        }

        public void bind(Carpark carpark) {
            // Load the image into the ImageView using Glide
            Glide.with(context)
                    .load(carpark.getCarparkImage())
                    .into(carparkImage);

            carparkName.setText(carpark.getCarparkName());
            carparkPrice.setText(carpark.getCarparkPrice());
        }

        private void showDatePicker() {
            // Create a DatePickerDialog to allow user to pick a date
            DatePickerDialog datePickerDialog = new DatePickerDialog(context);
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                    // Update the dateTextView with selected date
                    carparkDate.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
                }
            });
            datePickerDialog.show();
        }

        private void showTimePicker() {
            // Create a TimePickerDialog to allow user to pick a time
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                    // Update the timeTextView with selected time
                    carparkTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                }
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }
    }
}
