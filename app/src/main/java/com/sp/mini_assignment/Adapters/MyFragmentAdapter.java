package com.sp.mini_assignment.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sp.mini_assignment.History.CancelledFragment;
import com.sp.mini_assignment.History.OnGoingFragment;
import com.sp.mini_assignment.History.PastReservationsFragment;

public class MyFragmentAdapter extends FragmentStateAdapter {
    public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position ==1) {
            return new PastReservationsFragment();
        }
        else if (position ==2) {
            return new OnGoingFragment();
        }
        else {
            return new CancelledFragment();
        }


    }

    public int getItemCount() {
        return 0;
    }
}
