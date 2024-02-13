package com.sp.mini_assignment.Settings;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sp.mini_assignment.Adapters.HelpListAdapter;
import com.sp.mini_assignment.R;

public class HelpCentreFragment extends Fragment {

    SearchView searchBar;
    ListView helpList;

    String[] helpOptionsList = {"Profile Settings", "Frequently Asked Questions", "Contact Customer Support", "Language Preference","Feedback Form", "Payment, Billings & Refunds","Privacy & Security","Updates & Release Notes"};
    String[] helpDescriptions = {"Description 1", "Description 2", "Description 3", "Description 4", "Description 5", "Description 6", "Description 7","Description 8"};
    int[] helpIcons = {R.drawable.profile_icon, R.drawable.faq, R.drawable.support, R.drawable.language,R.drawable.feedback, R.drawable.payment, R.drawable.privacy, R.drawable.updates};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_help_page, container, false);
        searchBar = rootView.findViewById(R.id.search_bar);
        helpList = rootView.findViewById(R.id.help_list);

        // Create a custom adapter with your custom layout
        HelpListAdapter customAdapter = new HelpListAdapter(requireContext(), helpOptionsList, helpDescriptions, helpIcons);
        helpList.setAdapter(customAdapter);

        helpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = helpOptionsList[position];

                // Open a different page based on the clicked setting
                openPageForSetting(selectedItem);
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

    private void openPageForSetting(String setting) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        if ("Frequently Asked Questions".equals(setting)) {
            // Open FAQFragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FAQFragment()).addToBackStack(null).commit();
        } else if ("Contact Customer Support".equals(setting)) {
            // Open CustomerSupportFragment
            CustomerSupportFragment customerSupportFragment = new CustomerSupportFragment();
            // Pass a flag indicating delayed visibility
            Bundle args = new Bundle();
            args.putBoolean("delayVisibility", true);
            customerSupportFragment.setArguments(args);

            fragmentManager.beginTransaction().replace(R.id.fragment_container, customerSupportFragment).addToBackStack(null).commit();
        } else if("Language Preference".equals(setting)){
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new LanguageFragment()).addToBackStack(null).commit();

        }

        // Show the toast for all settings
        Toast.makeText(requireContext(), "You Click - " + setting, Toast.LENGTH_SHORT).show();
    }


}