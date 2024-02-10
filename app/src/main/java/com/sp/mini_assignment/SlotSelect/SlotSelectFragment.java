package com.sp.mini_assignment.SlotSelect;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sp.mini_assignment.Adapters.Carpark;
import com.sp.mini_assignment.BookSlot.BookedSlotFragment;
import com.sp.mini_assignment.R;

import java.util.List;

public class SlotSelectFragment extends Fragment {
    private View clickView;

    Carpark carpark;

    private ImageView close;

    private List<Carpark> carparkList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slot_select, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
             carpark = bundle.getParcelable("carpark");

        }
        else {
            Log.d("SlotSelectFragment", "Bundle null");
        }
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickView = view.findViewById(R.id.clickView);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_dialog);

        // Find the ImageView by its ID in the bottom_sheet_dialog layout
        ImageView close = dialog.findViewById(R.id.close_dialog);
        FrameLayout booknow = dialog.findViewById(R.id.BookNow);

        booknow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BookedSlotFragment bookedSlotFragment = new BookedSlotFragment();
                Bundle args = new Bundle();
                args.putParcelable("carpark", carpark);
                bookedSlotFragment.setArguments(args);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.bookslot_container, bookedSlotFragment);
                fragmentTransaction.commit();
                dialog.dismiss();

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBottomDialog(dialog); // Dismiss the dialog when the ImageView is clicked
            }
        });

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.BOTTOM;
            window.setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().windowAnimations = com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
            window.setGravity(Gravity.BOTTOM);
        }

        dialog.show();
    }


    private void closeBottomDialog(Dialog dialog) {
        // Get the dialog's window
        Window window = dialog.getWindow();
        if (window != null) {
            // Create a new TranslateAnimation for the slide-down effect
            Animation slideDown = new TranslateAnimation(0, 0, 0, window.getDecorView().getHeight());
            slideDown.setDuration(300); // Set the duration of the animation
            slideDown.setFillAfter(true); // Ensure the view stays in its final position after the animation

            // Set the animation listener to dismiss the dialog when the animation ends
            slideDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    dialog.dismiss(); // Dismiss the dialog when the animation ends
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            // Start the animation on the dialog's content view
            dialog.findViewById(android.R.id.content).startAnimation(slideDown);
        }
    }
}