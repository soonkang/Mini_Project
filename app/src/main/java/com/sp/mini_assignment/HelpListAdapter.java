package com.sp.mini_assignment;


// CustomListAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> originalTitles;
    private final List<String> titles;
    private final String[] descriptions;
    private final int[] icons;

    public CustomListAdapter(Context context, String[] titles, String[] descriptions, int[] icons) {
        super(context, R.layout.help_list_layout, titles);

        this.context = context;
        this.originalTitles = new ArrayList<>(Arrays.asList(titles));
        this.titles = new ArrayList<>(originalTitles);
        this.descriptions = descriptions;
        this.icons = icons;
    }

    public void resetData() {
        titles.clear();
        titles.addAll(originalTitles);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<String> filteredList = new ArrayList<>();

                // Implement your filtering logic here
                for (String title : originalTitles) {
                    if (title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(title);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                titles.clear();
                titles.addAll((List<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.help_list_layout, parent, false);

        TextView titleTextView = rowView.findViewById(R.id.titleTextView);
        TextView descriptionTextView = rowView.findViewById(R.id.descriptionTextView);
        ImageView iconImageView = rowView.findViewById(R.id.iconImageView);

        if (position < titles.size()) { // Ensure the position is within bounds
            int index = originalTitles.indexOf(titles.get(position));

            titleTextView.setText(titles.get(position));
            descriptionTextView.setText(descriptions[index]);
            iconImageView.setImageResource(icons[index]);
        } else {
            // If position is out of bounds, hide the row
            rowView.setVisibility(View.GONE);
        }

        return rowView;
    }


}

