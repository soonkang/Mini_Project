package com.sp.mini_assignment.BookSlot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.mini_assignment.Adapters.BookedSlotAdapter;
import com.sp.mini_assignment.Adapters.Carpark;
import com.sp.mini_assignment.Payment.PaymentFragment;
import com.sp.mini_assignment.R;

import java.util.List;
import java.util.Locale;

public class BookedSlotFragment extends Fragment {

    private BookedSlotAdapter bookedSlotAdapter;

    private RecyclerView bookedSlotRecyclerView;

    Carpark carpark;

    TextView countdownTimer, confirm;

    CountDownTimer timer;

    private List<Carpark> carparkList;

    public BookedSlotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_booked_slot, container, false);
        bookedSlotRecyclerView = rootView.findViewById(R.id.booked_slot_recyclerview);
        bookedSlotRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        countdownTimer = rootView.findViewById(R.id.confirm_timer);
        confirm = rootView.findViewById(R.id.booked_slot_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PaymentFragment());
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            carpark = bundle.getParcelable("carpark");


            if (carpark != null) {
                bookedSlotAdapter = new BookedSlotAdapter(carpark, requireContext());
                bookedSlotRecyclerView.setAdapter(bookedSlotAdapter);

            } else {
                Log.d("BookedSlotFragment", "carparkList is null or empty");

            }
        } else {
            Log.d("BookedSlotFragment", "bundle or carparkList is null");

        }
        startCountdown();

        return rootView;


    }


    public void startCountdown() {
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) % 3600 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String formatTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                countdownTimer.setText(formatTime);
            }

            @Override
            public void onFinish() {
                countdownTimer.setText("0m 00s");
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.popBackStack();

            }


            }.start();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_bookedslot, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}
