package com.sp.mini_assignment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class HelpPageFragment extends Fragment {

    SearchView searchBar;
    ListView helpList;

    String[] helpOptionsList = {"Privacy & Security", "Payment, Billings & Refunds", "Frequently Asked Questions", "Contact Customer Support","Updates & Release Notes","Feedback Form", "Language Preference"};
    String[] helpDescriptions = {"Description 1", "Description 2", "Description 3", "Description 4", "Description 5", "Description 6", "Description 7"};
    int[] helpIcons = {R.drawable.privacy, R.drawable.payment, R.drawable.faq, R.drawable.support, R.drawable.updates,R.drawable.feedback, R.drawable.language};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_help_page, container, false);
        searchBar = rootView.findViewById(R.id.search_bar);
        helpList = rootView.findViewById(R.id.help_list);

        // Create a custom adapter with your custom layout
        CustomListAdapter customAdapter = new CustomListAdapter(requireContext(), helpOptionsList, helpDescriptions, helpIcons);
        helpList.setAdapter(customAdapter);

        helpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = helpOptionsList[position];
                Toast.makeText(requireContext(), "You Click - " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Apply the filter to the adapter
                customAdapter.getFilter().filter(newText);

                // Reset the data to the original titles when the query is empty
                if (newText.isEmpty()) {
                    customAdapter.resetData();
                }

                return false;
            }

        });


        return rootView;
    }
}
