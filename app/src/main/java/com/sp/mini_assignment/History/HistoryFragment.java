package com.sp.mini_assignment.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.sp.mini_assignment.Adapters.MyFragmentAdapter;
import com.sp.mini_assignment.R;

public class HistoryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private MyFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Past Reservations"));
        tabLayout.addTab(tabLayout.newTab().setText("OnGoing"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));


        // Create an instance of PagerAdapter and set it as the adapter for the ViewPager
        adapter = new MyFragmentAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        // Connect the TabLayout with the ViewPager2
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        return rootView;
    }
}
