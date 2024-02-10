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

import java.util.List;
import java.util.Locale;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<Carpark> carparkList, searchResultsList;
    private Context context;

    public SearchResultsAdapter(List<Carpark> searchResultsList, List<Carpark> carparkList, Context context) {
        this.searchResultsList = searchResultsList;
        this.carparkList = carparkList;
        this.context = context;
    }
    @NonNull
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.ViewHolder holder, int position) {
        Carpark carpark = searchResultsList.get(position);
        holder.bind(carpark);

    }

    @Override
    public int getItemCount() {
        return searchResultsList.size();

    }

    public void updateSearchResults(String query) {
        query = query.toLowerCase(Locale.getDefault());
        searchResultsList.clear();


        if(query.isEmpty()) {
            searchResultsList.addAll(carparkList);

        }

        // Perform search logic and add matching results to searchResultsList
        for (Carpark carpark : carparkList) {
            if (carpark.getCarparkName().toLowerCase(Locale.getDefault()).contains(query)) {
                searchResultsList.add(carpark);
            }
        }

        // Notify the adapter that the data set has changed
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView carparkImage;

        private TextView carparkName, carparkPrice, carparkDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carparkImage = itemView.findViewById(R.id.home_carparkImage);
            carparkName = itemView.findViewById(R.id.home_carparkName);
            // carparkPrice = itemView.findViewById(R.id.home_carparkDistance);
            carparkDistance = itemView.findViewById(R.id.home_carparkDistance);
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
