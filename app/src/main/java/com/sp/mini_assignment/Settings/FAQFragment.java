package com.sp.mini_assignment.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.sp.mini_assignment.R;

public class FAQFragment extends Fragment {

    ListView FAQList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        FAQList = rootView.findViewById(R.id.faq_list);

        String[] faqQuestions = {
                "How does the app help me find available parking spaces?",
                "Is the parking information real-time?",
                "Can I reserve a parking spot in advance?",
                "What features are available in the app's map view?",
                "Are there filters to narrow down parking options based on preferences?",
                "How accurate is the parking availability information?",
                "Is there a notification system for updates on parking availability?",
                "Can I pay for parking through the app?",
                "Are there loyalty programs or discounts for frequent users?",
                "How do I report an issue with a parking space or the app?",
                "What payment methods are accepted?",
                "Is my payment information secure?",
                "Can I view my parking history and receipts?",
                "How does the app handle accessibility for users with disabilities?",
                "What do I do if I can't find a parking space?",
                "How can I provide feedback or suggest improvements for the app?",
                // Add more questions as needed
        };


        // Use a custom layout for each list item
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.faq_list, R.id.faq_question, faqQuestions);

        // Set the adapter to the ListView
        FAQList.setAdapter(adapter);

        return rootView;
    }
}
