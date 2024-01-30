package com.sp.mini_assignment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class SlotSelectFragment extends Fragment {

    private View whiteBox;
    private View clickView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slot_select, container, false);

        whiteBox = view.findViewById(R.id.whiteBox);
        clickView = view.findViewById(R.id.clickView);

        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandWhiteBox();
            }
        });

        return view;
    }

    public void onCarClick(View view) {
        // Handle car click event here
        expandWhiteBox();
    }

    private void expandWhiteBox() {
        // Expand white box to fill the space below the table layout
        ValueAnimator animator = ValueAnimator.ofInt(0, 380); // 380dp is the height of the table layout
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = whiteBox.getLayoutParams();
                layoutParams.height = value;
                whiteBox.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(100000); // Animation duration in milliseconds
        animator.start();
    }
}
